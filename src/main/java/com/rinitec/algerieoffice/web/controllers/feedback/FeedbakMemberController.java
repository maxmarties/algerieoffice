package com.rinitec.algerieoffice.web.controllers.feedback;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityComment;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Rate;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.blacklist.IBlacklistMemberService;
import com.rinitec.algerieoffice.services.user.feedback.IFeedbackService;
import com.rinitec.algerieoffice.services.user.feedback.ILikeService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.feedback.LockForm;
import com.rinitec.algerieoffice.web.form.feedback.RateForm;
import com.rinitec.algerieoffice.web.form.user.repport.CommentForm;
import com.rinitec.algerieoffice.web.listener.events.OnCommentEvent;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;

@Controller
@RequestMapping(value = "/feedback/members")
public class FeedbakMemberController {

	private ILikeService likeService;
	private IFeedbackService feedbackService;
	private IBlacklistMemberService blacklistService;
	private ApplicationEventPublisher eventPublisher;
	
	public FeedbakMemberController(ILikeService likeService, IFeedbackService feedbackService, IBlacklistMemberService blacklistService, 
			ApplicationEventPublisher eventPublisher) {
		this.likeService = likeService;
		this.feedbackService = feedbackService;
		this.blacklistService = blacklistService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param blogId
	 * @return
	 */
	@RequestMapping(value = "/like-blog", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse likeBlog(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("blogId") final String blogId) {
		final Blog blog = likeService.postOrRemoveBlogLike(localUser.getUserId(), blogId);
		if(blog != null) {
			eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_LIKE_BLOG, blog.getTitle()));
		}
		return new GenericResponse("success");
	}

	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param autorId
	 * @param actuId
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/comments-load", method = RequestMethod.GET)
	public String loadComments(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam("actuId") final String actuId, 
			@RequestParam("page") final Integer page, @RequestParam("limit") final Integer limit) {
		model.addAttribute("currPage", page);
		model.addAttribute("currUserId", localUser.getUserId());
		model.addAttribute("list", likeService.findActualityCommentList(localUser.getUserId(), actuId, page, limit));
		return "screenMarketplaceComments";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param commentId
	 * @return
	 */
	@RequestMapping(value = "/like-comment", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse likeComment(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("commentId") final String commentId) {
		final Long userId = localUser.getUserId();
		final Object[] result = likeService.postOrRemoveCommentLike(userId, commentId);
		if(result != null) {
			final ActualityComment actualityComment = (ActualityComment) result[0];
			if(actualityComment != null && !actualityComment.getUserId().equals(userId)) {
				final Long autorId = (Long) result[1];
				eventPublisher.publishEvent(new OnNotificationEvent(userId, actualityComment.getUserId(), actualityComment.getActualityId(), 
						autorId != null && autorId.equals(userId) ? NotificationType.likeComment : NotificationType.likedComment, request));
			}
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param actuId
	 * @return
	 */
	@RequestMapping(value = "/like-actu", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse likeActu(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("actuId") final String actuId) {
		final Actuality actuality = likeService.postOrRemoveActualityLike(localUser.getUserId(), actuId);
		if(actuality != null && !actuality.getAutorId().equals(localUser.getUserId())) {
			eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_LIKE_ACTU, actuality.getTitle()));
			eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), actuality.getAutorId(), actuality.getId(), NotificationType.likeActu, request));
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param commentForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/comment-actu", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse commentActu(final HttpServletRequest request, @Valid final CommentForm commentForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!commentForm.getUserId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		final Object[] result = likeService.addActualityComment(commentForm);
		if(result == null) {
			throw new AccessAuthorityException();
		}
		final Actuality actuality = (Actuality) result[0];
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_COMMENT_ACTU, actuality.getTitle()));
		eventPublisher.publishEvent(new OnCommentEvent(localUser.getUser(), actuality.getId(), request));
		if(!actuality.getAutorId().equals(localUser.getUserId())) {
			eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), actuality.getAutorId(), actuality.getId(), NotificationType.commentActu, request));
		}
		return new GenericResponse("success", (String) result[1]);
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param rateForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/rate-member", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse rateMember(final HttpServletRequest request, @Valid final RateForm rateForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(rateForm.getMemberRate().equals(localUser.getUserId())) {
			throw new AccessLeaderException("message.error.authority");
		}
		final Rate rate = feedbackService.addRate(localUser.getUserId(), rateForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_RATE_MEMBER, null));
		eventPublisher.publishEvent(new OnNotificationEvent(rate.getMemberId(), null, null, NotificationType.rate, request));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lockForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/lock-member", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse lockMember(@Valid final LockForm lockForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(lockForm.getMemberLock().equals(localUser.getUserId())) {
			throw new AccessLeaderException("message.error.authority");
		}
		blacklistService.postOrUpdateBlacklistMember(localUser.getUserId(), lockForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_LOCK_MEMBER, null));
		return new GenericResponse("success");
	}
	
}
