package com.rinitec.algerieoffice.web.controllers.company;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
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

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.company.dashboard.ICompanyDashboardService;
import com.rinitec.algerieoffice.services.company.dashboard.IDetectService;
import com.rinitec.algerieoffice.services.company.dashboard.IJournalCompanyService;
import com.rinitec.algerieoffice.services.company.help.ITaskHelpService;
import com.rinitec.algerieoffice.services.company.team.ITeamService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.modal.CurrentCompany;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/company/dashboard")
public class CompanyDashboardController {
	
	private IPremiumService premiumService;
	private IAttributeService attributeService;
	private ICompanyDashboardService companyDashboardService;
	private IJournalCompanyService journalCompanyService;
	private ITeamService teamService;
	private IDetectService detectService;
	private ITaskHelpService taskHelpService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyDashboardController(IPremiumService premiumService, IAttributeService attributeService, ICompanyDashboardService companyDashboardService, 
			IJournalCompanyService journalCompanyService, ITeamService teamService, IDetectService detectService, ITaskHelpService taskHelpService, 
			ApplicationEventPublisher eventPublisher) {
		this.premiumService = premiumService;
		this.attributeService = attributeService;
		this.companyDashboardService = companyDashboardService;
		this.journalCompanyService = journalCompanyService;
		this.teamService = teamService;
		this.detectService = detectService;
		this.taskHelpService = taskHelpService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showDashboard(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final CurrentCompany currentCompany = attributeService.attributeCompany(model, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("logSucc", request.getParameter("logSucc"));
		model.addAttribute("toDay", new DateTime(Date.from(Instant.now())));
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("countFavorite", companyDashboardService.countFormattedFavoriteCompany(companyId));
		model.addAttribute("dataLines", companyDashboardService.findDashboardDataList(companyId, currentCompany.isPublished()));
		model.addAttribute("evaluationLines", companyDashboardService.findLastDashboardEvaluation(companyId, 5));
		if(currentConfig.isWelcome() && !currentCompany.hasIgnoreWelcome()) {
			model.addAttribute("begginer", taskHelpService.readDashboardTask(companyId));
		}
		if(currentCompany.hasPremium()) {
			model.addAttribute("journalLines", companyDashboardService.findLastDashboardJournal(companyId, 6));
			model.addAttribute("detectLines", companyDashboardService.findLastDashboardDetect(companyId, 6));
		}
		if(!currentConfig.isIdentityCollapse() && StringUtils.isEmpty(currentCompany.getUrl())) {
			model.addAttribute("countSkills", taskHelpService.countAllActiveCompanies());
		}
		return "companyDashboard";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String post() {
		return "redirect:/company/dashboard";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/analytic", method = RequestMethod.GET)
	public String showAnalytic(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		if(premiumService.hasPremium(localUser.getCompanyId())) {
			model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
			model.addAttribute("refering", companyDashboardService.readAnalyticSearch(localUser.getCompanyId()));
			model.addAttribute("accessing", companyDashboardService.readAnalyticAccess(localUser.getCompanyId()));
		}
		return "companyAnalytic";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/analytic-access", method = RequestMethod.GET)
	public String loadAnalyticAccess(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		if(!premiumService.hasPremiumRegular(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		model.addAttribute("listAccess", companyDashboardService.findAnalyticAutentifiedList(localUser.getCompanyId(), 20));
		return "companyListAnalyticAccess";
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
	@RequestMapping(value = "/journal", method = RequestMethod.GET)
	public String showJournal(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_DASHBOARD1, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_DASHBOARD1));
		return "companyJournal";
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
	@RequestMapping(value = "/journal-load", method = RequestMethod.GET)
	public String loadJouranl(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_DASHBOARD1, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", journalCompanyService.findJournalList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListJournal";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/journal-access", method = RequestMethod.GET)
	public String loadJournalAccess(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		model.addAttribute("listAccess", journalCompanyService.findJournalAccessList(localUser.getCompanyId()));
		return "companyListJournalAccess";
	}

	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/journal/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteJournal(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		if(!teamService.hasSuperAdmin(localUser.getUserId(), localUser.getCompanyId())) {
			throw new AccessUploadException("message.error.journal");
		}
		journalCompanyService.deleteJournal(id, localUser.getCompanyId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/journal/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteJournals(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		if(!teamService.hasSuperAdmin(localUser.getUserId(), localUser.getCompanyId())) {
			throw new AccessUploadException("message.error.journal");
		}
		journalCompanyService.deleteJournals(lines, localUser.getCompanyId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/journal/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllJournals(@AuthenticationPrincipal final LocalUser localUser) {
		if(!teamService.hasSuperAdmin(localUser.getUserId(), localUser.getCompanyId())) {
			throw new AccessUploadException("message.error.journal");
		}
		journalCompanyService.deleteAllJournals(localUser.getCompanyId());
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
	@RequestMapping(value = "/detect", method = RequestMethod.GET)
	public String showDetect(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_DASHBOARD2, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_DASHBOARD2));
		return "companyDetect";
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
	@RequestMapping(value = "/detect-load", method = RequestMethod.GET)
	public String loadDetect(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_DASHBOARD2, defaultValue = "") final String table) {
		if(!premiumService.hasPremiumRegular(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", detectService.findDetectList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListDetect";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/detect-access", method = RequestMethod.GET)
	public String loadDetetctAccess(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		if(!premiumService.hasPremiumRegular(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		model.addAttribute("listAccess", detectService.findDetectAccessList(localUser.getCompanyId(), 20));
		return "companyListDetectAccess";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/detect/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteDetect(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		detectService.deleteDetect(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(),
				ConstraintesJournal.COMPANY_DELETE_DETECT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/detect/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteDetects(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		detectService.deleteDetects(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(),
				ConstraintesJournal.COMPANY_DELETE_LINES_DETECT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/detect/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllDetects(@AuthenticationPrincipal final LocalUser localUser) {
		detectService.deleteAllDetects(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(),
				ConstraintesJournal.COMPANY_DELETE_ALL_DETECT, null));
		return new GenericResponse("success");
	}
	
}
