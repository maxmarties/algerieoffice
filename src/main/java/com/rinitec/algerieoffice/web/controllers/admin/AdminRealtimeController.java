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

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Assist;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Contactus;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Deactivate;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Problem;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Testimonial;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.realtime.IAdmDeactivateService;
import com.rinitec.algerieoffice.services.admins.realtime.IAdmRepportService;
import com.rinitec.algerieoffice.services.admins.realtime.IContactusService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.admins.realtime.AdmTestimonialForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmChatbotDetail;
import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmContactDetail;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/realtime")
public class AdminRealtimeController {

	private IAttributeService attributeService;
	private IAdmDeactivateService deactivateService;
	private IAdmRepportService repportService;
	private IContactusService contactusService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminRealtimeController(IAttributeService attributeService, IAdmDeactivateService deactivateService, IAdmRepportService repportService, 
			IContactusService contactusService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.deactivateService = deactivateService;
		this.repportService = repportService;
		this.contactusService = contactusService;
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
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public String showCompanies(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminRealtimeCompanies";
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
	 * @return
	 */
	@RequestMapping(value = "/companies-load", method = RequestMethod.GET)
	public String loadCompanies(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", deactivateService.findDeactivateCompanyList(filter, search, sort, rows, page, hasDesc));
		return "adminListDeactivateCompanies";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/companies/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateDeactivateCompany(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Deactivate deactivate = deactivateService.validateDeactivate(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_CONSULTE_DEACTIVATE, deactivate.getObservation()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/companies/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteDeactivateCompany(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Deactivate deactivate = deactivateService.deleteDeactivateCompany(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_DEACTIVATE, deactivate.getObservation()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/companies/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteDeactivateCompanies(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		deactivateService.deleteDeactivateCompanies(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_DEACTIVATES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/companies/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllDeactivates() {
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
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String showUsers(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminRealtimeUsers";
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
	 * @return
	 */
	@RequestMapping(value = "/users-load", method = RequestMethod.GET)
	public String loadUsers(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", deactivateService.findDeactivateUserList(filter, search, sort, rows, page, hasDesc));
		return "adminListDeactivateUsers";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateDeactivateUser(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Deactivate deactivate = deactivateService.validateDeactivate(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_CONSULTE_USER_DEACTIVATE, deactivate.getObservation()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/restore", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse restoreDeactivateUser(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final User user = deactivateService.restoreDeactivate(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_RESTORE_USER_DEACTIVATE, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteDeactivateUser(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final User user = deactivateService.deleteDeactivateUser(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_USER_DEACTIVATE, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/users/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteDeactivateUsers(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		deactivateService.deleteDeactivateUsers(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_USER_DEACTIVATES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/users/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllUserDeactivates() {
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
	@RequestMapping(value = "/testimonials", method = RequestMethod.GET)
	public String showTestimonials(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminRealtimeTestimonials";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param fl
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/testimonials-load", method = RequestMethod.GET)
	public String loadTestimonials(final Model model, @RequestParam("fl") final String fl, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final Boolean filter = !StringUtils.isEmpty(fl) ? fl.equals("true") : null;
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", repportService.findTestimonialList(filter, search, sort, rows, page, hasDesc));
		return "adminListTestimonials";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/testimonials/new", method = RequestMethod.GET)
	public String showNewTestimonial(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("testimonial", new AdmTestimonialForm());
		return "adminRealtimeTestimonial";
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
	@RequestMapping(value = "/testimonials/edit", method = RequestMethod.GET)
	public String showEditTestimonial(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmTestimonialForm testimonialForm = repportService.readTestimonialForm(id);
		if(testimonialForm == null) {
			return "redirect:/admin/realtime/testimonials?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("testimonial", testimonialForm);
		return "adminRealtimeTestimonial";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param testimonialForm
	 * @return
	 */
	@RequestMapping(value = "/testimonials/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveTestimonial(@AuthenticationPrincipal final LocalUser localUser, @Valid final AdmTestimonialForm testimonialForm) {
		Testimonial testimonial = null;
		if(StringUtils.isEmpty(testimonialForm.getId())) {
			testimonial = repportService.addTestimonial(testimonialForm, localUser.getUserId());
		} else {
			testimonial = repportService.updateTestimonial(testimonialForm);
			if(testimonial == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), StringUtils.isEmpty(testimonialForm.getId()) ? ConstraintesJournal.ADMIN_ADD_TESTIMONIAL 
				: ConstraintesJournal.ADMIN_UPDATE_TESTIMONIAL, testimonial.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/testimonials/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteTestimonial(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Testimonial testimonial = repportService.deleteTestimonial(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_TESTIMONIAL, testimonial.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/testimonials/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteTestimonials(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		repportService.deleteTestimonials(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_TESTIMONIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/testimonials/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllTestimonials() {
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
	@RequestMapping(value = "/assists", method = RequestMethod.GET)
	public String showAssists(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminRealtimeAssists";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param fl
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/assists-load", method = RequestMethod.GET)
	public String loadAssists(final Model model, @RequestParam("fl") final String fl, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", repportService.findAssistList(search, sort, rows, page, hasDesc));
		return "adminListAssists";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/assists/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAssist(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Assist assist = repportService.deleteAssist(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_ASSIST, assist.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/assists/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAssists(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		repportService.deleteAssists(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_ASSISTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/assists/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAssists() {
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
	@RequestMapping(value = "/problems", method = RequestMethod.GET)
	public String showProblems(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminRealtimeProblems";
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
	@RequestMapping(value = "/problems-load", method = RequestMethod.GET)
	public String loadProblems(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", repportService.findProblemList(filter, search, sort, rows, page, hasDesc));
		return "adminListProblems";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/problems/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteProblem(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Problem problem = repportService.deleteProblem(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_PROBLEM, problem.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/problems/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteProblems(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		repportService.deleteProblems(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_PROBLEMS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/problems/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllProblems() {
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
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public String showContacts(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminRealtimeContacts";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param fl
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/contacts-load", method = RequestMethod.GET)
	public String loadContacts(final Model model, @RequestParam("fl") final String fl, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc, @CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final Boolean filter = !StringUtils.isEmpty(fl) ? fl.equals("true") : null;
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", contactusService.findContactusList(filter, search, sort, rows, page, hasDesc));
		return "adminListContactus";
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
	@RequestMapping(value = "/contacts/edit", method = RequestMethod.GET)
	public String showContactDetail(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmContactDetail contactDetail = contactusService.readAdmContactDetail(id);
		if(contactDetail == null) {
			return "redirect:/admin/realtime/contacts?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("contact", contactDetail);
		return "adminRealtimeContact";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/contacts/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteContact(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Contactus contactus = contactusService.deleteContactus(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CONTACTUS, contactus.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/contacts/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteContacts(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		contactusService.deleteContactsus(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CONTACTUSS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/contacts/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllContacts() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/chatbots", method = RequestMethod.GET)
	public String showChatbots(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminRealtimeChatbots";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * @param model
	 * @param fl
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/chatbots-load", method = RequestMethod.GET)
	public String loadChatbots(final Model model, @RequestParam("fl") final String fl, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc, @CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final Boolean filter = !StringUtils.isEmpty(fl) ? fl.equals("true") : null;
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", contactusService.findAdmChatbotList(filter, search, sort, rows, page, hasDesc));
		return "adminListChatbots";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/chatbots/edit", method = RequestMethod.GET)
	public String showChatbotDetail(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmChatbotDetail chatbotDetail = contactusService.readAdmChatbotDetail(id, localUser.getUserId());
		if(chatbotDetail == null) {
			return "redirect:/admin/realtime/chatbots?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("chatbotDetail", chatbotDetail);
		return "adminRealtimeChatbot";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/chatbots/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteChatbot(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Chatbot chatbot = contactusService.deleteChatbot(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CHATBOT, chatbot.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/chatbots/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteChatbots(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		contactusService.deleteChatbots(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CHATBOTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * @return
	 */
	@RequestMapping(value = "/chatbots/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllChatbots() {
		throw new AccessAuthorityException();
	}
	
}
