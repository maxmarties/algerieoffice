package com.rinitec.algerieoffice.web.controllers.inbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.inbox.IMessageService;

@Controller
@RequestMapping(value = "/inbox/messages")
public class MessagesController {

	private IMessageService messageService;
	
	@Autowired
	public MessagesController(IMessageService messageService) {
		this.messageService = messageService;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	public Integer countMessages(@AuthenticationPrincipal final LocalUser localUser) {
		return messageService.countMessages(localUser.getUserId());
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
	public String menuMessages(final Model model, @RequestParam("limit") final Integer limit, 
			@AuthenticationPrincipal final LocalUser localUser) {
		final Long userId = localUser.getUserId();
		model.addAttribute("currUserId", userId);
		model.addAttribute("hasAllConsulted", messageService.hasAllConsulted(userId));
		model.addAttribute("messages", messageService.findAllMessageNotification(userId, 1, limit));
		return "inboxMenuMessages";
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
	public String subMessages(final Model model, @RequestParam("page") final Integer page, @RequestParam("limit") final Integer limit, 
			@AuthenticationPrincipal final LocalUser localUser) {
		final Long userId = localUser.getUserId();
		model.addAttribute("currUserId", userId);
		model.addAttribute("messages", messageService.findAllMessageNotification(userId, page, limit));
		return "inboxSubMessages";
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
	public String readAllMessages(@RequestParam("consulted") final String consulted, @AuthenticationPrincipal final LocalUser localUser) {
		if(consulted.equals("true")) {
			messageService.updateAllConsulted(localUser.getUserId());
		}
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param recepientId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/load-info", method = RequestMethod.GET)
	public String loadMessagesInfo(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("id") final Long recepientId, @RequestParam("pg") final Integer page,  @RequestParam("rw") final Integer rows) {
		final Long userId = localUser.getUserId();
		model.addAttribute("currPage", page);
		model.addAttribute("currRecepientId", recepientId);
		model.addAttribute("currUserId", userId);
		model.addAttribute("infos", messageService.readMessageInfo(userId, recepientId));
		model.addAttribute("list", messageService.findAllMessagePopup(userId, recepientId, page, rows));
		return "inboxMessagesPopup";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param recepientId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/load-popup", method = RequestMethod.GET)
	public String loadMessagesPopup(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("id") final Long recepientId, @RequestParam("pg") final Integer page,  @RequestParam("rw") final Integer rows) {
		final Long userId = localUser.getUserId();
		model.addAttribute("currPage", page);
		model.addAttribute("currUserId", userId);
		model.addAttribute("list", messageService.findAllMessagePopup(userId, recepientId, page, rows));
		return "inboxMessagesPopup";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param messageId
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/update-consulted", method = RequestMethod.POST)
	@ResponseBody
	public String updateMessageConsulted(@RequestParam("id") final String messageId, @AuthenticationPrincipal final LocalUser localUser) {
		messageService.updateConsulted(localUser.getUserId(), messageId);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param recepientId
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/update-all", method = RequestMethod.POST)
	@ResponseBody
	public String updateAllMessagesConsulted(@RequestParam("id") final Long recepientId, @AuthenticationPrincipal final LocalUser localUser) {
		messageService.updateAllConsulted(localUser.getUserId(), recepientId);
		return "";
	}
	
}
