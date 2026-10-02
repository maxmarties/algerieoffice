package com.rinitec.algerieoffice.web.controllers.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.company.dashboard.IAnalyticService;
import com.rinitec.algerieoffice.services.company.help.ITaskHelpService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.listener.events.OnReferringCompletedEvent;

@Controller
@RequestMapping(value = "/feedback/analytic")
public class FeedbakAnalyticController {

	private IAnalyticService analyticService;
	private ITaskHelpService taskHelpService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public FeedbakAnalyticController(IAnalyticService analyticService, ITaskHelpService taskHelpService, ApplicationEventPublisher eventPublisher) {
		this.analyticService = analyticService;
		this.taskHelpService = taskHelpService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "/favorite-company", method = RequestMethod.GET)
	public String readFavoriteCompanyAnalytic(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("filter") final Integer filter) {
		model.addAttribute("analyticFavorite", analyticService.readAnalyticFavorite(localUser.getCompanyId(), filter));
		return "companyAnalyticFavorite";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "/favorite-document", method = RequestMethod.GET)
	public String readFavoriteDocumentAnalytic(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("filter") final Integer filter) {
		model.addAttribute("analyticDocument", analyticService.readAnalyticDocument(localUser.getCompanyId(), filter));
		return "companyAnalyticDocument";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "/access-company", method = RequestMethod.GET)
	public String readAccessCompanyAnalytic(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("filter") final Integer filter) {
		model.addAttribute("analyticAccess", analyticService.readAnalyticAccess(localUser.getCompanyId(), filter));
		return "companyAnalyticAccess";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/activity-company", method = RequestMethod.GET)
	public String readActivityCompanyAnalytic(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("analyticActivity", analyticService.readAnalyticActivity(localUser.getCompanyId()));
		return "companyAnalyticActivity";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "/evaluation-company", method = RequestMethod.GET)
	public String readEvaluationCompanyAnalytic(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("filter") final Integer filter) {
		model.addAttribute("analyticEvaluation", analyticService.readAnalyticEvaluation(localUser.getCompanyId(), filter));
		return "companyAnalyticEvaluation";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/contact-company", method = RequestMethod.GET)
	public String readContactCompanyAnalytic(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("analyticContact", analyticService.readAnalyticStatsContact(localUser.getCompanyId()));
		return "companyAnalyticContact";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/guest-company", method = RequestMethod.GET)
	public String readGuestCompanyAnalytic(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("analyticGuest", analyticService.readAnalyticStatsDocument(localUser.getCompanyId()));
		return "companyAnalyticGuest";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/guest-post", method = RequestMethod.GET)
	public String readGuestCompanyPost(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("analyticPost", analyticService.readAnalyticStatsPost(localUser.getCompanyId()));
		return "companyAnalyticPost";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/activity-post", method = RequestMethod.GET)
	public String readActivityCompanyPost(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("months", ParseUtil.parseSixsubMonthForNow());
		model.addAttribute("activityPost", analyticService.readAnalyticPostActivity(localUser.getCompanyId()));
		return "companyAnalyticActivityPost";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chart-trafic", method = RequestMethod.GET)
	public String readTraficCompanyChart(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("days", ParseUtil.parseWeeksubDayForNow());
		model.addAttribute("chartTrafic", analyticService.readCompanyDashboardTrafic(localUser.getCompanyId()));
		return "companyChartTrafic";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chart-statistic", method = RequestMethod.GET)
	public String readStatisticCompanyChart(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("chartStatistic", analyticService.readCompanyStatistic(localUser.getCompanyId()));
		return "companyChartStatistic";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chart-communication", method = RequestMethod.GET)
	public String readCommunicationCompanyChart(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("days", ParseUtil.parseWeeksubDayForNow());
		model.addAttribute("currContact", "Communication");
		model.addAttribute("chartContact", analyticService.readCompanyCommunication(localUser.getCompanyId()));
		return "companyChartContact";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chart-prospect", method = RequestMethod.GET)
	public String readProspectCompanyChart(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("days", ParseUtil.parseWeeksubDayForNow());
		model.addAttribute("currContact", "Prospect");
		model.addAttribute("chartContact", analyticService.readCompanyProspect(localUser.getCompanyId()));
		return "companyChartContact";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chart-completed", method = RequestMethod.GET)
	public String readCompletedCompanyChart(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		final int completed = taskHelpService.countPersentCompleted(localUser.getCompanyId());
		model.addAttribute("completedChart", completed);
		eventPublisher.publishEvent(new OnReferringCompletedEvent(localUser.getCompanyId(), completed, false));
		return "companyChartCompleted";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/chart-market", method = RequestMethod.GET)
	public String readMarketCompanyChart(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("chartMarket", analyticService.readCompanyMarket(localUser.getCompanyId()));
		return "companyChartMarket";
	}
	
}
