package com.rinitec.algerieoffice.web.controllers.user;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.user.dashboard.IDashboardUserService;
import com.rinitec.algerieoffice.services.user.dashboard.IJournalUserService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/user/dashboard")
public class UserDashboardController {

	private IAttributeService attributeService;
	private IJournalUserService journalUserService;
	private IDashboardUserService dashboardUserService;
	
	@Autowired
	public UserDashboardController(IAttributeService attributeService, IJournalUserService journalUserService, IDashboardUserService dashboardUserService) {
		this.attributeService = attributeService;
		this.journalUserService = journalUserService;
		this.dashboardUserService = dashboardUserService;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param request
	 * @param model
	 * @param localUser
	 * @param style
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showDashboard(final HttpServletResponse response, final HttpServletRequest request, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "style", required = false) final Integer style,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long userId = localUser.getUserId();
		final CurrentConfig currentConfig = new CurrentConfig(config);
		if(style != null && (style == 2 || style == 3)) {
			currentConfig.setDefaultStyle(style);
			RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		}
		attributeService.attributeUser(model, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("logSucc", request.getParameter("logSucc"));
		model.addAttribute("toDay", new DateTime(Date.from(Instant.now())));
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("dataLines", dashboardUserService.readDashboardUser(userId));
		model.addAttribute("historyLines", journalUserService.findLastDashboardHistory(userId, 7));
		model.addAttribute("messageLines", dashboardUserService.findLastDashboardMessage(userId, 6));
		model.addAttribute("commentLines", dashboardUserService.findLastDashboardComment(userId, 6));
		return "userDashboard";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String post() {
		return "redirect:/user/dashboard";
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
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String showHistory(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_DASHBOARD01, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_DASHBOARD01));
		return "userHistory";
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
	@RequestMapping(value = "/history-load", method = RequestMethod.GET)
	public String loadHistory(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_DASHBOARD01, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", journalUserService.findJournalList(localUser.getUserId(), filter, sort, rows, page, hasDesc));
		return "userListHistory";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/history/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteHistory(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		journalUserService.deleteJournal(id, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/history/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteHistories(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		journalUserService.deleteJournals(lines, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/history/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllHistory(@AuthenticationPrincipal final LocalUser localUser) {
		journalUserService.deleteAllJournals(localUser.getUserId());
		return new GenericResponse("success");
	}
	
}
