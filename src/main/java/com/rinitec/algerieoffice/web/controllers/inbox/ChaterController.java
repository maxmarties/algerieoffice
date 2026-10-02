package com.rinitec.algerieoffice.web.controllers.inbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.inbox.IChaterService;

@Controller
@RequestMapping(value = "/inbox/chater")
public class ChaterController {

	private IChaterService chaterService;
	
	@Autowired
	public ChaterController(IChaterService chaterService) {
		this.chaterService = chaterService;
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
	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public String loadChaterPublic(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam("pg") final Integer page, 
			@RequestParam("rw") final Integer rows) {
		model.addAttribute("currPage", page);
		model.addAttribute("currUserId", localUser.getUserId());
		model.addAttribute("list", chaterService.findAllChaterPublic(page, rows));
		return "inboxListChaters";
	}
	
}
