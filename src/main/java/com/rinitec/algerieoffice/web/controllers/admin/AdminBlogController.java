package com.rinitec.algerieoffice.web.controllers.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.blog.IAutorService;
import com.rinitec.algerieoffice.services.admins.blog.IBlogService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.admins.blog.AutorForm;
import com.rinitec.algerieoffice.web.form.admins.blog.BlogForm;
import com.rinitec.algerieoffice.web.form.admins.blog.BlogStatForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/blog")
public class AdminBlogController {

	private IAttributeService attributeService;
	private IBlogService blogService;
	private IAutorService autorService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminBlogController(IAttributeService attributeService, IBlogService blogService, IAutorService autorService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.blogService = blogService;
		this.autorService = autorService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String showBlogs(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminBlogBlogs";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/all-load", method = RequestMethod.GET)
	public String loadBlogs(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", blogService.findBlogsList(filter, search, sort, rows, page, hasDesc));
		return "adminListBlogs";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String showNewBlog(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("blog", new BlogForm(request));
		model.addAttribute("autors", autorService.findAllAutors());
		model.addAttribute("urlBlog", ConstraintesURL.getExplorerBlogURL());
		return "adminBlogBlog";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String showEditBlog(final Model model, @RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final BlogForm blogForm = blogService.readBlogForm(id);
		if(blogForm == null) {
			return "redirect:/admin/blog/all?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("blog", blogForm);
		model.addAttribute("autors", autorService.findAllAutors());
		model.addAttribute("urlBlog", ConstraintesURL.getExplorerBlogURL());
		return "adminBlogBlog";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param identify
	 * @return
	 */
	@RequestMapping(value = "/check-identify", method = RequestMethod.GET)
	@ResponseBody
	public GenericResponse checkIdentify(@RequestParam("identify") final String identify) {
		if(blogService.existsByIdentify(identify)) {
			throw new UrlUnavailableException("message.error.url");
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param blogForm
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveBlog(@AuthenticationPrincipal final LocalUser localUser, @Valid final BlogForm blogForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		Blog blog = null;
		blogForm.setFile(file);
		if(StringUtils.isEmpty(blogForm.getId())) {
			blog = blogService.addBlog(blogForm);
		} else {
			blog = blogService.updateBlog(blogForm);
			if(blog == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), 
				StringUtils.isEmpty(blogForm.getId()) ? ConstraintesJournal.ADMIN_ADD_BLOG : ConstraintesJournal.ADMIN_UPDATE_BLOG, blog.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/all/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteBlog(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Blog blog = blogService.deleteBlog(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_BLOG, blog.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/all/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteBlogs(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		blogService.deleteBlogs(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_BLOGS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/all/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllBlogs() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/autors", method = RequestMethod.GET)
	public String showAutors(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminBlogAutors";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/autors-load", method = RequestMethod.GET)
	public String loadCategories(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, 
			@RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", autorService.findAutorsList(search, sort, rows, page, hasDesc));
		return "adminListAutors";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/autors/new", method = RequestMethod.GET)
	public String showNewAutor(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("autor", new AutorForm());
		model.addAttribute("urlAutors", ConstraintesURL.getExplorerBlogAutorURL());
		return "adminBlogAutor";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param identify
	 * @return
	 */
	@RequestMapping(value = "/autors/check-identify", method = RequestMethod.GET)
	@ResponseBody
	public GenericResponse checkAutorIdentify(@RequestParam("identify") final String identify) {
		if(autorService.existsByIdentify(identify)) {
			throw new UrlUnavailableException("message.error.url");
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/autors/edit", method = RequestMethod.GET)
	public String showEditAutor(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AutorForm autorForm = autorService.readAutorForm(id);
		if(autorForm == null) {
			return "redirect:/admin/blog/autors?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("autor", autorForm);
		model.addAttribute("urlAutors", ConstraintesURL.getExplorerBlogAutorURL());
		return "adminBlogAutor";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param autorForm
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/autors/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAutor(@AuthenticationPrincipal final LocalUser localUser, @Valid final AutorForm autorForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		Autor autor = null;
		autorForm.setFile(file);
		if(StringUtils.isEmpty(autorForm.getId())) {
			autor = autorService.addAutor(autorForm);
		} else {
			autor = autorService.updateAutor(autorForm);
			if(autor == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), 
				autorForm.getId() == null ? ConstraintesJournal.ADMIN_ADD_AUTOR : ConstraintesJournal.ADMIN_UPDATE_AUTOR, autor.getAutorname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/autors/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAutor(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id) {
		final Autor autor = autorService.deleteAutor(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_AUTOR, autor.getAutorname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/autors/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAutors(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<Long> lines) {
		autorService.deleteAutors(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_AUTORS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/autors/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAutors(@AuthenticationPrincipal final LocalUser localUser) {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public String showBlogStats(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminBlogStats";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/stats-load", method = RequestMethod.GET)
	public String loadBlogStats(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc, @CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", blogService.findBlogStatsList(filter, search, sort, rows, page, hasDesc));
		return "adminListBlogstats";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/stats/edit", method = RequestMethod.GET)
	public String showEditStat(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final BlogStatForm blogStatForm = blogService.readBlogStatForm(id);
		if(blogStatForm == null) {
			return "redirect:/admin/blog/stats?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("blogStats", blogStatForm);
		return "adminBlogStat";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param blogStatForm
	 * @return
	 */
	@RequestMapping(value = "/stats/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveStats(@AuthenticationPrincipal final LocalUser localUser, @Valid final BlogStatForm blogStatForm) {
		final Blog blog = blogService.updateBlogStat(blogStatForm);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_STATE_BLOG, blog.getTitle()));
		return new GenericResponse("success");
	}
	
}
