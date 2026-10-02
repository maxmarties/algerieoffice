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

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestPartner;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.company.IGuestPartnerService;
import com.rinitec.algerieoffice.services.user.feedback.ICommunicationService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.company.communication.AppointForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationsEvent;
import com.rinitec.algerieoffice.web.modal.company.communication.AppointDetail;
import com.rinitec.algerieoffice.web.modal.company.communication.ChatbotDetail;
import com.rinitec.algerieoffice.web.modal.company.communication.ContactDetail;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/company/communication")
public class CompanyCommunicationController {

	private IAttributeService attributeService;
	private IPremiumService premiumService;
	private ICommunicationService communicationService;
	private IGuestPartnerService guestPartnerService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyCommunicationController(IAttributeService attributeService, IPremiumService premiumService, ICommunicationService communicationService, 
			IGuestPartnerService guestPartnerService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.premiumService = premiumService;
		this.communicationService = communicationService;
		this.guestPartnerService = guestPartnerService;
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
	@RequestMapping(value = "/notices", method = RequestMethod.GET)
	public String showNotices(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION1, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION1));
		return "companyCommunicationNotices";
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
	@RequestMapping(value = "/notices-load", method = RequestMethod.GET)
	public String loadNotices(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION1, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findNoticeList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListNotices";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notices/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateNotice(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final Notice notice = communicationService.validateNotice(id, localUser.getCompanyId(), localUser.getUserId());
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), notice.getUserId(), null, NotificationType.noticeAccepted, request));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_VALIDATE_NOTICE, notice.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notices/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteNotice(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final Notice notice = communicationService.deleteNotice(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), notice.getUserId(), null, NotificationType.noticeRejected, request));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_NOTICE, notice.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notices/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteNotices(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteNotices(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_NOTICE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notices/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllNotices(@AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAllNotices(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_NOTICE, null));
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
	@RequestMapping(value = "/appointments", method = RequestMethod.GET)
	public String showAppointments(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION2, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION2));
		return "companyCommunicationAppoints";
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
	@RequestMapping(value = "/appointments-load", method = RequestMethod.GET)
	public String loadAppointments(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION2, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findAppointList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListAppoints";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/appointments/edit", method = RequestMethod.GET)
	public String showEditAppointment(final Model model, @RequestParam(name = "id", required = false) final String id, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "/company/communication/appointments";
		}
		if(premiumService.hasPremium(localUser.getCompanyId())) {
			final AppointDetail appointDetail = communicationService.readAppointDetail(id, localUser.getCompanyId());
			if(appointDetail == null) {
				return "redirect:/company/communication/appointments?notFound=true";
			}
			model.addAttribute("appoint", new AppointForm(id));
			model.addAttribute("appointDetail", appointDetail);
		}
		attributeService.attributeCompany(model, config, localUser);
		return "companyCommunicationAppoint";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param appointForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appointments/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAppointment(final HttpServletRequest request, @Valid final AppointForm appointForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		final Appointment appointment = communicationService.updateAppointment(appointForm, localUser.getCompanyId(), localUser.getUserId());
		if(appointment == null) {
			throw new NotFoundException("message.error.notfound");
		}
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), appointment.getUserId(), null, 
				NotificationType.appointment, request));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_VALIDATE_APPOINTMENT, appointForm.getAppointDate()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appointments/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAppointment(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAppointment(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_APPOINTMENT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appointments/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAppointments(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAppointments(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_APPOINTMENT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appointments/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAppointments(@AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAllAppointments(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_APPOINTMENT, null));
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
	@RequestMapping(value = "/evaluations", method = RequestMethod.GET)
	public String showEvaluations(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION3, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION3));
		return "companyCommunicationEvaluations";
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
	@RequestMapping(value = "/evaluations-load", method = RequestMethod.GET)
	public String loadEvaluations(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION3, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findEvaluationList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListEvaluations";
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
	@RequestMapping(value = "/collaborators", method = RequestMethod.GET)
	public String showCollaborators(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION4, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION4));
		return "companyCommunicationCollaborators";
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
	@RequestMapping(value = "/collaborators-load", method = RequestMethod.GET)
	public String loadCollaborators(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION4, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findCollaboratorList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListCollaborators";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/collaborators/new", method = RequestMethod.GET)
	public String showNewCollaborator(final HttpServletRequest request, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/communication/collaborators";
		}
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			return "redirect:/company/communication/collaborators?notFound=true";
		}
		final Collaborator collaborator = communicationService.validateCollaborator(id, localUser.getCompanyId(), localUser.getUserId());
		if(collaborator == null) {
			return "redirect:/company/communication/collaborators?notFound=true";
		}
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), collaborator.getUserId(), null, NotificationType.collaborator, request));
		return "redirect:/company/team/agents/new-collaborator?id=".concat(collaborator.getUserId().toString());
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/collaborators/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validateCollaborators(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final Collaborator collaborator = communicationService.validateCollaborator(id, localUser.getCompanyId(), localUser.getUserId());
		if(collaborator == null) {
			throw new NotFoundException("message.error.notfound");
		}
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), collaborator.getUserId(), null, NotificationType.collaborator, request));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_VALIDATE_COLLABORATOR, collaborator.getFunction()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/collaborators/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteCollaborator(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Collaborator collaborator = communicationService.deleteCollaborator(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_COLLABORATOR, collaborator.getFunction()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/collaborators/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteCollaborators(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteCollaborators(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_COLLABORATOR, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/collaborators/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllCollaborators(@AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAllCollaborators(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_COLLABORATOR, null));
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
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public String showContacts(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION5, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION5));
		return "companyCommunicationContacts";
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
	@RequestMapping(value = "/contacts-load", method = RequestMethod.GET)
	public String loadContacts(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION5, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findContactList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListContacts";
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
	@RequestMapping(value = "/contacts/detail", method = RequestMethod.GET)
	public String showContactDetail(final Model model, @RequestParam(name = "id", required = false) final String id, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/communication/contacts";
		}
		if(premiumService.hasPremium(localUser.getCompanyId())) {
			final ContactDetail contactDetail = communicationService.readContactDetail(id, localUser.getCompanyId(), localUser.getUserId());
			if(contactDetail == null) {
				return "redirect:/company/communication/contacts?notFound=true";
			}
			model.addAttribute("contactDetail", contactDetail);
			eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
					ConstraintesJournal.COMPANY_VALIDATE_CONTACT, contactDetail.getUsername()));
		}
		attributeService.attributeCompany(model, config, localUser);
		return "companyCommunicationContact";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/contacts/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteContact(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Contact contact = communicationService.deleteContact(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_CONTACT, contact.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/contacts/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteContacts(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteContacts(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_CONTACT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/contacts/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllContacts(@AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAllContacts(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_CONTACT, null));
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
	@RequestMapping(value = "/partners", method = RequestMethod.GET)
	public String showPartners(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION6, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION6));
		return "companyCommunicationPartners";
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
	@RequestMapping(value = "/partners-load", method = RequestMethod.GET)
	public String loadPartners(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION6, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", guestPartnerService.findPartnerGuestList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListGuestPartners";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/partners/validate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse validatePartner(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		final GuestPartner guestPartner = guestPartnerService.validateGuestPartner(id, companyId, localUser.getUserId());
		eventPublisher.publishEvent(new OnNotificationEvent(companyId, guestPartner.getGuestBy(), null, NotificationType.partnerAccepted, request));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_VALIDATE_PARTNERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/partners/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deletePartner(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		final Long userId = guestPartnerService.deleteGuestPartner(id, companyId);
		if(userId != null) {
			eventPublisher.publishEvent(new OnNotificationEvent(companyId, userId, null, NotificationType.partnerRejected, request));
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_PARTNERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/partners/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deletePartners(final HttpServletRequest request, @RequestParam("lines[]") final List<String> lines,
			@AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		final List<Long> usersId = guestPartnerService.deleteGuestPartners(lines, companyId);
		if(usersId != null && !usersId.isEmpty()) {
			eventPublisher.publishEvent(new OnNotificationsEvent(companyId, usersId, null, NotificationType.partnerRejected, request));
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_PARTNERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/partners/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllPartners(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		final List<Long> usersId = guestPartnerService.deleteAllGuestPartners(companyId);
		if(usersId != null && !usersId.isEmpty()) {
			eventPublisher.publishEvent(new OnNotificationsEvent(companyId, usersId, null, NotificationType.partnerRejected, request));
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_PARTNERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION7, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_COMMUNICATION7));
		return "companyCommunicationChatbots";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
	@RequestMapping(value = "/chatbots-load", method = RequestMethod.GET)
	public String loadChatbots(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_COMMUNICATION7, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", communicationService.findChatbotList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListChatbots";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/chatbots/detail", method = RequestMethod.GET)
	public String showChatbotDetail(final Model model, @RequestParam(name = "id", required = false) final String id, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/communication/chatbots";
		}
		if(premiumService.hasPremium(localUser.getCompanyId())) {
			final ChatbotDetail chatbotDetail = communicationService.readChatbotDetail(id, localUser.getCompanyId(), localUser.getUserId());
			if(chatbotDetail == null) {
				return "redirect:/company/communication/chatbots?notFound=true";
			}
			model.addAttribute("chatbotDetail", chatbotDetail);
			eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
					ConstraintesJournal.COMPANY_CONSULT_CHATBOT, chatbotDetail.getMessage()));
		}
		attributeService.attributeCompany(model, config, localUser);
		return "companyCommunicationChatbot";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chatbots/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteChatbot(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Chatbot chatbot = communicationService.deleteChatbot(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_CHATBOT, chatbot.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chatbots/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteChatbots(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteChatbots(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_CHATBOT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chatbots/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllChatbots(@AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAllChatbots(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_CHATBOT, null));
		return new GenericResponse("success");
	}
	
}
