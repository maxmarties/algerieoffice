package com.rinitec.algerieoffice.web.controllers.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.services.admins.dashboard.IAdminAnalyticService;
import com.rinitec.algerieoffice.utils.ParseUtil;

@Controller
@RequestMapping(value = "/feedback/admin")
public class FeedbakAdminController {

	private IAdminAnalyticService adminAnalyticService;
	
	@Autowired
	public FeedbakAdminController(IAdminAnalyticService adminAnalyticService) {
		this.adminAnalyticService = adminAnalyticService;
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/data-market", method = RequestMethod.GET)
	public String readDataMarket(final Model model) {
		model.addAttribute("dashboardMarketplace", adminAnalyticService.findDashboardMarketplace());
		return "adminDataMarketplace";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/data-feedback", method = RequestMethod.GET)
	public String readDataFeedback(final Model model) {
		model.addAttribute("dashboardFeedback", adminAnalyticService.findDashboardFeedback());
		return "adminDataFeedback";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/data-content", method = RequestMethod.GET)
	public String readDataContent(final Model model) {
		model.addAttribute("dashboardContent", adminAnalyticService.findDashboardContent());
		return "adminDataContent";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/data-linked", method = RequestMethod.GET)
	public String readDataLinked(final Model model) {
		model.addAttribute("dashboardLinked", adminAnalyticService.findAdmDashboardLinked());
		return "adminDataLinked";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/data-admin", method = RequestMethod.GET)
	public String readDataAdmin(final Model model) {
		model.addAttribute("dashboardAdmin", adminAnalyticService.findDashboardAdmin());
		return "adminDataAdmin";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/data-favborite", method = RequestMethod.GET)
	public String readDataFavorite(final Model model) {
		model.addAttribute("dashboardFavorite", adminAnalyticService.findDashboardFavorite());
		return "adminDataFavorite";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chart-access", method = RequestMethod.GET)
	public String readChartAccess(final Model model) {
		model.addAttribute("days", ParseUtil.parseWeeksubDayForNow());
		model.addAttribute("chartAccess", adminAnalyticService.readAdmAnalyticAccess());
		return "adminAnalyticAccess";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chart-premium", method = RequestMethod.GET)
	public String readChartPremium(final Model model) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("chartPremium", adminAnalyticService.readAdmAnalyticPremium());
		return "adminAnalyticPremium";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chart-visit", method = RequestMethod.GET)
	public String readChartVisit(final Model model) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("chartVisit", adminAnalyticService.readAdmAnalyticVisit());
		return "adminAnalyticVisit";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "/chart-market", method = RequestMethod.GET)
	public String readChartMarket(final Model model, @RequestParam("filter") final Integer filter) {
		model.addAttribute("chartMarket", adminAnalyticService.readAdmAnalyticMarket(filter));
		return "adminAnalyticMarket";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chart-analyse", method = RequestMethod.GET)
	public String readChartAnalyse(final Model model) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("chartAnalyse", adminAnalyticService.readAdmAnalyticAnalyse());
		return "adminAnalyticAnalyse";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chart-journal", method = RequestMethod.GET)
	public String readChartJournal(final Model model) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("chartJournal", adminAnalyticService.readAdmAnalyticJournal());
		return "adminAnalyticJournal";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chart-guest", method = RequestMethod.GET)
	public String readChartGuest(final Model model) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("chartGuest", adminAnalyticService.readAdmAnalyticGuest());
		return "adminAnalyticGuest";
	}
	
}
