package com.rinitec.algerieoffice.web.controllers.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.utils.RequestUtil;

@Controller
@RequestMapping(value = "/maquettes")
public class MaquetteController {

	private final String[] MAQUETTE_TITLES = {"GoStore", "Bildhub", "Bacola", "Lave-auto"};
	private final String[] MAQUETTE_LINKS = {"https://demo.theme-sky.com/gostore/", "https://preview.themeforest.net/item/bildhub-construction-building-wordpress/full_screen_preview/32532776", 
			"https://klbtheme.com/bacola/product/tomatoes-on-the-vine/", "http://preview.themeforest.net/item/mister-car-wash-wordpress-theme/full_screen_preview/31348551"};
	
	private IAttributeService attributeService;
	
	@Autowired
	public MaquetteController(IAttributeService attributeService) {
		this.attributeService = attributeService;
	}
	
	@RequestMapping(value = "/{index}", method = RequestMethod.GET)
	public String showPost(final Model model, @PathVariable("index") final int index, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(index < 1 && index > 4) {
			return "redirect:/404";
		}
		attributeService.attributeNotfound(model, config);
		model.addAttribute("maquetteIndex", index);
		model.addAttribute("maquetteTitle", MAQUETTE_TITLES[index - 1]);
		model.addAttribute("maquetteLink", MAQUETTE_LINKS[index - 1]);
		return "maquette";
	}
	
}
