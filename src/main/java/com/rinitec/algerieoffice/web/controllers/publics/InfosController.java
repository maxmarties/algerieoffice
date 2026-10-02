package com.rinitec.algerieoffice.web.controllers.publics;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.publics.IMemberService;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

@Controller
@RequestMapping(value = "/infos")
public class InfosController {

	private IAttributeService attributeService;
	private IMemberService memberService;
	
	@Autowired
	public InfosController(IAttributeService attributeService, IMemberService memberService) {
		this.attributeService = attributeService;
		this.memberService = memberService;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/faq", method = RequestMethod.GET)
	public String showFaq(final HttpServletRequest request, final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("sect", request.getParameter("sect"));
		model.addAttribute("mapsiteURL", ConstraintesURL.getInfosMapsiteURL("faq"));
		return "homeInfosFaq";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/cgu", method = RequestMethod.GET)
	public String showCondition(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getInfosMapsiteURL("cgu"));
		return "homeInfosCondition";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/politique-confidentialite", method = RequestMethod.GET)
	public String showPolitic(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getInfosMapsiteURL("politique-confidentialite"));
		return "homeInfosPolitic";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/mentions-legales", method = RequestMethod.GET)
	public String showMention(final HttpServletRequest request, final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("sect", request.getParameter("sect"));
		model.addAttribute("mapsiteURL", ConstraintesURL.getInfosMapsiteURL("mentions-legales"));
		return "homeInfosMention";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/plan-du-site", method = RequestMethod.GET)
	public String showMapsite(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getInfosMapsiteURL("plan-du-site"));
		return "homeInfosMapsite";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/charte-bonnes-pratiques", method = RequestMethod.GET)
	public String showChart(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getInfosMapsiteURL("charte-bonnes-pratiques"));
		return "homeInfosChart";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/credits", method = RequestMethod.GET)
	public String showCredit(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getInfosMapsiteURL("credits"));
		return "homeInfosCredit";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/temoignages-clients", method = RequestMethod.GET)
	public String showTestimonies(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("testimonies", memberService.findLastMemberTestimonial(20));
		model.addAttribute("mapsiteURL", ConstraintesURL.getInfosMapsiteURL("temoignages-clients"));
		return "homeInfosTestimonies";
	}
	
}
