package com.rinitec.algerieoffice.web.controllers.company;

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

import com.rinitec.algerieoffice.enums.LiveType;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.company.posts.IPostService;
import com.rinitec.algerieoffice.services.company.tools.ISettingService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.MaxPlanException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.posts.CategoryForm;
import com.rinitec.algerieoffice.web.form.company.posts.PostForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnLiveEvent;
import com.rinitec.algerieoffice.web.modal.premium.PremiumPost;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/company/posts")
public class CompanyPostsController {

	private IAttributeService attributeService;
	private IPremiumService premiumService;
	private IPostService postService;
	private ISettingService settingService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyPostsController(IAttributeService attributeService, IPremiumService premiumService, IPostService postService, 
			ISettingService settingService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.premiumService = premiumService;
		this.postService = postService;
		this.settingService = settingService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String showPosts(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_POST1, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_POST1));
		model.addAttribute("choseCategories", postService.findAllCategory(localUser.getCompanyId()));
		model.addAttribute("countTrashed", postService.countTrashedPost(localUser.getCompanyId()));
		return "companyPostsPosts";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
	@RequestMapping(value = "/all-load", method = RequestMethod.GET)
	public String loadPosts(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_POST1, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", postService.findPostsList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListPosts";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String showNewPost(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final PremiumPost premiumPost = premiumService.parsePremiumPost(companyId, postService.countPost(companyId));
		attributeService.attributeCompany(model, config, localUser);
		if(!premiumPost.isHasConsumer()) {
			model.addAttribute("choseCategories", postService.findAllCategory(companyId));
			model.addAttribute("urlPosts", ConstraintesURL.URL_POSTS.concat("/"));
		}
		model.addAttribute("premium", premiumPost);
		model.addAttribute("post", new PostForm(companyId, settingService.readService(companyId)));
		return "companyPostsPost";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String showEditPost(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam(name = "id", required = false) final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/posts/all";
		}
		final PostForm postForm = postService.readPostForm(id, localUser.getCompanyId());
		if(postForm == null) {
			return "redirect:/company/posts/all?notFound=true";
		}
		final Long companyId = localUser.getCompanyId();
		final PremiumPost premiumPost = premiumService.parsePremiumPost(companyId, 0L);
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("post", postForm);
		model.addAttribute("premium", premiumPost);
		model.addAttribute("choseCategories", postService.findAllCategory(companyId));
		model.addAttribute("urlPosts", ConstraintesURL.URL_POSTS.concat("/"));
		return "companyPostsPost";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * @param request
	 * @param postForm
	 * @param files
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse savePost(final HttpServletRequest request, @Valid final PostForm postForm, 
			@RequestParam(name = "files", required = false) final MultipartFile[] files, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!postForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final PremiumPost premiumPost = premiumService.parsePremiumPost(postForm.getCompanyId(), postService.countPost(postForm.getCompanyId()));
		postForm.setFiles(files);
		if(StringUtils.isEmpty(postForm.getId())) {
			if(premiumPost.isHasConsumer()) {
				throw new MaxPlanException("message.plan.post");
			}
			final Post post = postService.addPost(postForm, premiumPost.getMaxKeywords(), localUser.getUserId());
			if(post.getHasPublished()) {
				eventPublisher.publishEvent(new OnLiveEvent(post.getCompanyId(), post.getIdentify(), LiveType.addPost, request));
			}
		} else {
			final Post post = postService.updatePost(postForm, premiumPost.getMaxKeywords(), localUser.getUserId());
			if(post == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(postForm.getId()) ? ConstraintesJournal.COMPANY_ADD_POST : ConstraintesJournal.COMPANY_UPDATE_POST, postForm.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/all/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deletePost(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Post post = postService.trashPost(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_POST, post.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/all/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deletePosts(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		postService.trashPosts(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_LINES_POST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/all/delete-all", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAllPosts(@AuthenticationPrincipal final LocalUser localUser) {
		postService.trashAllPosts(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_ALL_POST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public String showCategories(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_POST2, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_POST2));
		model.addAttribute("filterCategories", postService.findAllFilterCategory(localUser.getCompanyId()));
		return "companyPostsCategories";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
	@RequestMapping(value = "/categories-load", method = RequestMethod.GET)
	public String loadCategories(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_POST2, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", postService.findCategoriesList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListCategories";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/categories/new", method = RequestMethod.GET)
	public String showNewCategory(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("category", new CategoryForm(localUser.getCompanyId()));
		model.addAttribute("choseCategories", postService.findAllChoseCategory(localUser.getCompanyId(), null));
		model.addAttribute("urlCategories", ConstraintesURL.URL_CATEGORIES);
		return "companyPostsCategory";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/categories/edit", method = RequestMethod.GET)
	public String showEditCategory(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam(name = "id", required = false) final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/posts/categories";
		}
		final CategoryForm categoryForm = postService.readCategoryForm(id, localUser.getCompanyId());
		if(categoryForm == null) {
			return "redirect:/company/posts/categories?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("category", categoryForm);
		model.addAttribute("choseCategories", postService.findAllChoseCategory(localUser.getCompanyId(), categoryForm.getId()));
		model.addAttribute("urlCategories", ConstraintesURL.URL_CATEGORIES);
		return "companyPostsCategory";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param categoryForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/categories/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveCategory(@Valid final CategoryForm categoryForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!categoryForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		if(StringUtils.isEmpty(categoryForm.getId())) {
			postService.addCategory(categoryForm);
		} else {
			final Category category = postService.updateCategory(categoryForm);
			if(category == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(categoryForm.getId()) ? ConstraintesJournal.COMPANY_ADD_CATEGORY : ConstraintesJournal.COMPANY_UPDATE_CATEGORY, categoryForm.getName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/categories/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteCategory(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Category category = postService.deleteCategory(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_CATEGORY, category.getName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/categories/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteCategories(@RequestParam("lines[]") final List<String> lines,
			@AuthenticationPrincipal final LocalUser localUser) {
		postService.deleteCategories(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_CATEGORY, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/categories/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllCategories(@AuthenticationPrincipal final LocalUser localUser) {
		postService.deleteAllCategories(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_CATEGORY, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/statistic", method = RequestMethod.GET)
	public String showStatistic(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_POST3, defaultValue = "") final String table) {
		final Long companyId = localUser.getCompanyId();
		attributeService.attributeCompany(model, config, localUser);
		if(premiumService.hasPremium(companyId)) {
			model.addAttribute("desc", true);
			model.addAttribute("refering", postService.readAnalyticPost(companyId));
			model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_POST3));
		}
		return "companyPostsStatistic";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param fl
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/statistic-load", method = RequestMethod.GET)
	public String loadStatistic(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String fl, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_POST1, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final Boolean filter = !StringUtils.isEmpty(fl) ? fl.equals("true") : null;
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", postService.findPostStatsList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListStatistic";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/statistic-access", method = RequestMethod.GET)
	public String loadStatisticAccess(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		model.addAttribute("listAccess", postService.findPostAccessList(localUser.getCompanyId(), 20));
		return "companyListStatisticAccess";
	}
	
}
