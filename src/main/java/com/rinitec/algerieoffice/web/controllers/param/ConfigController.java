package com.rinitec.algerieoffice.web.controllers.param;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/config")
public class ConfigController {

	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/menu-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateMenuCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setMenuCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/panel-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updatePanelCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setPanelCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/chater-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateChaterCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setChaterCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/welcome-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateWelcomeCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setWelcome(!collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/started-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateStartedCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setStarted(!collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param collapsed
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/identity-collapse", method = RequestMethod.POST)
	@ResponseBody
	public String updateIdentityCollapse(final HttpServletResponse response, @RequestParam("collapsed") final boolean collapsed, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setIdentityCollapse(collapsed);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param defaultRow
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/default-row", method = RequestMethod.POST)
	@ResponseBody
	public String updateTableRows(final HttpServletResponse response, @RequestParam("defaultRow") final Integer defaultRow, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.setDefaultRow(defaultRow);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		return "";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param currentTable
	 * @return
	 */
	@RequestMapping(value = "/column-table", method = RequestMethod.POST)
	@ResponseBody
	public String updateColumnTable(final HttpServletResponse response, @Valid final CurrentTable currentTable) {
		RequestUtil.addCookie(response, new Cookie(currentTable.getCookieName(), currentTable.parseCookie()), 365);
		return "";
	}
	
}
