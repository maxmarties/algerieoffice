package com.rinitec.algerieoffice.web.controllers.inbox;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.inbox.ISupportService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.modal.inbox.SupportPush;

@Controller
@RequestMapping(value = "/inbox/supports")
public class SupportsController {

	private ISupportService supportService;
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	public SupportsController(ISupportService supportService, SimpMessagingTemplate messagingTemplate) {
		this.supportService = supportService;
		this.messagingTemplate = messagingTemplate;
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	public Integer countSupports() {
		return supportService.countSupportAdmin();
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menuSupports(final Model model, @RequestParam("limit") final Integer limit) {
		model.addAttribute("hasAllConsulted", supportService.hasAllConsulted());
		model.addAttribute("supports", supportService.findAllSupportNotification(1, limit));
		return "inboxMenuSupports";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/sub", method = RequestMethod.GET)
	public String subSupports(final Model model, @RequestParam("page") final Integer page, @RequestParam("limit") final Integer limit) {
		model.addAttribute("supports", supportService.findAllSupportNotification(page, limit));
		return "inboxSubSupports";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param consulted
	 * @return
	 */
	@RequestMapping(value = "/read-all", method = RequestMethod.POST)
	@ResponseBody
	public String readAllSupports(@RequestParam("consulted") final String consulted) {
		if(consulted.equals("true")) {
			supportService.updateAllConsultedAdmin();
		}
		return "";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/load-info", method = RequestMethod.GET)
	public String loadSupportsInfo(final Model model, @RequestParam("id") final Long userId, @RequestParam("pg") final Integer page, 
			@RequestParam("rw") final Integer rows) {
		model.addAttribute("currPage", page);
		model.addAttribute("currUserId", userId);
		model.addAttribute("infos", supportService.readSupportInfo(userId));
		model.addAttribute("list", supportService.findAllSupportSheetAdmin(userId, page, rows));
		return "inboxSheetSupports";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/load-sheet", method = RequestMethod.GET)
	public String loadSupportsSheet(final Model model, @RequestParam("id") final Long userId, @RequestParam("pg") final Integer page, 
			@RequestParam("rw") final Integer rows) {
		model.addAttribute("currPage", page);
		model.addAttribute("currUserId", userId);
		model.addAttribute("list", supportService.findAllSupportSheetAdmin(userId, page, rows));
		return "inboxSheetSupports";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param supportId
	 * @return
	 */
	@RequestMapping(value = "/update-consulted", method = RequestMethod.POST)
	@ResponseBody
	public String updateSupportConsulted(@RequestParam("id") final String supportId) {
		supportService.updateConsultedAdmin(supportId);
		return "";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/update-all", method = RequestMethod.POST)
	@ResponseBody
	public String updateAllSupportsConsulted(@RequestParam("id") final Long userId) {
		supportService.updateAllConsultedAdmin(userId);
		return "";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param userId
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/screenshot", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse uploadSupportImage(final HttpServletRequest request, @RequestParam("id") final Long userId,
			@RequestParam("file") final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		final SupportPush supportPush = supportService.addSupportAdminFile(userId, localUser.getUser(), file);
		final String email = supportService.findUserEmail(userId);
		if(email != null) {
			final List<String> admins = supportService.findAllEmailsAdminOne(localUser.getUserId());
			messagingTemplate.convertAndSendToUser(email, "/queue/support", supportPush);
			for (final String admin : admins) {
				messagingTemplate.convertAndSendToUser(admin, "/queue/supports", supportPush);
			}
		}
		final String responseURL = RequestUtil.getAppurl(request).concat(supportPush.getMessage());
		return new GenericResponse("success", responseURL);
	}
	
}
