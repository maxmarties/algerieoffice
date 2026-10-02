package com.rinitec.algerieoffice.web.controllers.user;

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

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertPost;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.user.alerts.IAlertPostService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.user.alerts.AlertPostForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/user/alerts")
public class UserAlertController {

	private IAttributeService attributeService;
	private IAlertPostService alertPostService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UserAlertController(IAttributeService attributeService, IAlertPostService alertPostService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.alertPostService = alertPostService;
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
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String showAlertPosts(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ALERT01, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ALERT01));
		return "userAlertPosts";
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
	@RequestMapping(value = "/posts-load", method = RequestMethod.GET)
	public String loadAlertPosts(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ALERT01, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", alertPostService.findAlertPostList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc, DocumentType.post));
		return "userListAlertPosts";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/posts/new", method = RequestMethod.GET)
	public String showNewAlertPost(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("alert", new AlertPostForm(localUser.getUserId(), DocumentType.post));
		return "userAlertPost";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/posts/edit", method = RequestMethod.GET)
	public String showEditAlertPost(final Model model, @RequestParam(name = "id", required = false) final String id, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/user/alerts/posts";
		}
		final AlertPostForm alertPostForm = alertPostService.readAlertPostForm(id, localUser.getUserId(), DocumentType.post);
		if(alertPostForm == null) {
			return "redirect:/user/alerts/posts?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("alert", alertPostForm);
		return "userAlertPost";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param alertPostForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/posts/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAlertPost(@Valid final AlertPostForm alertPostForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!alertPostForm.getUserId().equals(localUser.getUserId()) || !alertPostForm.getType().equals(DocumentType.post)) {
			throw new AccessAuthorityException();
		}
		AlertPost alertPost = null;
		if(StringUtils.isEmpty(alertPostForm.getId())) {
			alertPost = alertPostService.addAlertPost(alertPostForm);
		} else {
			alertPost = alertPostService.updateAlertPost(alertPostForm);
			if(alertPost == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), StringUtils.isEmpty(alertPostForm.getId()) 
				? ConstraintesJournal.USER_ADD_ALERTPOST : ConstraintesJournal.USER_UPDATE_ALERTPOST, alertPost.getName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/posts/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAlertPost(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAlertPost(id, localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALERT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/posts/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAlertPosts(@RequestParam("lines[]") final List<String> lines,
			@AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAlertPosts(lines, localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALERTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/posts/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAlertPosts(@AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAllAlertPosts(localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLALERT1, null));
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
	@RequestMapping(value = "/ads", method = RequestMethod.GET)
	public String showAlertAnnonces(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ALERT02, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ALERT02));
		return "userAlertAnnonces";
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
	@RequestMapping(value = "/ads-load", method = RequestMethod.GET)
	public String loadAlertAnnonces(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ALERT02, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", alertPostService.findAlertPostList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc, DocumentType.annonce));
		return "userListAlertAnnonces";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/ads/new", method = RequestMethod.GET)
	public String showNewAlertAnnonce(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("alert", new AlertPostForm(localUser.getUserId(), DocumentType.annonce));
		return "userAlertAnnonce";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/ads/edit", method = RequestMethod.GET)
	public String showEditAlertAnnonce(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/user/alerts/ads";
		}
		final AlertPostForm alertPostForm = alertPostService.readAlertPostForm(id, localUser.getUserId(), DocumentType.annonce);
		if(alertPostForm == null) {
			return "redirect:/user/alerts/ads?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("alert", alertPostForm);
		return "userAlertAnnonce";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param alertPostForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAlertAnnonce(@Valid final AlertPostForm alertPostForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!alertPostForm.getUserId().equals(localUser.getUserId()) || !alertPostForm.getType().equals(DocumentType.annonce)) {
			throw new AccessAuthorityException();
		}
		AlertPost alertPost = null;
		if(StringUtils.isEmpty(alertPostForm.getId())) {
			alertPost = alertPostService.addAlertPost(alertPostForm);
		} else {
			alertPost = alertPostService.updateAlertPost(alertPostForm);
			if(alertPost == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), StringUtils.isEmpty(alertPostForm.getId()) 
				? ConstraintesJournal.USER_ADD_ALERTADS : ConstraintesJournal.USER_UPDATE_ALERTADS, alertPost.getName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAlertAnnonce(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAlertPost(id, localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALERT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAlertAnnonces(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAlertPosts(lines, localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALERTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAlertAnnonces(@AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAllAlertPosts(localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLALERT2, null));
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
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String showAlertEvents(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ALERT03, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ALERT03));
		return "userAlertEvents";
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
	@RequestMapping(value = "/events-load", method = RequestMethod.GET)
	public String loadAlertEvents(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ALERT03, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", alertPostService.findAlertPostList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc, DocumentType.event));
		return "userListAlertEvents";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/events/new", method = RequestMethod.GET)
	public String showNewAlertEvents(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("alert", new AlertPostForm(localUser.getUserId(), DocumentType.event));
		return "userAlertEvent";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/events/edit", method = RequestMethod.GET)
	public String showEditAlertEvent(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/user/alerts/events";
		}
		final AlertPostForm alertPostForm = alertPostService.readAlertPostForm(id, localUser.getUserId(), DocumentType.event);
		if(alertPostForm == null) {
			return "redirect:/user/alerts/events?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("alert", alertPostForm);
		return "userAlertEvent";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param alertPostForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAlertEvent(@Valid final AlertPostForm alertPostForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!alertPostForm.getUserId().equals(localUser.getUserId()) || !alertPostForm.getType().equals(DocumentType.event)) {
			throw new AccessAuthorityException();
		}
		AlertPost alertPost = null;
		if(StringUtils.isEmpty(alertPostForm.getId())) {
			alertPost = alertPostService.addAlertPost(alertPostForm);
		} else {
			alertPost = alertPostService.updateAlertPost(alertPostForm);
			if(alertPost == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), StringUtils.isEmpty(alertPostForm.getId()) 
				? ConstraintesJournal.USER_ADD_ALERTEVENT : ConstraintesJournal.USER_UPDATE_ALERTEVENT, alertPost.getName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAlertEvent(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAlertPost(id, localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALERT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAlertEvents(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAlertPosts(lines, localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALERTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAlertEvents(@AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAllAlertPosts(localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLALERT3, null));
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
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public String showAlertEmployes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ALERT04, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ALERT04));
		return "userAlertEmployes";
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
	@RequestMapping(value = "/jobs-load", method = RequestMethod.GET)
	public String loadAlertEmployes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ALERT04, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", alertPostService.findAlertPostList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc, DocumentType.employe));
		return "userListAlertEmployes";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/jobs/new", method = RequestMethod.GET)
	public String showNewAlertEmployes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("alert", new AlertPostForm(localUser.getUserId(), DocumentType.employe));
		return "userAlertEmploye";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/jobs/edit", method = RequestMethod.GET)
	public String showEditAlertEmploye(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/user/alerts/jobs";
		}
		final AlertPostForm alertPostForm = alertPostService.readAlertPostForm(id, localUser.getUserId(), DocumentType.employe);
		if(alertPostForm == null) {
			return "redirect:/user/alerts/jobs?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("alert", alertPostForm);
		return "userAlertEmploye";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param alertPostForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAlertEmploye(@Valid final AlertPostForm alertPostForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!alertPostForm.getUserId().equals(localUser.getUserId()) || !alertPostForm.getType().equals(DocumentType.employe)) {
			throw new AccessAuthorityException();
		}
		AlertPost alertPost = null;
		if(StringUtils.isEmpty(alertPostForm.getId())) {
			alertPost = alertPostService.addAlertPost(alertPostForm);
		} else {
			alertPost = alertPostService.updateAlertPost(alertPostForm);
			if(alertPost == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), StringUtils.isEmpty(alertPostForm.getId()) 
				? ConstraintesJournal.USER_ADD_ALERTEMPLOYE : ConstraintesJournal.USER_UPDATE_ALERTEMPLOYE, alertPost.getName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAlertEmploye(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAlertPost(id, localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALERT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAlertEmployes(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAlertPosts(lines, localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALERTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAlertEmployes(@AuthenticationPrincipal final LocalUser localUser) {
		alertPostService.deleteAllAlertPosts(localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLALERT4, null));
		return new GenericResponse("success");
	}
	
}
