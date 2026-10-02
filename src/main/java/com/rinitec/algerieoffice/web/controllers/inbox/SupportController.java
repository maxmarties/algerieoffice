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
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.modal.inbox.SupportPush;

@Controller
@RequestMapping(value = "/inbox/support")
public class SupportController {

	private ISupportService supportService;
	private SimpMessagingTemplate messagingTemplate;
	
	
	@Autowired
	public SupportController(ISupportService supportService, SimpMessagingTemplate messagingTemplate) {
		this.supportService = supportService;
		this.messagingTemplate = messagingTemplate;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	public Integer countSupportUser(@AuthenticationPrincipal final LocalUser localUser) {
		return supportService.countSupportUser(localUser.getUserId());
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/load-begin", method = RequestMethod.GET)
	public String loadSupportBegin(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam("pg") final Integer page, 
			@RequestParam("rw") final Integer rows) {
		final Long userId = localUser.getUserId();
		supportService.updateAllConsulted(userId);
		model.addAttribute("currPage", page);
		model.addAttribute("nbrModerators", supportService.countSupportOnline());
		model.addAttribute("list", supportService.findAllSupportSheetUser(userId, page, rows));
		return "inboxListSupport";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/load-next", method = RequestMethod.GET)
	public String loadSupportNext(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam("pg") final Integer page, 
			@RequestParam("rw") final Integer rows) {
		model.addAttribute("currPage", page);
		model.addAttribute("list", supportService.findAllSupportSheetUser(localUser.getUserId(), page, rows));
		return "inboxListSupport";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param supportId
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/update-consulted", method = RequestMethod.POST)
	@ResponseBody
	public String updateSupportConsulted(@RequestParam("id") final String supportId, @AuthenticationPrincipal final LocalUser localUser) {
		supportService.updateConsulted(localUser.getUserId(), supportId);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/update-all", method = RequestMethod.POST)
	@ResponseBody
	public String updateAllSupportConsulted(@RequestParam("id") final Long userId, @AuthenticationPrincipal final LocalUser localUser) {
		if(!localUser.getUserId().equals(userId)) {
			throw new AccessAuthorityException();
		}
		supportService.updateAllConsulted(localUser.getUserId());
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/screenshot", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse uploadSupportImage(final HttpServletRequest request, @RequestParam("file") final MultipartFile file, 
			@AuthenticationPrincipal final LocalUser localUser) {
		final SupportPush supportPush = supportService.addSupportUserFile(localUser.getUser(), file);
		final List<String> admins = supportService.findAllEmailsAdmin();
		final String responseURL = RequestUtil.getAppurl(request).concat(supportPush.getMessage());
		for (final String admin : admins) {
			messagingTemplate.convertAndSendToUser(admin, "/queue/supports", supportPush);
		}
		return new GenericResponse("success", responseURL);
	}

}
