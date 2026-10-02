package com.rinitec.algerieoffice.web.controllers.admin;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.rinitec.algerieoffice.services.admins.dashboard.IAdminDashboardService;
import com.rinitec.algerieoffice.services.admins.dashboard.IJournalAdminService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/dashboard")
public class AdminDashboardController {

	private IAttributeService attributeService;
	private IJournalAdminService journalAdminService;
	private IAdminDashboardService adminDashboardService;
	
	@Autowired
	public AdminDashboardController(IAttributeService attributeService, IJournalAdminService journalAdminService, IAdminDashboardService adminDashboardService) {
		this.attributeService = attributeService;
		this.journalAdminService = journalAdminService;
		this.adminDashboardService = adminDashboardService;
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
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
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("logSucc", request.getParameter("logSucc"));
		model.addAttribute("dashboardData", adminDashboardService.findDashboardData());
		model.addAttribute("dashboardPremium", adminDashboardService.findDashboardPremium());
		model.addAttribute("dashboardMedia", adminDashboardService.findDashboardMedia());
		model.addAttribute("dashboardAlert", adminDashboardService.findDashboardAlert());
		model.addAttribute("dashboardJournal", adminDashboardService.findDashboardJournal());
		model.addAttribute("dashboardDatakey", adminDashboardService.findDashboardDatakey());
		model.addAttribute("dashboardInbox", adminDashboardService.findAdmDashboardInbox());
		return "adminDashboard";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String post() {
		return "redirect:/admin/dashboard";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/analytic", method = RequestMethod.GET)
	public String showAnalytic(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("toDay", new DateTime(Date.from(Instant.now())));
		model.addAttribute("analyticLogin", adminDashboardService.findAdmAnalyticLogin());
		model.addAttribute("analyticUser", adminDashboardService.findAdmAnalyticUser());
		model.addAttribute("analyticCompany", adminDashboardService.findAdmAnalyticCompany());
		return "adminAnalytic";
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
	@RequestMapping(value = "/journal", method = RequestMethod.GET)
	public String showJournal(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminJournal";
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
	@RequestMapping(value = "/journal-load", method = RequestMethod.GET)
	public String loadJournal(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", journalAdminService.findJournalList(filter, search, sort, rows, page, hasDesc));
		return "adminListJournal";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/journal/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteJournal(@RequestParam(name = "id") final String id) {
		journalAdminService.deleteJournal(id);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/journal/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteJournals(@RequestParam("lines[]") final List<String> lines) {
		journalAdminService.deleteJournals(lines);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/journal/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllJournal() {
		journalAdminService.deleteAllJournals();
		return new GenericResponse("success");
	}
	
}
