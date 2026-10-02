package com.rinitec.algerieoffice.web.controllers.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.blacklist.IBlacklistCompanyService;
import com.rinitec.algerieoffice.services.company.tools.IRecycleService;
import com.rinitec.algerieoffice.services.company.tools.ISettingService;
import com.rinitec.algerieoffice.services.company.tools.ISubscribeService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.company.tools.BlacklistForm;
import com.rinitec.algerieoffice.web.form.company.tools.OrderPremiumForm;
import com.rinitec.algerieoffice.web.form.company.tools.SettingForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnSupportEvent;
import com.rinitec.algerieoffice.web.modal.CurrentCompany;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/company/tools")
public class CompanyToolsController {

	private IAttributeService attributeService;
	private ISettingService settingService;
	private IRecycleService recycleService;
	private IBlacklistCompanyService blacklistService;
	private ISubscribeService subscribeService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyToolsController(IAttributeService attributeService, ISettingService settingService, IRecycleService recycleService, 
			IBlacklistCompanyService blacklistService, ISubscribeService subscribeService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.settingService = settingService;
		this.recycleService = recycleService;
		this.blacklistService = blacklistService;
		this.subscribeService = subscribeService;
		this.eventPublisher = eventPublisher;
	}

	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public String showSetting(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("setting", settingService.readSettingForm(localUser.getCompanyId()));
		return "companyToolsSetting";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param settingForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/setting/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateSetting(@Valid final SettingForm settingForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!settingForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		settingService.updateSettings(settingForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_SETTING, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/recycle/posts", method = RequestMethod.GET)
	public String showPosts(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS2_1, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_TOOLS2_1));
		model.addAttribute("choseCategories", recycleService.findAllCategory(localUser.getCompanyId()));
		return "companyToolsRecyclePost";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
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
	@RequestMapping(value = "/recycle/posts-load", method = RequestMethod.GET)
	public String loadPosts(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS2_1, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", recycleService.findPostsList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListRecyclePost";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/posts/restore", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse restorePost(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Post post = recycleService.restorePost(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_RESTORE_RECYCLE_POST, post.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/posts/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deletePost(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Post post = recycleService.deletePost(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_RECYCLE_POST, post.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/posts/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deletePosts(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		recycleService.deletePosts(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_RECYCLE_POST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/posts/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllPosts(@AuthenticationPrincipal final LocalUser localUser) {
		recycleService.deleteAllPosts(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_RECYCLE_POST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/recycle/promotes", method = RequestMethod.GET)
	public String showPromotes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS2_2, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_TOOLS2_2));
		return "companyToolsRecyclePromote";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
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
	@RequestMapping(value = "/recycle/promotes-load", method = RequestMethod.GET)
	public String loadPromotes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS2_2, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", recycleService.findPromotesList(localUser.getCompanyId(), search, sort, rows, page, hasDesc));
		return "companyListRecyclePromote";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/promotes/restore", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse restorePromote(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Promote promote = recycleService.restorePromote(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_RESTORE_RECYCLE_PROMOTE, promote.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/promotes/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deletePromote(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Promote promote = recycleService.deletePromote(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_RECYCLE_PROMOTE, promote.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/promotes/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deletePromotes(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		recycleService.deletePromotes(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_RECYCLE_PROMOTE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/promotes/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllPromotes(@AuthenticationPrincipal final LocalUser localUser) {
		recycleService.deleteAllPromotes(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_RECYCLE_PROMOTE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/recycle/ads", method = RequestMethod.GET)
	public String showAnnonces(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS2_3, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_TOOLS2_3));
		return "companyToolsRecycleAnnonce";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
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
	@RequestMapping(value = "/recycle/ads-load", method = RequestMethod.GET)
	public String loadAnnonces(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS2_3, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", recycleService.findAnnoncesList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListRecycleAnnonce";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/ads/restore", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse restoreAnnonce(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Annonce annonce = recycleService.restoreAnnonce(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_RESTORE_RECYCLE_ADS, annonce.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/ads/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAnnonce(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Annonce annonce = recycleService.deleteAnnonce(id, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_RECYCLE_ADS, annonce.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/ads/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAnnonces(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		recycleService.deleteAnnonces(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_RECYCLE_ADS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/ads/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAnnonces(@AuthenticationPrincipal final LocalUser localUser) {
		recycleService.deleteAllAnnonces(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_RECYCLE_ADS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/recycle/jobs", method = RequestMethod.GET)
	public String showEmployes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS2_4, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_TOOLS2_4));
		return "companyToolsRecycleEmployes";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
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
	@RequestMapping(value = "/recycle/jobs-load", method = RequestMethod.GET)
	public String loadEmployes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS2_2, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", recycleService.findEmployesList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListRecycleEmploye";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/jobs/restore", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse restoreEmploye(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Employe employe = recycleService.restoreEmploye(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_RESTORE_RECYCLE_JOB, employe.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/jobs/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEmploye(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Employe employe = recycleService.deleteEmploye(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_RECYCLE_JOB, employe.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/jobs/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEmployes(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		recycleService.deleteEmployes(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_RECYCLE_JOB, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/recycle/jobs/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEmployes(@AuthenticationPrincipal final LocalUser localUser) {
		recycleService.deleteAllEmployes(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_RECYCLE_JOB, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/black-list", method = RequestMethod.GET)
	public String showBlacklists(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS1, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_TOOLS1));
		return "companyToolsBlacklists";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
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
	@RequestMapping(value = "/black-list-load", method = RequestMethod.GET)
	public String loadBlacklists(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS1, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", blacklistService.findBlackList(localUser.getCompanyId(), search, sort, rows, page, hasDesc));
		return "companyListBlacklists";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/black-list/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteBlacklist(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		blacklistService.deleteBlacklist(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_BLACKLIST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/black-list/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteBlacklists(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		blacklistService.deleteBlacklists(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_BLACKLIST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/black-list/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllBlacklists(@AuthenticationPrincipal final LocalUser localUser) {
		blacklistService.deleteAllBlacklists(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_BLACKLIST, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/black-list/new", method = RequestMethod.GET)
	public String showBlacklist(final Model model, @RequestParam("id") final Long id, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final BlacklistForm blacklistForm = blacklistService.readBlacklistForm(localUser.getCompanyId(), id);
		if(blacklistForm == null) {
			return "redirect:/company/tools//black-list?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("blacklist", blacklistForm);
		return "companyToolsBlacklist";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param blacklistForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/black-list/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveBlacklist(final HttpServletRequest request, @Valid final BlacklistForm blacklistForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!blacklistForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		blacklistService.addBlacklistCompany(blacklistForm, localUser.getUserId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_ADD_BLACKLIST, blacklistForm.getUsername()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/subscribes", method = RequestMethod.GET)
	public String showSubscribes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS3, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_TOOLS3));
		return "companyToolsSubscribes";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
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
	@RequestMapping(value = "/subscribes-load", method = RequestMethod.GET)
	public String loadSubscribes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_TOOLS3, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", subscribeService.findPremiumList(localUser.getCompanyId(), filter, sort, rows, page, hasDesc));
		return "companyListSubscribes";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/subscribes/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteSubscribe(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		subscribeService.deletePremium(localUser.getCompanyId(), id);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_PREMIUM, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/subscribes/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteSubscribes(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		subscribeService.deletePremiums(localUser.getCompanyId(), lines);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_PREMIUM, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/subscribes/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllSubscribes(@AuthenticationPrincipal final LocalUser localUser) {
		subscribeService.deleteAllPremiums(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_PREMIUM, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/subscribes/new", method = RequestMethod.GET)
	public String showSubscribe(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentCompany currentCompany = attributeService.attributeCompany(model, config, localUser);
		if(currentCompany.isEnabled()) {
			model.addAttribute("subscribe", subscribeService.readOrderPremiumForm(localUser.getUserId(), localUser.getCompanyId()));
		}
		return "companyToolsSubscribe";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param orderPremiumForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/subscribes/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveSubscribe(final HttpServletRequest request, @Valid final OrderPremiumForm orderPremiumForm, 
			@RequestParam(name = "file", required = true) final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		if(!orderPremiumForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		orderPremiumForm.setFile(file);
		final DocumentOrder documentOrder = subscribeService.updateDocumentOrder(orderPremiumForm, localUser.getUserId());
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), null, documentOrder.getId(), NotificationType.premium, request));
		eventPublisher.publishEvent(new OnSupportEvent(localUser.getUserId(), localUser.getUser().getEmail(), ConstraintesJournal.SUPPORT_SUBSCRIBE, request));
		return new GenericResponse("success");
	}
	
}
