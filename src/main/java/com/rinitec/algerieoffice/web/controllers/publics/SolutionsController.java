package com.rinitec.algerieoffice.web.controllers.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.publics.ISearchService;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

@Controller
@RequestMapping(value = "/solutions")
public class SolutionsController {

	private IAttributeService attributeService;
	private ISearchService searchService;
	
	@Autowired
	public SolutionsController(IAttributeService attributeService, ISearchService searchService) {
		this.attributeService = attributeService;
		this.searchService = searchService;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showHome(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getSolutionsMapsiteURL());
		return "homeSolutions";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/presentation", method = RequestMethod.GET)
	public String showToure(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getSolutionMapsiteURL("presentation"));
		return "homeSolutionTours";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/visibilite", method = RequestMethod.GET)
	public String showVisibility(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getSolutionMapsiteURL("visibilite"));
		return "homeSolutionVisibility";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/referencement", method = RequestMethod.GET)
	public String showStore(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getSolutionMapsiteURL("referencement"));
		return "homeSolutionStore";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/detect", method = RequestMethod.GET)
	public String showDetect(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("partners", searchService.findLastCompanyPremiumMini(10));
		model.addAttribute("mapsiteURL", ConstraintesURL.getSolutionMapsiteURL("detect"));
		return "homeSolutionDetect";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/easylist", method = RequestMethod.GET)
	public String showEasylist(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getSolutionMapsiteURL("easylist"));
		return "homeSolutionEasylist";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/publicite", method = RequestMethod.GET)
	public String showAds(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("mapsiteURL", ConstraintesURL.getSolutionMapsiteURL("publicite"));
		return "homeSolutionAds";
	}
	
}
