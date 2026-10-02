package com.rinitec.algerieoffice.web.controllers.user;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.blacklist.IBlacklistMemberService;
import com.rinitec.algerieoffice.services.user.alerts.IAlertSettingService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.user.setting.CookiesForm;
import com.rinitec.algerieoffice.web.form.user.setting.GenralForm;
import com.rinitec.algerieoffice.web.form.user.setting.NotificationsForm;
import com.rinitec.algerieoffice.web.form.user.setting.SecurityForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.listener.events.OnLogoutEvent;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/user/settings")
public class UserSettingController {

	private IAttributeService attributeService;
	private IAlertSettingService alertSettingService;
	private IBlacklistMemberService blacklistMemberService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UserSettingController(IAttributeService attributeService, IAlertSettingService alertSettingService, 
			IBlacklistMemberService blacklistMemberService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.alertSettingService = alertSettingService;
		this.blacklistMemberService = blacklistMemberService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/general", method = RequestMethod.GET)
	public String showGeneral(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = attributeService.attributeConfig(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("general", new GenralForm(localUser.getUserId(), currentConfig));
		return "userSettingGeneral";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param genralForm
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/general/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateCoordinates(final HttpServletResponse response, @Valid final GenralForm genralForm, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(!genralForm.getId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		final CurrentConfig currentConfig = new CurrentConfig(config);
		currentConfig.parseSetting(genralForm);
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_SETTING_GENERAL, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public String showNotifications(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentConfig currentConfig = attributeService.attributeConfig(model, config, localUser);
		model.addAttribute("notises", alertSettingService.readNotificationsForm(localUser.getUserId(), currentConfig.getSounds()));
		return "userSettingNotifications";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param notificationsForm
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/notifications/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateNotifications(final HttpServletResponse response, @Valid final NotificationsForm notificationsForm, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(!notificationsForm.getId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		final CurrentConfig currentConfig = new CurrentConfig(config);
		alertSettingService.updateAlertSetting(notificationsForm);
		currentConfig.parseSounds(notificationsForm.getSounds());
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_CONFIG, currentConfig.toString()), 365);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_SETTING_NOTISE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/security", method = RequestMethod.GET)
	public String showSecurity(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("security", alertSettingService.readSecurityForm(localUser.getUserId()));
		return "userSettingSecurity";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param securityForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/security/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateSecurity(final HttpServletRequest request, @Valid final SecurityForm securityForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!securityForm.getId().equals(localUser.getUserId())) {
			throw new AccessAuthorityException();
		}
		alertSettingService.updateAlertSetting(securityForm);
		if(securityForm.isLogouted()) {
			eventPublisher.publishEvent(new OnLogoutEvent(localUser.getUser().getEmail()));
		}
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_SETTING_SECURITY, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @param cookies
	 * @return
	 */
	@RequestMapping(value = "/cookies", method = RequestMethod.GET)
	public String showCookies(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config,
			@CookieValue(value = RequestUtil.COOKIE_SETTING, defaultValue = "") final String cookies) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("cookies", new CookiesForm(cookies));
		return "userSettingCookies";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param response
	 * @param cookiesForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/cookies/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateCookies(final HttpServletResponse response, @Valid final CookiesForm cookiesForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		RequestUtil.addCookie(response, new Cookie(RequestUtil.COOKIE_SETTING, cookiesForm.toString()), 365);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_SETTING_COOKIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/blacklist", method = RequestMethod.GET)
	public String showBlacklist(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_SETTING01, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_SETTING01));
		return "userSettingBlacklist";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/blacklist-load", method = RequestMethod.GET)
	public String loadBlacklist(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_SETTING01, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", blacklistMemberService.findBlackList(localUser.getUserId(), search, sort, rows, page, hasDesc));
		return "userListBlacklist";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/blacklist/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteBlacklist(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		blacklistMemberService.deleteBlacklist(id, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_BLACKLIST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/blacklist/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteBlacklists(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		blacklistMemberService.deleteBlacklists(lines, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_BLACKLISTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/blacklist/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllBlacklists(@AuthenticationPrincipal final LocalUser localUser) {
		blacklistMemberService.deleteAllBlacklists(localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLBLACKLIST, null));
		return new GenericResponse("success");
	}
	
}
