package com.rinitec.algerieoffice.web.controllers.publics;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.forums.ITopicService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.MaxPlanException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.forums.SearchForumForm;
import com.rinitec.algerieoffice.web.form.forums.SignalForm;
import com.rinitec.algerieoffice.web.form.forums.TopicForm;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.Langage;
import com.rinitec.algerieoffice.web.modal.forums.TopicInbox;
import com.rinitec.algerieoffice.web.modal.forums.TopicMember;

@Controller
@RequestMapping(value = "/forums")
public class ForumsController {

	private IAttributeService attributeService;
	private ITopicService topicService;
	
	@Autowired
	public ForumsController(IAttributeService attributeService, ITopicService topicService) {
		this.attributeService = attributeService;
		this.topicService = topicService;
	}
	
	private final void updateLanguage(final HttpServletRequest request, final HttpServletResponse response, final Model model, final String language) {
		if(!RequestContextUtils.getLocale(request).getLanguage().equalsIgnoreCase(language)) {
			final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("langage", new Langage(language));
		}
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param cat
	 * @param token
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showForums(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam(name = "category", required = false) final Integer cat, @RequestParam(name = "token", required = false) final String token, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentUser currentUser = attributeService.attributeUser(model, localUser);
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final Integer category = cat != null && cat >= 0 && cat <= ConstraintesForm.COUNT_FAMILY_TOPIC ? cat : 0;
		model.addAttribute("hasLoading", true);
		model.addAttribute("token", token);
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("searchForum", new SearchForumForm(currentUser.getUserId(), token, category, currentConfig.parseDefaultResult()));
		return "forumsHome";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
	public String showInbox(final HttpServletRequest request, final HttpServletResponse response, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@PathVariable("id") final String id, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final TopicInbox inbox = topicService.findTopicInbox(id, localUser.getUserId());
		if(inbox == null) {
			return "redirect:/forums?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("inbox", inbox);
		model.addAttribute("signal", new SignalForm(localUser.getUserId()));
		model.addAttribute("selectedId", request.getParameter("selectedId"));
		if(inbox.isHasQuiz()) {
			model.addAttribute("quiz", topicService.readTopicQuizVote(inbox.getId(), localUser.getUserId()));
		}
		updateLanguage(request, response, model, inbox.getLanguage());
		return "forumsInbox";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value = "/comment/{commentId}", method = RequestMethod.GET)
	public String showCommentInbox(@PathVariable("commentId") final String commentId) {
		final TopicComment topicComment = topicService.findTopicComment(commentId);
		if(topicComment == null) {
			return "redirect:/forums?notFound=true";
		}
		return "redirect:/forums/topic/".concat(topicComment.getTopicId().toString()).concat("?selectedId=").concat(topicComment.getId().toString());
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String showNewTopic(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("topic", new TopicForm(localUser.getUserId(), RequestContextUtils.getLocale(request).getLanguage()));
		return "forumsTopic";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String showEditTopic(final Model model, @PathVariable("id") final String id, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final TopicForm topicForm = topicService.readTopicForm(id, localUser.getUserId());
		if(topicForm == null) {
			return "redirect:/forums?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("topic", topicForm);
		return "forumsTopic";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param topicForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveTopic(@Valid final TopicForm topicForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!topicForm.getUserId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		Topic topic = null;
		if(StringUtils.isEmpty(topicForm.getId())) {
			if(topicService.hasMaxTopic(localUser.getUserId())) {
				throw new MaxPlanException("message.plan.topic");
			}
			topic = topicService.addTopic(topicForm);
		} else {
			topic = topicService.updateTopic(topicForm);
			if(topic == null) {
				throw new AccessAuthorityException();
			}
		}
		//TODO NOTIFICATION FAVORITE
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteTopic(@PathVariable("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		topicService.deleteTopic(id, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param userId
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public String showUser(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("userId") final Long userId, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final TopicMember topicMember = topicService.findTopicMember(userId);
		if(topicMember == null) {
			return "redirect:/forums?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("member", topicMember);
		model.addAttribute("topics", topicService.findTopicUserList(userId, localUser.getUserId(), 100));
		return "forumsMember";
	}
	
}
