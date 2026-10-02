package com.rinitec.algerieoffice.web.controllers.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.user.feedback.ICommunicationService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/user/communication")
public class UserCommunicationController {

	private IAttributeService attributeService;
	private ICommunicationService communicationService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UserCommunicationController(IAttributeService attributeService, ICommunicationService communicationService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.communicationService = communicationService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/notices", method = RequestMethod.GET)
	public String showNotices(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION01, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION01));
		return "userCommunicationNotices";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
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
	@RequestMapping(value = "/notices-load", method = RequestMethod.GET)
	public String loadNotices(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION01, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findUserNoticeList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListNotices";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notices/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteNotice(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteUserNotice(id, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_NOTICE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notices/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteNotices(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteUserNotices(lines, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_NOTICES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notices/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllNotices(@AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAllUserNotices(localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLNOTICE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/appointments", method = RequestMethod.GET)
	public String showAppointments(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION02, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION02));
		return "userCommunicationAppoints";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
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
	@RequestMapping(value = "/appointments-load", method = RequestMethod.GET)
	public String loadAppointments(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION02, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findUserAppointList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListAppoints";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appointments/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAppointment(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteUserAppointment(id, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_APPOINT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appointments/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAppointments(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteUserAppointments(lines, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_APPOINTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appointments/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAppointments(@AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAllUserAppointments(localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLAPPOINT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/evaluations", method = RequestMethod.GET)
	public String showEvaluations(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION03, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION03));
		return "userCommunicationEvaluations";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
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
	@RequestMapping(value = "/evaluations-load", method = RequestMethod.GET)
	public String loadEvaluations(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION03, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findUserEvaluationList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListEvaluations";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/evaluations/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEvaluation(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteUserEvaluation(id, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EVALUATION, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/evaluations/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEvaluations(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteUserEvaluations(lines, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EVALUATIONS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/evaluations/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEvaluations(@AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAllUserEvaluations(localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLEVALUATION, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/comments", method = RequestMethod.GET)
	public String showComments(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION05, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION05));
		return "userCommunicationComments";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
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
	@RequestMapping(value = "/comments-load", method = RequestMethod.GET)
	public String loadComments(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION05, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findUserCommentList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListComments";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/comments/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteComment(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final Actuality actuality = communicationService.deleteUserComment(id, localUser.getUserId());
		if(actuality != null && !actuality.getAutorId().equals(localUser.getUserId())) {
			eventPublisher.publishEvent(new OnNotificationEvent(localUser.getUserId(), actuality.getAutorId(), actuality.getId(), NotificationType.commentRemove, request));
		}
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_COMMENT, actuality != null ? actuality.getTitle() : null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/comments/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteComments(@RequestParam("lines[]") final List<String> lines) {
		throw new AccessLeaderException("message.error.deleteall");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/comments/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllComments() {
		throw new AccessLeaderException("message.error.deleteall");
	}
	
}
