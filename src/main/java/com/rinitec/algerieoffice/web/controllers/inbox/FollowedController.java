package com.rinitec.algerieoffice.web.controllers.inbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.inbox.IFollowedService;

@Controller
@RequestMapping(value = "/inbox/followed")
public class FollowedController {

	private IFollowedService followedService;
	
	@Autowired
	public FollowedController(IFollowedService followedService) {
		this.followedService = followedService;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param ch
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public String loadCompanies(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("ch") final String ch, @RequestParam("pg") final Integer page, @RequestParam("rw") final Integer rows) {
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currPage", page);
		model.addAttribute("currSearch", search);
		model.addAttribute("list", followedService.findAllFollowedCompany(localUser.getUserId(), search, page, rows));
		return "inboxFollowedCompanies";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param ch
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public String loadAccounts(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("ch") final String ch, @RequestParam("pg") final Integer page, @RequestParam("rw") final Integer rows) {
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currPage", page);
		model.addAttribute("currSearch", search);
		model.addAttribute("list", followedService.findAllFollowedAccount(localUser.getUserId(), search, page, rows));
		return "inboxFollowedAccounts";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param ch
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String loadUsers(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("ch") final String ch, @RequestParam("pg") final Integer page, @RequestParam("rw") final Integer rows) {
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currPage", page);
		model.addAttribute("currSearch", search);
		model.addAttribute("list", followedService.findAllFollowedUser(localUser.getUserId(), localUser.getCompanyId(), search, page, rows));
		return "inboxFollowedUsers";
	}
	
}
