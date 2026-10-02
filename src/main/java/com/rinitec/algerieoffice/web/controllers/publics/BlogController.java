package com.rinitec.algerieoffice.web.controllers.publics;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.blog.IBlogService;
import com.rinitec.algerieoffice.services.explorer.IExplorerPromoteService;
import com.rinitec.algerieoffice.services.user.feedback.ILikeService;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.listener.events.OnAccessBlogEvent;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.Langage;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogAutorMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogInbox;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {

	private IAttributeService attributeService;
	private IBlogService blogService;
	private ILikeService likeService;
	private IExplorerPromoteService explorerPromoteService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public BlogController(IAttributeService attributeService, IBlogService blogService, ILikeService likeService, 
			IExplorerPromoteService explorerPromoteService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.blogService = blogService;
		this.likeService = likeService;
		this.explorerPromoteService = explorerPromoteService;
		this.eventPublisher = eventPublisher;
	}
	
	@ModelAttribute("urlBlogFamilies")
	public String[] urlBlogFamilies() {
		return ConstraintesURL.URL_FAMILY_BLOG;
	}
	
	@ModelAttribute("countBlogFamilies")
	public List<String> countBlogFamilies() {
		return blogService.countFamilyBlog();
	}
	
	private final void updateLanguage(final HttpServletRequest request, final HttpServletResponse response, final Model model, final String language) {
		if(!RequestContextUtils.getLocale(request).getLanguage().equalsIgnoreCase(language)) {
			final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("langage", new Langage(language));
		}
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showBlogs(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("desc", true);
		model.addAttribute("hasLoading", true);
		model.addAttribute("mapsiteURL", ConstraintesURL.getMapsiteBlogURL());
		return "blogHome";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param familyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/categorie/{familyURL}", method = RequestMethod.GET)
	public String showFamily(final Model model, final Authentication authentication, @PathVariable("familyURL") final String familyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final int family = ConstraintesURL.getIndexFamily(familyURL);
		if(family == 0) {
			return "redirect:/blog/error/404";
		}
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("desc", true);
		model.addAttribute("category", family);
		model.addAttribute("categoryURL", familyURL);
		model.addAttribute("mapsiteURL", ConstraintesURL.getMapsiteBlogFamilyURL(familyURL));
		return "blogFamily";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param autorURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/auteurs/{autorURL}", method = RequestMethod.GET)
	public String showAutor(final Model model, final Authentication authentication, @PathVariable("autorURL") final String autorURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final BlogAutorMini autor = blogService.readAutor(autorURL);
		if(autor == null) {
			return "redirect:/blog/error/404";
		}
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("desc", true);
		model.addAttribute("autor", autor);
		model.addAttribute("mapsiteURL", ConstraintesURL.getMapsiteBlogAutorURL(autorURL));
		return "blogAutor";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param token
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/recherche/{token}", method = RequestMethod.GET)
	public String showSearch(final Model model, final Authentication authentication, @PathVariable("token") final String token, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final String search = token.replaceAll("\\+", " ");
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("desc", true);
		model.addAttribute("search", search);
		model.addAttribute("mapsiteURL", ConstraintesURL.getMapsiteBlogSearchURL(token));
		return "blogSearch";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param keyword
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/tag/{keyword}", method = RequestMethod.GET)
	public String showKeyword(final Model model, final Authentication authentication, @PathVariable("keyword") final String keyword, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final String key = keyword.replaceAll("\\+", " ");
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("desc", true);
		model.addAttribute("keyword", key);
		model.addAttribute("mapsiteURL", ConstraintesURL.getMapsiteBlogKeywordURL(keyword));
		return "blogKeyword";
	}

	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param articleURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{articleURL}", method = RequestMethod.GET)
	public String showArticle(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("articleURL") final String articleURL,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final BlogInbox inbox = blogService.readBlogInbox(articleURL);
		if(inbox == null) {
			return "redirect:/blog/error/404";
		}
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication, config);
		if(currentUser != null) {
			model.addAttribute("hasLiked", likeService.hasLikeBlog(currentUser.getUserId(), inbox.getId()));
		}
		model.addAttribute("inbox", inbox);
		model.addAttribute("mapsiteURL", ConstraintesURL.getMapsiteBlogArticelURL(articleURL));
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		updateLanguage(request, response, model, inbox.getLanguage());
		eventPublisher.publishEvent(new OnAccessBlogEvent(inbox.getId(), true));
		return "blogArticle";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/error/404", method = RequestMethod.GET)
	public String showError(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getMapsiteBlogURL());
		return "blogError";
	}
	
}
