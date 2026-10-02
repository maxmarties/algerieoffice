package com.rinitec.algerieoffice.web.controllers.publics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.blog.IBlogSearchService;
import com.rinitec.algerieoffice.services.publics.ISectorService;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

@Controller
@RequestMapping(value = "/secteurs")
public class SectorController {

	private IAttributeService attributeService;
	private ISectorService sectorService;
	private IBlogSearchService blogSearchService;
	
	@Autowired
	public SectorController(IAttributeService attributeService, ISectorService sectorService, IBlogSearchService blogSearchService) {
		this.attributeService = attributeService;
		this.sectorService = sectorService;
		this.blogSearchService = blogSearchService;
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
	public String showSectors(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("sectors", ConstraintesURL.URL_SECTORS);
		model.addAttribute("screenAnalytics", ConstraintesForm.getSectorAnalytics());
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getSectorsMapsiteURL());
		return "homeSectors";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param sectorURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{sectorURL}", method = RequestMethod.GET)
	public String showSector(final Model model, final Authentication authentication, @PathVariable("sectorURL") final String sectorURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final int sector = ConstraintesURL.getIndexSector(sectorURL);
		if(sector == 0) {
			return "redirect:/404";
		}
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("sector", sector);
		model.addAttribute("activities", sectorService.findSectorLinkList(sector));
		model.addAttribute("screenAnalytics", ConstraintesForm.getSectorAnalytics());
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getSectorMapsiteURL(sectorURL));
		return "homeSector";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param activityURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/activite/{activityURL}", method = RequestMethod.GET)
	public String showSectorActivity(final Model model, final Authentication authentication, @PathVariable("activityURL") final String activityURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Activity activity = sectorService.findActivityByURL(activityURL);
		if(activity == null) {
			return "redirect:/404";
		}
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("activity", activity);
		model.addAttribute("wilayas", sectorService.findSectorActivityLinkList(activity));
		model.addAttribute("sectorURL", ConstraintesURL.getSectorURL(activity.getSector()));
		model.addAttribute("screenAnalytics", ConstraintesForm.getSectorAnalytics());
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getSectorActivityMapsiteURL(activityURL));
		return "homeSectorActivity";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param activityURL
	 * @param wilayaURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/activite/{activityURL}/{wilayaURL}", method = RequestMethod.GET)
	public String showSectorWilaya(final Model model, final Authentication authentication, @PathVariable("activityURL") final String activityURL, 
			@PathVariable("wilayaURL") final String wilayaURL, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final int wilaya = ConstraintesURL.getIndexWilaya(wilayaURL);
		if(wilaya == 0) {
			return "redirect:/404";
		}
		final Activity activity = sectorService.findActivityByURL(activityURL);
		if(activity == null) {
			return "redirect:/404";
		}
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("desc", true);
		model.addAttribute("wilaya", wilaya);
		model.addAttribute("activity", activity);
		model.addAttribute("sectorURL", ConstraintesURL.getSectorURL(activity.getSector()));
		model.addAttribute("activityURL", ConstraintesURL.getSectorActivityURL(activityURL));
		model.addAttribute("screenAnalytics", ConstraintesForm.getWilayaAnalytics());
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getSectorWilayaMapsiteURL(activityURL, wilayaURL));
		return "homeSectorWilaya";
	}
	
}
