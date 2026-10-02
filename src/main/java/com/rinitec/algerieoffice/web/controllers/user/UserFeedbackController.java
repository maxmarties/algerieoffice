package com.rinitec.algerieoffice.web.controllers.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.rinitec.algerieoffice.services.inbox.IMessageService;
import com.rinitec.algerieoffice.services.inbox.INotificationService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/user/feedback")
public class UserFeedbackController {

	private IAttributeService attributeService;
	private INotificationService notificationService;
	private IMessageService messageService;
	
	@Autowired
	public UserFeedbackController(IAttributeService attributeService, INotificationService notificationService, 
			IMessageService messageService) {
		this.attributeService = attributeService;
		this.notificationService = notificationService;
		this.messageService = messageService;
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
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public String showMessages(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FEEDBACK01, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("choseUsers", messageService.findAllUserMessage(localUser.getUserId(), true));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_FEEDBACK01));
		return "userFeedbackMessages";
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
	@RequestMapping(value = "/messages-load", method = RequestMethod.GET)
	public String loadMessages(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Long filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FEEDBACK01, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", messageService.findMessagesList(localUser.getUserId(), true, filter, search, sort, rows, page, hasDesc));
		return "userListMessages";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/messages/consulted", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse consultMessage(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		messageService.consultMessage(id, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/messages/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteMessage(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		messageService.deleteMessage(id, localUser.getUserId(), true);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/messages/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteMessages(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		messageService.deleteMessages(lines, localUser.getUserId(), true);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/messages/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllMessages(@AuthenticationPrincipal final LocalUser localUser) {
		messageService.deleteAllMessages(localUser.getUserId(), true);
		return new GenericResponse("success");
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
	@RequestMapping(value = "/senders", method = RequestMethod.GET)
	public String showSenders(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FEEDBACK01, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("choseUsers", messageService.findAllUserMessage(localUser.getUserId(), false));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_FEEDBACK01));
		return "userFeedbackSenders";
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
	@RequestMapping(value = "/senders-load", method = RequestMethod.GET)
	public String loadSenders(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Long filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FEEDBACK01, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", messageService.findMessagesList(localUser.getUserId(), false, filter, search, sort, rows, page, hasDesc));
		return "userListSenders";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/senders/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteSender(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		messageService.deleteMessage(id, localUser.getUserId(), false);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/senders/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteSenders(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		messageService.deleteMessages(lines, localUser.getUserId(), false);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/senders/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllSenders(@AuthenticationPrincipal final LocalUser localUser) {
		messageService.deleteAllMessages(localUser.getUserId(), false);
		return new GenericResponse("success");
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
	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public String showNotifications(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FEEDBACK02, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		notificationService.resetInboxNotification(localUser.getUserId());
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_FEEDBACK02));
		return "userFeedbackNotifications";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param fl
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/notifications-load", method = RequestMethod.GET)
	public String loadNotifications(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String fl, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FEEDBACK02, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final Boolean filter = !StringUtils.isEmpty(fl) ? fl.equals("true") : null;
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", notificationService.findNotificationList(localUser.getUserId(), filter, sort, rows, page, hasDesc));
		return "userListNotifications";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notifications/consulted", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse consultNotification(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		notificationService.consultNotification(id, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notifications/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteNotification(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		notificationService.deleteNotification(id, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notifications/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteNotifications(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		notificationService.deleteNotifications(lines, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/notifications/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllNotifications(@AuthenticationPrincipal final LocalUser localUser) {
		notificationService.deleteAllNotifications(localUser.getUserId());
		return new GenericResponse("success");
	}
	
}
