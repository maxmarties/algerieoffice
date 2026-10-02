package com.rinitec.algerieoffice.web.controllers.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.explorer.IExplorerPromoteService;
import com.rinitec.algerieoffice.services.user.dashboard.IDashboardUserService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.listener.events.OnReferringCompletedEvent;

@Controller
@RequestMapping(value = "/feedback/dashboard")
public class FeedbakDashboardController {

	private IExplorerPromoteService explorerPromoteService;
	private IDashboardUserService dashboardUserService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public FeedbakDashboardController(IExplorerPromoteService explorerPromoteService, IDashboardUserService dashboardUserService, ApplicationEventPublisher eventPublisher) {
		this.explorerPromoteService = explorerPromoteService;
		this.dashboardUserService = dashboardUserService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/campaign", method = RequestMethod.GET)
	public String readCampaign(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		if(companyId != null) {
			model.addAttribute("campaign", explorerPromoteService.readCampaignFeedback(companyId));
		}
		return "explorerFeedbackCampaign";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/campaign/click", method = RequestMethod.POST)
	@ResponseBody
	public String clickCampaign(@RequestParam("id") final String id) {
		explorerPromoteService.incrementClickCampaign(id);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/user-completed", method = RequestMethod.GET)
	public String readCompletedProfile(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("chartCompleted", dashboardUserService.countCompletedProfile(localUser.getUser()));
		return "userChartCompleted";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/user-perform", method = RequestMethod.GET)
	public String readPerformProfile(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		final int completed = dashboardUserService.countPerformProfile(localUser.getUserId());
		model.addAttribute("chartPerform", completed);
		eventPublisher.publishEvent(new OnReferringCompletedEvent(localUser.getUserId(), completed, true));
		return "userChartPerform";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/user-analytic", method = RequestMethod.GET)
	public String readAnalyticProfile(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("days", ParseUtil.parseWeeksubDayForNow());
		model.addAttribute("chartAnalytic", dashboardUserService.readDashboardAnalytic(localUser.getUserId()));
		return "userChartAnalytic";
	}
	
}
