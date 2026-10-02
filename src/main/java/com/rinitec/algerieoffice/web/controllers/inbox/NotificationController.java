package com.rinitec.algerieoffice.web.controllers.inbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.inbox.INotificationService;

@Controller
@RequestMapping(value = "/inbox/notification")
public class NotificationController {

	private INotificationService notificationService;
	
	@Autowired
	public NotificationController(INotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	public Integer countNotification(@AuthenticationPrincipal final LocalUser localUser) {
		return notificationService.countNotification(localUser.getUserId());
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param limit
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menuNotification(final Model model, @RequestParam("limit") final Integer limit, @AuthenticationPrincipal final LocalUser localUser) {
		final Long userId = localUser.getUserId();
		model.addAttribute("hasAllConsulted", notificationService.hasAllConsulted(userId));
		model.addAttribute("notifications", notificationService.findAllNotification(userId, 1, limit));
		return "inboxMenuNotification";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param page
	 * @param limit
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/sub", method = RequestMethod.GET)
	public String subNotification(final Model model, @RequestParam("page") final Integer page, @RequestParam("limit") final Integer limit, 
			@AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("notifications", notificationService.findAllNotification(localUser.getUserId(), page, limit));
		return "inboxSubNotification";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param reseted
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public String resetInboxNotification(@RequestParam("reseted") final String reseted, @AuthenticationPrincipal final LocalUser localUser) {
		if(reseted.equals("true")) {
			notificationService.resetInboxNotification(localUser.getUserId());
		}
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param consulted
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/read-all", method = RequestMethod.POST)
	@ResponseBody
	public String readAllNotification(@RequestParam("consulted") final String consulted, @AuthenticationPrincipal final LocalUser localUser) {
		if(consulted.equals("true")) {
			notificationService.updateAllConsulted(localUser.getUserId());
		}
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param uuid
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/href", method = RequestMethod.GET)
	public String linkedNotification(@RequestParam("uuid") final String uuid, @AuthenticationPrincipal final LocalUser localUser) {
		final String link = notificationService.getLinkAndConsultedNotification(localUser.getUserId(), uuid);
		if(!StringUtils.isEmpty(link)) {
			return "redirect:".concat(link);
		}
		return "redirect:/user/feedback/notifications";
	}
	
}
