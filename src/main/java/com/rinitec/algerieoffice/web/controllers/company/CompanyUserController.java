package com.rinitec.algerieoffice.web.controllers.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.inbox.ITalkService;
import com.rinitec.algerieoffice.services.user.easylist.IEasylistDetailService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnJournalUserEvent;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/company-user")
public class CompanyUserController {

	private IAttributeService attributeService;
	private ITalkService talkService;
	private IPremiumService premiumService;
	private IEasylistDetailService easylistDetailService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyUserController(IAttributeService attributeService, ITalkService talkService, IPremiumService premiumService, 
			IEasylistDetailService easylistDetailService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.talkService = talkService;
		this.premiumService = premiumService;
		this.easylistDetailService = easylistDetailService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/feedback/communications", method = RequestMethod.GET)
	public String showCommunications(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FEEDBACK03, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_FEEDBACK03));
		return "userFeedbackCommunications";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
	@RequestMapping(value = "/feedback/communications-load", method = RequestMethod.GET)
	public String loadCommunications(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_FEEDBACK03, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", talkService.findTalkList(localUser.getCompanyId(), filter, sort, rows, page, hasDesc));
		return "userListCommunications";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/feedback/communications/consulted", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse consultCommunication(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		talkService.consultTalk(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_CONSULT_COMMUNICATION, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/feedback/communications/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteCommunication(final HttpServletRequest request, @RequestParam("id") final String id,
			@AuthenticationPrincipal final LocalUser localUser) {
		talkService.deleteTalk(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_CONSULT_COMMUNICATION, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/feedback/communications/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteCommunications(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		talkService.deleteTalks(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_COMMUNICATION, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/feedback/communications/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllCommunications(@AuthenticationPrincipal final LocalUser localUser) {
		talkService.deleteAllTalks(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_All_COMMUNICATION, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/companies", method = RequestMethod.GET)
	public String showEasylistCompanies(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY01, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY01));
		return "userEasylistCompanies";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
	@RequestMapping(value = "/easylist/companies-load", method = RequestMethod.GET)
	public String loadEasylistCompanies(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY01, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistCompanies(localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListEasylistCompanies";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/companies/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEasylistCompany(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final EasylistCompany easylistCompany = easylistDetailService.deleteEasylistCompany(id, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_COMPANY, easylistCompany.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/companies/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEasylistCompanies(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteEasylistCompanies(lines, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_COMPANIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/companies/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEasylistCompanies(@AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteAllEasylistCompanies(localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_ALL_COMPANIES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/companies/{id}", method = RequestMethod.GET)
	public String showEasylistCompany(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY11, defaultValue = "") final String table) {
		final EasylistCompany easylist = easylistDetailService.readEasylistCompany(id, localUser.getUserId());
		if(easylist == null) {
			return "redirect:/company-user/easylist/companies?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("easylist", easylist);
		model.addAttribute("easyurl", "/company-user/easylist/companies/".concat(id));
		model.addAttribute("hasPremium", premiumService.hasPremium(localUser.getCompanyId()));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY11));
		return "userEasylistCompany";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/companies/{id}-load", method = RequestMethod.GET)
	public String loadEasylistCompany(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY11, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistCompany(id, localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListEasylistCompany";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/posts", method = RequestMethod.GET)
	public String showEasylistPosts(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY02, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY02));
		return "userEasylistPosts";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
	@RequestMapping(value = "/easylist/posts-load", method = RequestMethod.GET)
	public String loadEasylistPosts(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY02, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistDocuments(localUser.getUserId(), DocumentType.post, filter, search, sort, rows, page, hasDesc));
		return "userListEasylistPosts";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/posts/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEasylistPost(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final EasylistDocument easylistDocument = easylistDetailService.deleteEasylistDocument(id, localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_POST, easylistDocument.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/posts/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEasylistPosts(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteEasylistDocuments(lines, localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_POSTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/posts/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEasylistPosts(@AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteAllEasylistDocuments(localUser.getUserId(), DocumentType.post);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_ALL_POSTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/posts/{id}", method = RequestMethod.GET)
	public String showEasylistPost(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY12, defaultValue = "") final String table) {
		final EasylistDocument easylist = easylistDetailService.readEasylistDocument(id, localUser.getUserId(), DocumentType.post);
		if(easylist == null) {
			return "redirect:/company-user/easylist/posts?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("easylist", easylist);
		model.addAttribute("easyurl", "/company-user/easylist/posts/".concat(id));
		model.addAttribute("hasPremium", premiumService.hasPremium(localUser.getCompanyId()));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY12));
		return "userEasylistPost";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/posts/{id}-load", method = RequestMethod.GET)
	public String loadEasylistPost(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY12, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistPost(id, localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListEasylistPost";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/ads", method = RequestMethod.GET)
	public String showEasylistAnnonces(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY03, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY03));
		return "userEasylistAnnonces";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
	@RequestMapping(value = "/easylist/ads-load", method = RequestMethod.GET)
	public String loadEasylistAnnonces(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY03, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistDocuments(localUser.getUserId(), DocumentType.annonce, filter, search, sort, rows, page, hasDesc));
		return "userListEasylistAnnonces";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/ads/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEasylistAnnonce(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final EasylistDocument easylistDocument = easylistDetailService.deleteEasylistDocument(id, localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_ANNONCE, easylistDocument.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/ads/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEasylistAnnonces(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteEasylistDocuments(lines, localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_ANNONCES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/ads/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEasylistAnnonces(@AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteAllEasylistDocuments(localUser.getUserId(), DocumentType.annonce);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_ALL_ANNONCES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/ads/{id}", method = RequestMethod.GET)
	public String showEasylistAnnonce(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY13, defaultValue = "") final String table) {
		final EasylistDocument easylist = easylistDetailService.readEasylistDocument(id, localUser.getUserId(), DocumentType.annonce);
		if(easylist == null) {
			return "redirect:/company-user/easylist/ads?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("easylist", easylist);
		model.addAttribute("easyurl", "/company-user/easylist/ads/".concat(id));
		model.addAttribute("hasPremium", premiumService.hasPremium(localUser.getCompanyId()));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY13));
		return "userEasylistAnnonce";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/ads/{id}-load", method = RequestMethod.GET)
	public String loadEasylistAnnonce(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY13, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistAnnonce(id, localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListEasylistAnnonce";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/events", method = RequestMethod.GET)
	public String showEasylistEvents(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY04, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY04));
		return "userEasylistEvents";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
	@RequestMapping(value = "/easylist/events-load", method = RequestMethod.GET)
	public String loadEasylistEvents(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY04, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistDocuments(localUser.getUserId(), DocumentType.event, filter, search, sort, rows, page, hasDesc));
		return "userListEasylistEvents";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/events/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEasylistEvent(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final EasylistDocument easylistDocument = easylistDetailService.deleteEasylistDocument(id, localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_EVENT, easylistDocument.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/events/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEasylistEvents(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteEasylistDocuments(lines, localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_EVENTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/events/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEasylistEvents(@AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteAllEasylistDocuments(localUser.getUserId(), DocumentType.event);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_ALL_EVENTS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/events/{id}", method = RequestMethod.GET)
	public String showEasylistEvent(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY14, defaultValue = "") final String table) {
		final EasylistDocument easylist = easylistDetailService.readEasylistDocument(id, localUser.getUserId(), DocumentType.event);
		if(easylist == null) {
			return "redirect:/company-user/easylist/events?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("easylist", easylist);
		model.addAttribute("easyurl", "/company-user/easylist/events/".concat(id));
		model.addAttribute("hasPremium", premiumService.hasPremium(localUser.getCompanyId()));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY14));
		return "userEasylistEvent";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/events/{id}-load", method = RequestMethod.GET)
	public String loadEasylistEvent(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY14, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistEvent(id, localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListEasylistEvent";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/jobs", method = RequestMethod.GET)
	public String showEasylistEmployes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY05, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY05));
		return "userEasylistEmployes";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
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
	@RequestMapping(value = "/easylist/jobs-load", method = RequestMethod.GET)
	public String loadEasylistEmployes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY05, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistDocuments(localUser.getUserId(), DocumentType.employe, filter, search, sort, rows, page, hasDesc));
		return "userListEasylistEmployes";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/jobs/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEasylistEmploye(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final EasylistDocument easylistDocument = easylistDetailService.deleteEasylistDocument(id, localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_JOB, easylistDocument.getEasyname()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/jobs/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEasylistEmployes(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteEasylistDocuments(lines, localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_JOBS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/easylist/jobs/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEasylistEmployes(@AuthenticationPrincipal final LocalUser localUser) {
		easylistDetailService.deleteAllEasylistDocuments(localUser.getUserId(), DocumentType.employe);
		eventPublisher.publishEvent(new OnJournalUserEvent(localUser.getUserId(), ConstraintesJournal.USER_DELETE_EASYLIST_ALL_JOBS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/jobs/{id}", method = RequestMethod.GET)
	public String showEasylistEmploye(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY15, defaultValue = "") final String table) {
		final EasylistDocument easylist = easylistDetailService.readEasylistDocument(id, localUser.getUserId(), DocumentType.employe);
		if(easylist == null) {
			return "redirect:/company-user/easylist/jobs?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("easylist", easylist);
		model.addAttribute("easyurl", "/company-user/easylist/jobs/".concat(id));
		model.addAttribute("hasPremium", premiumService.hasPremium(localUser.getCompanyId()));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_EASY15));
		return "userEasylistEmploye";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/easylist/jobs/{id}-load", method = RequestMethod.GET)
	public String loadEasylistEmploye(final Model model, @AuthenticationPrincipal final LocalUser localUser, @PathVariable("id") final String id, 
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_EASY15, defaultValue = "") final String table) {
		if(!premiumService.hasPremium(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", easylistDetailService.findEasylistEmploye(id, localUser.getUserId(), filter, search, sort, rows, page, hasDesc));
		return "userListEasylistEmploye";
	}
	
}
