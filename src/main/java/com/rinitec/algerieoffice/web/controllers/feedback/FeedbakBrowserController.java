package com.rinitec.algerieoffice.web.controllers.feedback;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.enums.TalkType;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Report;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.blacklist.IBlacklistCompanyService;
import com.rinitec.algerieoffice.services.user.feedback.IFeedbackService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.feedback.AppointmentForm;
import com.rinitec.algerieoffice.web.form.feedback.NoticeForm;
import com.rinitec.algerieoffice.web.form.feedback.ReportForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnTalkEvent;

@Controller
@RequestMapping(value = "/feedback/browser")
public class FeedbakBrowserController {

	private IFeedbackService feedbackService;
	private IBlacklistCompanyService blacklistService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public FeedbakBrowserController(IFeedbackService feedbackService, IBlacklistCompanyService blacklistService, ApplicationEventPublisher eventPublisher) {
		this.feedbackService = feedbackService;
		this.blacklistService = blacklistService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param accountId
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/favorite-account", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse favoriteAccount(final HttpServletRequest request, @RequestParam("accountId") final Long accountId, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(accountId.equals(localUser.getUserId())) {
			throw new AccessLeaderException("message.error.profileFavorite");
		}
		final String username = feedbackService.postOrUpdateFavoriteAccount(localUser.getUserId(), accountId);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_MEMBER, username));
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), accountId, null, NotificationType.favoriteAccount, request));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/favorite-company", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse favoriteCompany(@RequestParam("companyId") final Long companyId,
			@RequestParam(name = "type", required = false) final Integer type, @AuthenticationPrincipal final LocalUser localUser) {
		if(companyId.equals(localUser.getCompanyId())) {
			throw new AccessLeaderException("message.error.favorite");
		}
		final String tradename = feedbackService.postOrUpdateFavoriteCompany(localUser.getUserId(), companyId, type);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_COMPANY, tradename));
		eventPublisher.publishEvent(new OnTalkEvent(companyId, TalkType.favorite));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param documentId
	 * @param type
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/favorite-document", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse favoriteDocument(@RequestParam("companyId") final Long companyId, @RequestParam("documentId") final String documentId, 
			@RequestParam("type") final String type, @AuthenticationPrincipal final LocalUser localUser) {
		if(companyId.equals(localUser.getCompanyId())) {
			throw new AccessLeaderException("message.error.postFavorite");
		}
		final String title = feedbackService.postOrUpdateFavoriteDocument(localUser.getUserId(), documentId, type);
		switch(type) {
		case "post": eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_POST, title)); break;
		case "annonce": eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_ANNONCE, title)); break;
		case "event": eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_EVENT, title)); break;
		default: eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_ADD_EMPLOYE, title));
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param liked
	 * @param note
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/evaluate-company", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse evaluateCompany(@RequestParam("companyId") final Long companyId, @RequestParam("liked") final boolean liked, 
			@RequestParam("note") final Integer note, @AuthenticationPrincipal final LocalUser localUser) {
		if(companyId.equals(localUser.getCompanyId())) {
			throw new AccessLeaderException("message.error.evaluate");
		}
		final String tradename = feedbackService.postOrUpdateEvaluation(localUser.getUserId(), companyId, liked, note);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_EVALUATE_COMPANY, tradename));
		eventPublisher.publishEvent(new OnTalkEvent(companyId, TalkType.rate));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param noticeForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notice-company", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse noticeCompany(@Valid final NoticeForm noticeForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(noticeForm.getCompanyNotice().equals(localUser.getCompanyId())) {
			throw new AccessLeaderException("message.error.notice");
		}
		if(blacklistService.HasUserBlocked(localUser.getUserId(), noticeForm.getCompanyNotice())) {
			throw new AccessLeaderException("message.error.blackcompany");
		}
		final String tradename = feedbackService.addNotice(localUser.getUserId(), noticeForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_NOTICE_COMPANY, tradename));
		eventPublisher.publishEvent(new OnTalkEvent(noticeForm.getCompanyNotice(), TalkType.notice));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param appointmentForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appoint-company", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse appointCompany(@Valid final AppointmentForm appointmentForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(appointmentForm.getCompanyAppoint().equals(localUser.getCompanyId())) {
			throw new AccessLeaderException("message.error.appoint");
		}
		if(blacklistService.HasUserBlocked(localUser.getUserId(), appointmentForm.getCompanyAppoint())) {
			throw new AccessLeaderException("message.error.blackcompany");
		}
		final String tradename = feedbackService.addAppointment(localUser.getUserId(), appointmentForm);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_APPOINT_COMPANY, tradename));
		eventPublisher.publishEvent(new OnTalkEvent(appointmentForm.getCompanyAppoint(), TalkType.appointment));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param reportForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/report-company", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse reportCompany(final HttpServletRequest request, @Valid final ReportForm reportForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		if(reportForm.getCompanyReport().equals(localUser.getCompanyId())) {
			throw new AccessLeaderException("message.error.report");
		}
		reportForm.setFile(file);
		final Report report = feedbackService.addReport(localUser.getUserId(), reportForm);
		eventPublisher.publishEvent(new OnNotificationEvent(report.getCompanyId(), null, null, NotificationType.report, request));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param function
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/collaborate-company", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse collaborateCompany(@RequestParam("companyId") final Long companyId, @RequestParam("function") final String function, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(companyId.equals(localUser.getCompanyId())) {
			throw new AccessLeaderException("message.error.leader");
		}
		final String tradename = feedbackService.addCollaborator(localUser.getUserId(), companyId, function);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_COLLABORATE_COMPANY, tradename));
		eventPublisher.publishEvent(new OnTalkEvent(companyId, TalkType.collaborate));
		return new GenericResponse("success");
	}
	
}
