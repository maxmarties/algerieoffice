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

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.team.IAdmUserService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.admins.team.AdmManagerForm;
import com.rinitec.algerieoffice.web.form.admins.team.AdmUserForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.listener.events.OnLogoutEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/team")
public class AdminTeamController {

	private IAttributeService attributeService;
	private IAdmUserService userService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminTeamController(IAttributeService attributeService, IAdmUserService userService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.userService = userService;
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
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String showUsers(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminTeamUsers";
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
	 * @return
	 */
	@RequestMapping(value = "/users-load", method = RequestMethod.GET)
	public String loadUsers(final Model model, @RequestParam("fl") final String fl, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final Boolean filter = !StringUtils.isEmpty(fl) ? fl.equals("true") : null;
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", userService.findAdmUsersList(filter, search, sort, rows, page, hasDesc));
		return "adminListUsers";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/users/new", method = RequestMethod.GET)
	public String showNewUser(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("admuser", new AdmUserForm());
		return "adminTeamUser";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param admUserForm
	 * @return
	 */
	@RequestMapping(value = "/users/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveUser(@AuthenticationPrincipal final LocalUser localUser, @Valid final AdmUserForm admUserForm) {
		final User user = userService.addUser(admUserForm);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_ADD_USER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/lock", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse lockUser(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id) {
		final User user = userService.lockUser(id);
		if(user.isLocked()) {
			eventPublisher.publishEvent(new OnLogoutEvent(user.getEmail()));
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), user.isLocked() ? ConstraintesJournal.ADMIN_LOCK_USER 
				: ConstraintesJournal.ADMIN_UNLOCK_USER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteUser(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id) {
		final User user = userService.deleteUser(id);
		eventPublisher.publishEvent(new OnLogoutEvent(user.getEmail()));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_USER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/users/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteUsers(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<Long> lines) {
		userService.deleteUsers(lines);
		eventPublisher.publishEvent(new OnLogoutEvent(lines));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_USERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/users/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllUsers() {
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
	@RequestMapping(value = "/moderators", method = RequestMethod.GET)
	public String showModerators(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminTeamModerators";
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
	@RequestMapping(value = "/moderators-load", method = RequestMethod.GET)
	public String loadModerators(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", userService.findAdmModeratorsList(filter, search, sort, rows, page, hasDesc));
		return "adminListModerators";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/moderators/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteModerator(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id) {
		final User user = userService.deleteUser(id);
		eventPublisher.publishEvent(new OnLogoutEvent(user.getEmail()));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_USER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/moderators/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteModerators(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<Long> lines) {
		userService.deleteUsers(lines);
		eventPublisher.publishEvent(new OnLogoutEvent(lines));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_USERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/moderators/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllModerators() {
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
	@RequestMapping(value = "/managers", method = RequestMethod.GET)
	public String showManagers(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminTeamManagers";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/managers-load", method = RequestMethod.GET)
	public String loadManagers(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam("fl") final Integer filter, 
			@RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, 
			@RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currUserId", localUser.getUserId());
		model.addAttribute("list", userService.findAdmManagersList(filter, search, sort, rows, page, hasDesc));
		return "adminListManagers";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/managers/new", method = RequestMethod.GET)
	public String showNewManager(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("manager", new AdmManagerForm());
		return "adminTeamManager";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/managers/edit", method = RequestMethod.GET)
	public String showEditManager(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmManagerForm admManagerForm = userService.readAdmManagerForm(id);
		if(admManagerForm == null) {
			return "redirect:/admin/team/managers?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("manager", admManagerForm);
		return "adminTeamManager";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param admManagerForm
	 * @return
	 */
	@RequestMapping(value = "/managers/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveManager(@AuthenticationPrincipal final LocalUser localUser, @Valid final AdmManagerForm admManagerForm) {
		User manager = null;
		if(admManagerForm.getId() == null) {
			manager = userService.addManager(admManagerForm);
		} else {
			manager = userService.updateManager(admManagerForm);
			if(manager == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), admManagerForm.getId() == null ? ConstraintesJournal.ADMIN_ADD_MANAGER 
				: ConstraintesJournal.ADMIN_UPDATE_MANAGER, manager.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/managers/lock", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse lockManager(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id) {
		final User user = userService.lockManager(id);
		if(user.isLocked()) {
			eventPublisher.publishEvent(new OnLogoutEvent(user.getEmail()));
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), user.isLocked() ? ConstraintesJournal.ADMIN_LOCK_MANAGER 
				: ConstraintesJournal.ADMIN_UNLOCK_MANAGER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/managers/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteManager(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id) {
		final User user = userService.removeManager(id);
		if(user == null) {
			throw new AccessAuthorityException();
		}
		eventPublisher.publishEvent(new OnLogoutEvent(user.getEmail()));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_MANAGER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/managers/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteManagers(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<Long> lines) {
		userService.removeManagers(lines);
		eventPublisher.publishEvent(new OnLogoutEvent(lines));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_MANAGERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/managers/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllManagers() {
		throw new AccessAuthorityException();
	}
	
}
