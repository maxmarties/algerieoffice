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

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.user.favorite.IFavoriteDocumentService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/user/favorite")
public class UserFavoriteController {

	private IAttributeService attributeService;
	private IFavoriteDocumentService favoriteDocumentService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public UserFavoriteController(IAttributeService attributeService, IFavoriteDocumentService favoriteDocumentService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.favoriteDocumentService = favoriteDocumentService;
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
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String showFavoritePosts(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FAVORITE01, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_FAVORITE01));
		return "userFavoritePosts";
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
	@RequestMapping(value = "/posts-load", method = RequestMethod.GET)
	public String loadFavoritePosts(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FAVORITE01, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", favoriteDocumentService.findFavoritePostList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListFavoritePosts";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/posts/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteFavoritePost(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteFavoriteDocument(id, localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_POST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/posts/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFavoritePosts(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteFavoriteDocuments(lines, localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_POSTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/posts/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFavoritePosts(@AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteAllFavoriteDocuments(localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLPOST, null));
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
	@RequestMapping(value = "/ads", method = RequestMethod.GET)
	public String showFavoriteAnnonces(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FAVORITE02, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_FAVORITE02));
		return "userFavoriteAnnonces";
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
	@RequestMapping(value = "/ads-load", method = RequestMethod.GET)
	public String loadFavoriteAnnonces(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FAVORITE02, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", favoriteDocumentService.findFavoriteAnnonceList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListFavoriteAnnonces";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteFavoriteAnnonce(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteFavoriteDocument(id, localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ANNONCE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFavoriteAnnonces(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteFavoriteDocuments(lines, localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ANNONCES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllFavoriteAnnonces(@AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteAllFavoriteDocuments(localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLANNONCE, null));
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
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public String showFavoriteEvents(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FAVORITE03, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_FAVORITE03));
		return "userFavoriteEvents";
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
	@RequestMapping(value = "/events-load", method = RequestMethod.GET)
	public String loadFavoriteEvents(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FAVORITE03, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", favoriteDocumentService.findFavoriteEventList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListFavoriteEvents";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteFavoriteEvent(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteFavoriteDocument(id, localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EVENT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFavoriteEvents(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteFavoriteDocuments(lines, localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EVENTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/events/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllFavoriteEvents(@AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteAllFavoriteDocuments(localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLEVENT, null));
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
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public String showFavoriteEmployes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FAVORITE04, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_FAVORITE04));
		return "userFavoriteEmployes";
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
	@RequestMapping(value = "/jobs-load", method = RequestMethod.GET)
	public String loadFavoriteEmployes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FAVORITE04, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", favoriteDocumentService.findFavoriteEmployetList(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListFavoriteEmployes";
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteFavoriteEmploye(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteFavoriteDocument(id, localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EMPLOYE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFavoriteEmployes(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteFavoriteDocuments(lines, localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EMPLOYES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY ACCOUNT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllFavoriteEmployes(@AuthenticationPrincipal final LocalUser localUser) {
		favoriteDocumentService.deleteAllFavoriteDocuments(localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_ALLEMPLOYE, null));
		return new GenericResponse("success");
	}
	
}
