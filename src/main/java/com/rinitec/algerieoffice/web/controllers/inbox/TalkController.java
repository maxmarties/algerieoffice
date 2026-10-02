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
import com.rinitec.algerieoffice.services.inbox.ITalkService;

@Controller
@RequestMapping(value = "/inbox/talk")
public class TalkController {

	private ITalkService talkService;
	
	@Autowired
	public TalkController(ITalkService talkService) {
		this.talkService = talkService;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	@ResponseBody
	public Integer countTalk(@AuthenticationPrincipal final LocalUser localUser) {
		return talkService.countNewTalk(localUser.getCompanyId());
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param limit
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menuTalk(final Model model, @RequestParam("limit") final Integer limit, @AuthenticationPrincipal final LocalUser localUser) {
		final Long companyId = localUser.getCompanyId();
		model.addAttribute("hasAllConsulted", talkService.hasAllConsulted(companyId));
		model.addAttribute("talks", talkService.findAllTalk(companyId, 1, limit));
		return "inboxMenuTalk";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param page
	 * @param limit
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/sub", method = RequestMethod.GET)
	public String subTalk(final Model model, @RequestParam("page") final Integer page, @RequestParam("limit") final Integer limit, 
			@AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("talks", talkService.findAllTalk(localUser.getUser().getId(), page, limit));
		return "inboxSubTalk";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param consulted
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/read-all", method = RequestMethod.POST)
	@ResponseBody
	public String readAllTalk(@RequestParam("consulted") final String consulted, @AuthenticationPrincipal final LocalUser localUser) {
		if(consulted.equals("true")) {
			talkService.updateAllConsulted(localUser.getCompanyId());
		}
		return "";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param uuid
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/href", method = RequestMethod.GET)
	public String linkedTalk(@RequestParam("uuid") final String uuid, @AuthenticationPrincipal final LocalUser localUser) {
		final String link = talkService.getLinkAndConsultedTalk(localUser.getCompanyId(), uuid);
		if(!StringUtils.isEmpty(link)) {
			return "redirect:".concat(link);
		}
		return "redirect:/company-user/feedback/communications";
	}
	
}
