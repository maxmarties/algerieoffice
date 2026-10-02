package com.rinitec.algerieoffice.web.controllers.admin;

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

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Report;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Rate;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.feedback.IAdmFeedbackService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/feedback")
public class AdminFeedbackController {

	private IAttributeService attributeService;
	private IAdmFeedbackService feedbackService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminFeedbackController(IAttributeService attributeService, IAdmFeedbackService feedbackService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.feedbackService = feedbackService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/reports", method = RequestMethod.GET)
	public String showReports(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminFeedbackReoprts";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/reports-load", method = RequestMethod.GET)
	public String loadReports(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", feedbackService.findAdmReportList(filter, search, sort, rows, page, hasDesc));
		return "adminListReports";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/reports/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateReport(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Report report = feedbackService.validateReport(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_VALIDATE_REPORT, report.getReason()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/reports/lock", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse lockReport(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long companyId) {
		final Company company = feedbackService.lockCompnyReport(companyId, true);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_LOCK_COMPANY, company.getTradename()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/reports/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteReport(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Report report = feedbackService.deleteReport(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_REPORT, report.getReason()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/reports/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteReports(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		feedbackService.deleteReports(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_REPORTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/reports/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllReports() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/rates", method = RequestMethod.GET)
	public String showRates(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminFeedbackRates";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/rates-load", method = RequestMethod.GET)
	public String loadRates(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", feedbackService.findAdmRateList(filter, search, sort, rows, page, hasDesc));
		return "adminListRates";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/rates/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateRate(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Rate rate = feedbackService.validateRate(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_VALIDATE_RATE, rate.getReason()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/rates/lock", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse lockRate(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long userId) {
		final User user = feedbackService.lockUserRate(userId, true);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_LOCK_USER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/rates/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteRate(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Rate rate = feedbackService.deleteRate(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_RATE, rate.getReason()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/rates/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteRates(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		feedbackService.deleteRates(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_RATES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/rates/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllRates() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/locks", method = RequestMethod.GET)
	public String showLocks(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminFeedbackLocks";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/locks-load", method = RequestMethod.GET)
	public String loadLocks(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", feedbackService.findAdmLockList(search, sort, rows, page, hasDesc));
		return "adminListLocks";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/locks/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteLock(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id) {
		final Company company = feedbackService.lockCompnyReport(id, false);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UNLOCK_COMPANY, company.getTradename()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/locks/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteLocks(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<Long> lines) {
		feedbackService.unlockCompniesReport(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UNLOCK_COMPANIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/locks/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllLocks() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/blocks", method = RequestMethod.GET)
	public String showBlocks(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminFeedbackBlocks";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/blocks-load", method = RequestMethod.GET)
	public String loadBlocks(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", feedbackService.findAdmBlockList(search, sort, rows, page, hasDesc));
		return "adminListBlocks";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/blocks/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteBlock(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id) {
		final User user = feedbackService.lockUserRate(id, false);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UNLOCK_USER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/blocks/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteBlocks(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<Long> lines) {
		feedbackService.unlockUsersReport(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UNLOCK_USERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/blocks/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllBlocks() {
		throw new AccessAuthorityException();
	}
	
}
