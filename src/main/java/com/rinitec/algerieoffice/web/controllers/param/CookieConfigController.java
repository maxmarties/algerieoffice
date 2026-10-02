package com.rinitec.algerieoffice.web.controllers.param;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.CurrentGeolocate;

@Controller
@RequestMapping(value = "/cookie-config")
public class CookieConfigController {
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param defaultResult
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/default-result", method = RequestMethod.POST)
	@ResponseBody
	public String updateScreenResult(final HttpServletResponse response, @RequestParam("defaultResult") final Integer defaultResult, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setDefaultResult(defaultResult);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param defaultBlog
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/default-blog", method = RequestMethod.POST)
	@ResponseBody
	public String updateBlogResult(final HttpServletResponse response, @RequestParam("defaultBlog") final Integer defaultBlog, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setDefaultBlog(defaultBlog);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/newsletter-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateNewsletterCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setNewsletterCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/privacy-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updatePrivacyCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setCookieCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/begginer-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateBegginerCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setBegginerCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/offer-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateOfferCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setOfferCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/geolocate", method = RequestMethod.POST)
	@ResponseBody
	public String updateGeolocate(final HttpServletResponse response, @RequestParam("latitude") final String latitude, 
			@RequestParam("longitude") final String longitude) {
		final CurrentGeolocate currentGeolocate = new CurrentGeolocate(latitude, longitude);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_GEOLOCATE, currentGeolocate.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/chatbot-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateChatbotCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setChatbotCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/chatboter-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateChatboterCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setChatboterCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
}
