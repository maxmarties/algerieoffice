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
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.company.team.Agent;
import com.rinitec.algerieoffice.persistence.modal.company.team.Guest;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.company.team.IAgentService;
import com.rinitec.algerieoffice.services.company.team.IGuestService;
import com.rinitec.algerieoffice.services.company.team.ITeamService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.MaxPlanException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.company.team.AgentForm;
import com.rinitec.algerieoffice.web.form.company.team.TeamguestForm;
import com.rinitec.algerieoffice.web.form.company.team.TeamuserForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnLogoutEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationsEvent;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/company/team")
public class CompanyTeamController {

	private IAttributeService attributeService;
	private IPremiumService premiumService;
	private ITeamService teamService;
	private IAgentService agentService;
	private IGuestService guestService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyTeamController(IAttributeService attributeService, IPremiumService premiumService, ITeamService teamService, 
			IAgentService agentService, IGuestService guestService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.premiumService = premiumService;
		this.teamService = teamService;
		this.agentService = agentService;
		this.guestService = guestService;
		this.eventPublisher = eventPublisher;
	}

	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
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
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TEAM1, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("countGuest", teamService.countGuest(localUser.getCompanyId()));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_TEAM1));
		return "companyTeamUsers";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/users-load", method = RequestMethod.GET)
	public String loadUsers(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TEAM1, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("currUserId", localUser.getUserId());
		model.addAttribute("list", teamService.findUsersList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListUsers";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/users/new", method = RequestMethod.GET)
	public String showNewUser(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final boolean hasMaxUser = premiumService.hasMaxUser(companyId, teamService.countUserAndGuest(companyId));
		attributeService.attributeCompany(model, config, localUser);
		if(!hasMaxUser) {
			model.addAttribute("teamguest", new TeamguestForm());
			model.addAttribute("teamuser", new TeamuserForm(companyId));
		}
		model.addAttribute("hasMaxUser", hasMaxUser);
		return "companyTeamNewuser";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/users/edit", method = RequestMethod.GET)
	public String showEditUser(final Model model, @RequestParam(name = "id", required = false) final Long id, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/team/users";
		}
		if(teamService.hasSuperAdmin(id, localUser.getCompanyId())) {
			return "redirect:/company/team/users?info=superAdmin";
		}
		final TeamuserForm teamuserForm = teamService.readTeamuserForm(id, localUser.getCompanyId());
		if(teamuserForm == null) {
			return "redirect:/company/team/users?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("teamuser", teamuserForm);
		return "companyTeamEdituser";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param teamuserForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/users/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveUser(final HttpServletRequest request, @Valid final TeamuserForm teamuserForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		if(!teamuserForm.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		final boolean hasMaxUser = premiumService.hasMaxUser(companyId, teamService.countUserAndGuest(companyId));
		if(hasMaxUser) {
			throw new MaxPlanException("message.plan.user");
		}
		teamuserForm.setFile(file);
		final User user = teamService.addUser(teamuserForm);
		eventPublisher.publishEvent(new OnRegisterEvent(user, request, EmailType.addUser));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_ADD_USER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param teamuserForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateUser(final HttpServletRequest request, @Valid final TeamuserForm teamuserForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		if(!teamuserForm.getCompanyId().equals(localUser.getCompanyId()) 
				|| teamService.hasSuperAdmin(teamuserForm.getId(), localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		teamuserForm.setFile(file);
		final User user = teamService.updateUser(teamuserForm);
		if(user == null) {
			throw new AccessAuthorityException();
		}
		if(!user.isEnabled()) {
			eventPublisher.publishEvent(new OnRegisterEvent(user, request, EmailType.editUser));
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_USER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/users/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteUser(final HttpServletRequest request, @RequestParam("id") final Long id, 
			@AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		if(teamService.hasSuperAdmin(id, companyId)) {
			throw new AccessUploadException("message.error.superuser");
		}
		final User user = teamService.removeUser(id, companyId);
		eventPublisher.publishEvent(new OnNotificationEvent(companyId, id, null, NotificationType.userRemoved, request));
		eventPublisher.publishEvent(new OnLogoutEvent(user.getEmail()));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_REMOVE_USER, user.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/users/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteUsers(final HttpServletRequest request, @RequestParam("lines[]") final List<Long> lines,
			@AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		if(teamService.hasContentSuperAdmin(lines, companyId)) {
			throw new AccessUploadException("message.error.superuser");
		}
		teamService.removeUsers(lines, companyId);
		eventPublisher.publishEvent(new OnNotificationsEvent(companyId, lines, null, NotificationType.userRemoved, request));
		eventPublisher.publishEvent(new OnLogoutEvent(lines));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_REMOVE_LINES_USER, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/users/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllUsers(@AuthenticationPrincipal final LocalUser localUser) {
		throw new AccessUploadException("message.error.superuser");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param teamguestForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/users/guest", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse guestUser(final HttpServletRequest request, @Valid final TeamguestForm teamguestForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		final boolean hasMaxUser = premiumService.hasMaxUser(companyId, teamService.countUserAndGuest(companyId));
		if(hasMaxUser) {
			throw new MaxPlanException("message.plan.user");
		}
		final Guest guest = guestService.addGuest(teamguestForm, companyId, localUser.getUserId());
		eventPublisher.publishEvent(new OnNotificationEvent(companyId, guest.getUserId(), null, NotificationType.guest, request));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_GUEST_USER, teamguestForm.getGuestmail()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/agents", method = RequestMethod.GET)
	public String showAgents(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TEAM2, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_TEAM2));
		return "companyTeamAgents";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/agents-load", method = RequestMethod.GET)
	public String loadPosts(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TEAM2, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", agentService.findAgentsList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListAgents";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/agents/new", method = RequestMethod.GET)
	public String showNewAgent(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final boolean hasMaxAgent = premiumService.hasMaxAgent(companyId, agentService.countAgent(companyId));
		attributeService.attributeCompany(model, config, localUser);
		if(!hasMaxAgent) {
			model.addAttribute("choseUsers", agentService.findAllChoseUser(companyId));
		}
		model.addAttribute("hasMaxAgent", hasMaxAgent);
		model.addAttribute("agent", new AgentForm(companyId));
		return "companyTeamAgent";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/agents/new-collaborator", method = RequestMethod.GET)
	public String showNewCollaboratorAgent(final Model model, @RequestParam(name = "id", required = false) final Long id, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/team/agents";
		}
		final Long companyId = localUser.getCompanyId();
		final boolean hasMaxAgent = premiumService.hasMaxAgent(companyId, agentService.countAgent(companyId));
		attributeService.attributeCompany(model, config, localUser);
		AgentForm agentForm = null;
		if(!hasMaxAgent) {
			model.addAttribute("choseUsers", agentService.findAllChoseUser(companyId));
			agentForm = agentService.readAgentFormCollaborator(id, companyId);
		}
		model.addAttribute("hasMaxAgent", hasMaxAgent);
		model.addAttribute("agent", agentForm != null ? agentForm : new AgentForm(companyId));
		return "companyTeamAgent";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/agents/edit", method = RequestMethod.GET)
	public String showEditAgent(final Model model, @RequestParam(name = "id", required = false) final Long id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/team/agents";
		}
		final AgentForm agentForm = agentService.readAgentForm(id, localUser.getCompanyId());
		if(agentForm == null) {
			return "redirect:/company/team/agents?notFound=true";
		}
		final Long companyId = localUser.getCompanyId();
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("agent", agentForm);
		model.addAttribute("choseUsers", agentService.findAllChoseUser(companyId));
		return "companyTeamAgent";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param agentForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/agents/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAgent(@AuthenticationPrincipal final LocalUser localUser, @Valid final AgentForm agentForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		final Long companyId = localUser.getCompanyId();
		if(!agentForm.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		agentForm.setFile(file);
		if(StringUtils.isEmpty(agentForm.getId())) {
			final boolean hasMaxAgent = premiumService.hasMaxAgent(companyId, agentService.countAgent(companyId));
			if(hasMaxAgent) {
				throw new MaxPlanException("message.plan.agent");
			}
			agentService.addAgent(agentForm);
		} else {
			final Agent agent = agentService.updateAgent(agentForm);
			if(agent == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(agentForm.getId()) ? ConstraintesJournal.COMPANY_ADD_AGENT : ConstraintesJournal.COMPANY_UPDATE_AGENT, agentForm.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/agents/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAgent(@RequestParam("id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		final Agent agent = agentService.deleteAgent(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_AGENT, agent.getDisplayName()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/agents/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAgents(@RequestParam("lines[]") final List<Long> lines, @AuthenticationPrincipal final LocalUser localUser) {
		agentService.deleteAgents(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_AGENT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/agents/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAgents(@AuthenticationPrincipal final LocalUser localUser) {
		agentService.deleteAllAgents(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_AGENT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/guests", method = RequestMethod.GET)
	public String showGuests(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TEAM3, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_TEAM3));
		return "companyTeamGuests";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
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
	@RequestMapping(value = "/guests-load", method = RequestMethod.GET)
	public String loadGuests(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TEAM3, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", guestService.findGuestList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListGuests";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/guests/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteGuest(@RequestParam("id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		guestService.deleteCompanyGuest(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_GUEST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/guests/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteGuests(@RequestParam("lines[]") final List<Long> lines, @AuthenticationPrincipal final LocalUser localUser) {
		guestService.deleteCompanyGuests(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_GUEST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/guests/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllGuests(@AuthenticationPrincipal final LocalUser localUser) {
		guestService.deleteAllCompanyGuests(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_GUEST, null));
		return new GenericResponse("success");
	}
	
}
