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

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Newsletter;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.persistence.modal.inbox.Chater;
import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.communication.IAdmCommunicationService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/communication")
public class AdminCommunicationController {

	private IAttributeService attributeService;
	private IAdmCommunicationService communicationService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminCommunicationController(IAttributeService attributeService, IAdmCommunicationService communicationService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.communicationService = communicationService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/chater", method = RequestMethod.GET)
	public String showChater(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCommunicationChater";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/chater-load", method = RequestMethod.GET)
	public String loadChater(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", communicationService.findAdmChaterList(filter, search, sort, rows, page, hasDesc));
		return "adminListChater";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chater/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteChater(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Chater chater = communicationService.deleteChater(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CHATER, chater.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chater/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteChaters(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteChaters(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CHATERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/chater/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllChaters() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String showMessage(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCommunicationMessage";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/message-load", method = RequestMethod.GET)
	public String loadMessage(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", communicationService.findAdmMessageList(filter, search, sort, rows, page, hasDesc));
		return "adminListMessage";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/message/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteMessage(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Message message = communicationService.deleteMessage(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_MESSAGE, message.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/message/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteMessages(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteMessages(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_MESSAGES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/message/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllMessages() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String showContact(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCommunicationContact";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/contact-load", method = RequestMethod.GET)
	public String loadContact(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", communicationService.findAdmContactList(filter, search, sort, rows, page, hasDesc));
		return "adminListContact";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/contact/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteContact(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Contact contact = communicationService.deleteContact(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CONTACT, contact.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/contact/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteContacts(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteContacts(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CONTACTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/contact/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllContacts() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/appoint", method = RequestMethod.GET)
	public String showAppoint(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCommunicationAppoint";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/appoint-load", method = RequestMethod.GET)
	public String loadAppoint(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", communicationService.findAdmAppointList(filter, search, sort, rows, page, hasDesc));
		return "adminListAppoint";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appoint/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAppoint(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Appointment appointment = communicationService.deleteAppoint(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_APPOINT, appointment.getMotif()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appoint/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAppoints(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteAppoints(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_APPOINTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/appoint/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAppoints() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String showNotice(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCommunicationNotice";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/notice-load", method = RequestMethod.GET)
	public String loadNotice(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", communicationService.findAdmNoticeList(filter, search, sort, rows, page, hasDesc));
		return "adminListNotice";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notice/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteNotice(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Notice notice = communicationService.deleteNotice(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_NOTICE, notice.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notice/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteNotices(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteNotices(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_NOTICES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/notice/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllNotices() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/guest", method = RequestMethod.GET)
	public String showGuest(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCommunicationGuest";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/guest-load", method = RequestMethod.GET)
	public String loadGuest(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", communicationService.findAdmGuestList(filter, search, sort, rows, page, hasDesc));
		return "adminListGuest";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/guest/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteGuest(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final GuestDocument guestDocument = communicationService.deleteGuest(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_GUEST, guestDocument.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/guest/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteGuests(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteGuests(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_GUESTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/guest/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllGuests() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/newsletter", method = RequestMethod.GET)
	public String showNewsletter(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCommunicationNewsletter";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/newsletter-load", method = RequestMethod.GET)
	public String loadNewsletter(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", communicationService.findAdmNewsletterList(filter, search, sort, rows, page, hasDesc));
		return "adminListNewsletter";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/newsletter/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteNewsletter(@RequestParam(name = "id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		final Newsletter newsletter = communicationService.deleteNewsletter(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_NEWSLETTER, newsletter.getEmail()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/newsletter/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteNewsletters(@RequestParam("lines[]") final List<Long> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteNewsletters(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_NEWSLETTERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/newsletter/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllNewsletters() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/chatbot", method = RequestMethod.GET)
	public String showChatbot(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCommunicationChatbot";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/chatbot-load", method = RequestMethod.GET)
	public String loadChatbot(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", communicationService.findAdmChatboterList(filter, search, sort, rows, page, hasDesc));
		return "adminListChatboter";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chatbot/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteChatbot(@RequestParam(name = "id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Chatbot chatbot = communicationService.deleteChatboter(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CHATBOT, chatbot.getMessage()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chatbot/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteChatbots(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		communicationService.deleteChatboters(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_CHATBOTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/chatbot/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllChatbots() {
		throw new AccessAuthorityException();
	}
	
}
