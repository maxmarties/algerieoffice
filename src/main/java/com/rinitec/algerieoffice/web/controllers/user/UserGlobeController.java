package com.rinitec.algerieoffice.web.controllers.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.rinitec.algerieoffice.services.user.favorite.IFavoriteGlobeService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/user/globe")
public class UserGlobeController {

	private IAttributeService attributeService;
	private IFavoriteGlobeService favoriteGlobeService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UserGlobeController(IAttributeService attributeService, IFavoriteGlobeService favoriteGlobeService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.favoriteGlobeService = favoriteGlobeService;
		this.eventPublisher = eventPublisher;
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
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public String showFavoriteCompanies(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_GLOBE01, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_GLOBE01));
		return "userGlobeCompanies";
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
	@RequestMapping(value = "/companies-load", method = RequestMethod.GET)
	public String loadFavoriteCompanies(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_GLOBE01, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", favoriteGlobeService.findFavoriteCompanyList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListGlobeCompanies";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/companies/alert", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse alertFavoriteCompany(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteGlobeService.alertFavoriteCompany(id, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/companies/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteFavoriteCompany(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteGlobeService.deleteFavoriteCompany(id, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_COMPANY, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/companies/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFavoriteCompanies(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteGlobeService.deleteFavoriteCompanies(lines, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_COMPANIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/companies/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllFavoriteCompanies(@AuthenticationPrincipal final LocalUser localUser) {
		favoriteGlobeService.deleteAllFavoriteCompanies(localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLCOMPANY, null));
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
	@RequestMapping(value = "/profiles", method = RequestMethod.GET)
	public String showFavoriteAccounts(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_GLOBE02, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_GLOBE02));
		return "userGlobeProfiles";
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
	@RequestMapping(value = "/profiles-load", method = RequestMethod.GET)
	public String loadFavoriteAccounts(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_GLOBE02, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", favoriteGlobeService.findFavoriteAccountList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListGlobeProfiles";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/profiles/alert", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse alertFavoriteAccount(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteGlobeService.alertFavoriteAccount(id, localUser.getUserId());
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/profiles/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteFavoriteAccount(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteGlobeService.deleteFavoriteAccount(id, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_MEMBER, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/profiles/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFavoriteAccounts(@RequestParam("lines[]") final List<String> lines,
			@AuthenticationPrincipal final LocalUser localUser) {
		favoriteGlobeService.deleteFavoriteAccounts(lines, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_MEMBERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/profiles/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFavoriteAccounts(@AuthenticationPrincipal final LocalUser localUser) {
		favoriteGlobeService.deleteAllFavoriteAccounts(localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLMEMBER, null));
		return new GenericResponse("success");
	}
	
}
