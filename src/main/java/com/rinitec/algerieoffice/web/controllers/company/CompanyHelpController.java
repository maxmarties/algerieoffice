package com.rinitec.algerieoffice.web.controllers.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.company.help.ITaskHelpService;
import com.rinitec.algerieoffice.utils.RequestUtil;

@Controller
@RequestMapping(value = "/company/help")
public class CompanyHelpController {

	private IAttributeService attributeService;
	private ITaskHelpService taskHelpService;
	
	@Autowired
	public CompanyHelpController(IAttributeService attributeService, ITaskHelpService taskHelpService) {
		this.attributeService = attributeService;
		this.taskHelpService = taskHelpService;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showHome(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		return "companyHelpHome";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/begginer", method = RequestMethod.GET)
	public String showBegginer(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("begginer", taskHelpService.readBegginerTask(localUser.getCompanyId()));
		return "companyHelpBegginer";
	}
	
}
