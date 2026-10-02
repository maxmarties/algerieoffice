package com.rinitec.algerieoffice.web.controllers.feedback;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.services.admins.ads.IAdmNewsletterService;
import com.rinitec.algerieoffice.services.admins.blog.IBlogSearchService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.validator.EmailValidator;

@Controller
@RequestMapping(value = "/feedback/blog")
public class FeedbakBlogController {

	private IBlogSearchService blogSearchService;
	private IAdmNewsletterService newsletterService;
	
	@Autowired
	public FeedbakBlogController(IBlogSearchService blogSearchService, IAdmNewsletterService newsletterService) {
		this.blogSearchService = blogSearchService;
		this.newsletterService = newsletterService;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/newsletter", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse registerNewsletter(@RequestParam("email") final String email) {
		if(email.length() > 100 || !Pattern.compile(EmailValidator.EMAIL_PATTERN).matcher(email).matches()) {
			throw new InvalidImageException("message.input.invalid");
		}
		newsletterService.registerNewsletter(email);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param key
	 * @param autorId
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public String loadBlogList(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("tg") final String key, @RequestParam("id") final Long autorId, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final String keyword = StringUtils.isEmpty(key) ? null : key.replaceAll("\\+", " ");
		model.addAttribute("list", blogSearchService.findBlogExplorerList(filter, search, keyword, autorId, sort, rows, page, hasDesc));
		return "blogFeedbackList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param blogId
	 * @return
	 */
	@RequestMapping(value = "/popular", method = RequestMethod.GET)
	public String loadBlogPopular(final Model model, @RequestParam("id") final String blogId) {
		model.addAttribute("list", blogSearchService.findLastBlogMini(blogId, 1, 5));
		return "blogFeedbackPopular";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param blogId
	 * @return
	 */
	@RequestMapping(value = "/recent", method = RequestMethod.GET)
	public String loadBlogRecent(final Model model, @RequestParam("id") final String blogId) {
		model.addAttribute("list", blogSearchService.findLastBlogMini(blogId, 2, 5));
		return "blogFeedbackPopular";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param blogId
	 * @param category
	 * @param language
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/simultude", method = RequestMethod.GET)
	public String loadBlogSimultude(final Model model, @RequestParam("id") final String blogId, @RequestParam("ct") final int category, 
			@RequestParam("lg") final String language, @RequestParam("key") final String key) {
		final String keyword = StringUtils.isEmpty(key) ? null : key.replaceAll("\\+", ",");
		model.addAttribute("list", blogSearchService.findSimultudeBlogMini(blogId, category, language, keyword, 3));
		return "blogFeedbackSimultude";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public String loadBlogNews(final Model model) {
		model.addAttribute("list", blogSearchService.findBlogNewsMini(5));
		return "blogFeedbackNews";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param blogId
	 * @return
	 */
	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse followBlog(@RequestParam("blogId") final String blogId) {
		blogSearchService.incrementBlogFollow(blogId);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/explorer", method = RequestMethod.GET)
	public String loadBlogExplorer(final Model model) {
		model.addAttribute("list", blogSearchService.findExplorerBlogMini(3));
		return "blogFeedbackExplorer";
	}
	
}
