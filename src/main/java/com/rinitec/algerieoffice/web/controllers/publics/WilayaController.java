package com.rinitec.algerieoffice.web.controllers.publics;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.blog.IBlogSearchService;
import com.rinitec.algerieoffice.services.publics.ISectorService;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

@Controller
@RequestMapping(value = "/villes")
public class WilayaController {

	private IAttributeService attributeService;
	private ISectorService sectorService;
	private IBlogSearchService blogSearchService;
	
	@Autowired
	public WilayaController(IAttributeService attributeService, ISectorService sectorService, IBlogSearchService blogSearchService) {
		this.attributeService = attributeService;
		this.sectorService = sectorService;
		this.blogSearchService = blogSearchService;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param authentication
	 * @param wilaya
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showWilayas(final HttpServletRequest request, final Model model, final Authentication authentication, 
			@RequestParam(name = "wilaya", required = false) final Integer wilaya,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(wilaya != null && wilaya >= 1 && wilaya <= ConstraintesForm.COUNT_WILAYA) {
			return "redirect:/villes/".concat(ConstraintesURL.URL_WILAYAS[wilaya - 1]);
		}
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("wilayas", ConstraintesURL.URL_WILAYAS);
		model.addAttribute("mapsiteURL", ConstraintesURL.getWilayasMapsiteURL());
		model.addAttribute("screenAnalytics", ConstraintesForm.getSectorAnalytics());
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		return "homeWilayas";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param wilayaURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{wilayaURL}", method = RequestMethod.GET)
	public String showWilaya(final Model model, final Authentication authentication, @PathVariable("wilayaURL") final String wilayaURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final int wilaya = ConstraintesURL.getIndexWilaya(wilayaURL);
		if(wilaya == 0) {
			return "redirect:/404";
		}
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("wilaya", wilaya);
		model.addAttribute("sectors", sectorService.findWilayaSectorLinkList(wilaya));
		model.addAttribute("mapsiteURL", ConstraintesURL.getWilayaMapsiteURL(wilayaURL));
		model.addAttribute("screenAnalytics", ConstraintesForm.getWilayaAnalytics());
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		return "homeWilaya";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param wilayaURL
	 * @param sectorURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{wilayaURL}/{sectorURL}", method = RequestMethod.GET)
	public String showWilayaSector(final Model model, final Authentication authentication, 
			@PathVariable("wilayaURL") final String wilayaURL, @PathVariable("sectorURL") final String sectorURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final int wilaya = ConstraintesURL.getIndexWilaya(wilayaURL);
		if(wilaya == 0) {
			return "redirect:/404";
		}
		final int sector = ConstraintesURL.getIndexSector(sectorURL);
		if(sector == 0) {
			return "redirect:/404";
		}
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("wilaya", wilaya);
		model.addAttribute("sector", sector);
		model.addAttribute("wilayaURL", ConstraintesURL.getWilayaURL(wilayaURL));
		model.addAttribute("activities", sectorService.findWilayaActivityLinkList(wilaya, sector));
		model.addAttribute("mapsiteURL", ConstraintesURL.getWilayaSectorMapsiteURL(wilayaURL, sectorURL));
		model.addAttribute("screenAnalytics", ConstraintesForm.getWilayaAnalytics());
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		return "homeWilayaSector";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param wilayaURL
	 * @param sectorURL
	 * @param activityURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{wilayaURL}/{sectorURL}/{activityURL}", method = RequestMethod.GET)
	public String showWilayaActivity(final Model model, final Authentication authentication, @PathVariable("wilayaURL") final String wilayaURL, 
			@PathVariable("sectorURL") final String sectorURL, @PathVariable("activityURL") final String activityURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final int wilaya = ConstraintesURL.getIndexWilaya(wilayaURL);
		if(wilaya == 0) {
			return "redirect:/404";
		}
		final int sector = ConstraintesURL.getIndexSector(sectorURL);
		if(sector == 0) {
			return "redirect:/404";
		}
		final Activity activity = sectorService.findActivityByURL(activityURL);
		if(activity == null) {
			return "redirect:/404";
		}
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("desc", true);
		model.addAttribute("wilaya", wilaya);
		model.addAttribute("sector", sector);
		model.addAttribute("activity", activity);
		model.addAttribute("wilayaURL", ConstraintesURL.getWilayaURL(wilayaURL));
		model.addAttribute("sectorURL", ConstraintesURL.getWilayaSectorURL(wilayaURL, sectorURL));
		model.addAttribute("mapsiteURL", ConstraintesURL.getWilayaActivityMapsiteURL(wilayaURL, sectorURL, activityURL));
		model.addAttribute("screenAnalytics", ConstraintesForm.getWilayaAnalytics());
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		return "homeWilayaActivity";
	}
	
}
