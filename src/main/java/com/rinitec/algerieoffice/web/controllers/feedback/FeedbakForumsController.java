package com.rinitec.algerieoffice.web.controllers.feedback;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicSignal;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.forums.ITopicExplorerService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.ujson.TopicQuizResponse;
import com.rinitec.algerieoffice.ujson.TopicReplyResponse;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.forums.SearchForumForm;
import com.rinitec.algerieoffice.web.form.forums.SignalForm;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReplyEvent;

@Controller
@RequestMapping(value = "/feedback/forums")
public class FeedbakForumsController {
	
	private ITopicExplorerService topicExplorerService;
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	public FeedbakForumsController(ITopicExplorerService topicExplorerService, ApplicationEventPublisher eventPublisher) {
		this.topicExplorerService = topicExplorerService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param topicQuizResponse
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/post-quiz", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveQuiz(final HttpServletRequest request, @RequestBody final TopicQuizResponse topicQuizResponse, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(topicQuizResponse.getUserId())) {
			throw new AccessAuthorityException();
		}
		topicExplorerService.addTopicVote(topicQuizResponse);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param topicReplyResponse
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/post-reply", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveReply(final HttpServletRequest request, @RequestBody final TopicReplyResponse topicReplyResponse, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(topicReplyResponse.getUserId())) {
			throw new AccessAuthorityException();
		}
		final Object[] result = topicExplorerService.addTopicComment(topicReplyResponse);
		final Topic topic = (Topic) result[0];
		final TopicComment topicComment = (TopicComment) result[1];
		eventPublisher.publishEvent(new OnReplyEvent(localUser.getUser(), topic, topicComment, request));
		if(!topic.getUserId().equals(localUser.getUserId())) {
			eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), topic.getUserId(), topic.getId(), 
					topicComment.isChildren() ? NotificationType.replyCommentTopic : NotificationType.replyTopic, request));
		}
		return new GenericResponse("success", topicComment.getId().toString());
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param topicReplyResponse
	 * @return
	 */
	@RequestMapping(value = "/like-reply", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse likeReply(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestBody final TopicReplyResponse topicReplyResponse) {
		if(!localUser.getUserId().equals(topicReplyResponse.getUserId())) {
			throw new AccessAuthorityException();
		}
		final Long userId = localUser.getUserId();
		final Object[] result = topicExplorerService.postOrRemoveCommentLike(userId, topicReplyResponse.getCommentId());
		if(result != null) {
			final TopicComment topicComment = (TopicComment) result[0];
			if(topicComment != null && !topicComment.getUserId().equals(userId)) {
				final Long autorId = (Long) result[1];
				eventPublisher.publishEvent(new OnNotificationEvent(userId, topicComment.getUserId(), topicComment.getId(), 
						autorId != null && autorId.equals(userId) ? NotificationType.likeTopicReply : NotificationType.likedTopicReply, request));
			}
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value = "/delete-reply", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteRelply(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("commentId") final String commentId) {
		topicExplorerService.deleteTopicComment(localUser.getUserId(), commentId);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param topicReplyResponse
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/like-topic", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse likeTopic(final HttpServletRequest request, @RequestBody final TopicReplyResponse topicReplyResponse, 
			@AuthenticationPrincipal final LocalUser localUser) {
		final Topic topic = topicExplorerService.postOrRemoveTopicLike(localUser.getUserId(), topicReplyResponse.getTopicId());
		if(topic != null && !topic.getUserId().equals(localUser.getUserId())) {
			eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), topic.getUserId(), topic.getId(), NotificationType.likeTopic, request));
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param topicId
	 * @return
	 */
	@RequestMapping(value = "/mark-topic", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse markTopic(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("topicId") final String topicId) {
		topicExplorerService.postOrRemoveTopicMark(localUser.getUserId(), topicId);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param signalForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/signal-topic", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse signalTopic(@Valid final SignalForm signalForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!signalForm.getUserSignal().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		final TopicSignal topicSignal = topicExplorerService.addTopicSignal(localUser.getUserId(), signalForm);
		System.out.println(topicSignal);
		//TODO NOTIFY ADMIN
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param topicId
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/load-replies", method = RequestMethod.GET)
	public String loadReplies(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam("topicId") final String topicId, 
			@RequestParam("page") final Integer page, @RequestParam("limit") final Integer limit) {
		model.addAttribute("currPage", page);
		model.addAttribute("currUserId", localUser.getUserId());
		model.addAttribute("list", topicExplorerService.findTopicCommentList(localUser.getUserId(), topicId, page, limit));
		return "forumsTopicComments";
	}

	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value = "/sub-replies", method = RequestMethod.GET)
	public String subReplies(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam("commentId") final String commentId) {
		model.addAttribute("currUserId", localUser.getUserId());
		model.addAttribute("list", topicExplorerService.findTopicRelpyCommentList(localUser.getUserId(), commentId, ConstraintesForm.MAX_COUNT_DATA));
		return "forumsTopicSubComments";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param searchForumForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/load-topics", method = RequestMethod.POST)
	public String loadTopics(final Model model, @Valid final SearchForumForm searchForumForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!searchForumForm.getUserId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		model.addAttribute("currUserId", localUser.getUserId());
		model.addAttribute("list", topicExplorerService.findTopicsList(searchForumForm));
		return "forumsTopicList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param topicId
	 * @return
	 */
	@RequestMapping(value = "/load-mini", method = RequestMethod.GET)
	public String loadMinies(final Model model, @RequestParam("topicId") final String topicId) {
		model.addAttribute("target", false);
		model.addAttribute("list", topicExplorerService.findTopicMiniList(topicId, 20));
		return "forumsTopicMini";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/load-explorer", method = RequestMethod.GET)
	public String loadExplorer(final Model model, @RequestParam("limit") final Integer limit) {
		model.addAttribute("target", true);
		model.addAttribute("list", topicExplorerService.findLastTopicMiniList(limit));
		return "forumsTopicMini";
	}
	
}
