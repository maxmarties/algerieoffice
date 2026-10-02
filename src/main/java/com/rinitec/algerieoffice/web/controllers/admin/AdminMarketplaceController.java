package com.rinitec.algerieoffice.web.controllers.admin;

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
import com.rinitec.algerieoffice.persistence.modal.admins.ads.Sponsore;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.ads.ISponsoreService;
import com.rinitec.algerieoffice.services.admins.marketplace.IAdmCampaignService;
import com.rinitec.algerieoffice.services.admins.marketplace.IAdmMarketplaceService;
import com.rinitec.algerieoffice.services.admins.marketplace.IAdmPromoteService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.admins.ads.SponsoreForm;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmCampaignForm;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmOrderForm;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmPromoteForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmOrderCampagne;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmOrderPromote;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/marketplace")
public class AdminMarketplaceController {

	private IAttributeService attributeService;
	private IAdmPromoteService promoteService;
	private ISponsoreService sponsoreService;
	private IAdmCampaignService campaignService;
	private IAdmMarketplaceService marketplaceService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminMarketplaceController(IAttributeService attributeService, IAdmPromoteService promoteService, ISponsoreService sponsoreService, 
			IAdmCampaignService campaignService, IAdmMarketplaceService marketplaceService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.promoteService = promoteService;
		this.sponsoreService = sponsoreService;
		this.campaignService = campaignService;
		this.marketplaceService = marketplaceService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/promotes", method = RequestMethod.GET)
	public String showPromotes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminMarketplacePromotes";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param fl
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/promotes-load", method = RequestMethod.GET)
	public String loadPromotes(final Model model, @RequestParam("fl") final String fl, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final Boolean filter = !StringUtils.isEmpty(fl) ? fl.equals("true") : null;
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", promoteService.findAdmPromotesList(filter, search, sort, rows, page, hasDesc));
		return "adminListPromotes";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/promotes/edit", method = RequestMethod.GET)
	public String showEditPromote(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") String config) {
		final AdmPromoteForm admPromoteForm = promoteService.readAdmPromoteForm(id);
		if(admPromoteForm == null) {
			return "redirect:/admin/marketplace/promotes?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("promote", admPromoteForm);
		return "adminMarketplacePromote";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param admPromoteForm
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/promotes/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse savePromote(@AuthenticationPrincipal final LocalUser localUser, @Valid final AdmPromoteForm admPromoteForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		admPromoteForm.setFile(file);
		final Promote promote = promoteService.moderatePromote(admPromoteForm);
		if(promote == null) {
			throw new AccessAuthorityException();
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_MODERATE_PROMOTE, promote.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/promotes/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deletePromote(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Promote promote = promoteService.deletePromote(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_PROMOTE, promote.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/promotes/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deletePromotes(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		promoteService.deletePromotes(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_PROMOTES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/promotes/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllPromotes() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public String showOrders(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminMarketplaceOrders";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param fl
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/orders-load", method = RequestMethod.GET)
	public String loadOrders(final Model model, @RequestParam("fl") final String fl, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final Boolean filter = !StringUtils.isEmpty(fl) ? fl.equals("true") : null;
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", promoteService.findAdmPromoteOrdersList(filter, search, sort, rows, page, hasDesc));
		return "adminListOrders";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/orders/edit", method = RequestMethod.GET)
	public String showEditOrder(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmOrderPromote admOrderPromote = promoteService.readAdmOrderPromote(id);
		if(admOrderPromote == null) {
			return "redirect:/admin/marketplace/orders?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("promote", admOrderPromote);
		model.addAttribute("order", new AdmOrderForm(id));
		return "adminMarketplaceOrder";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param admOrderForm
	 * @return
	 */
	@RequestMapping(value = "/orders/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveOrder(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@Valid final AdmOrderForm admOrderForm) {
		final Object[] result = promoteService.validateOrder(localUser.getUserId(), admOrderForm);
		if(result == null) {
			throw new AccessAuthorityException();
		}
		final NotificationType type = admOrderForm.isResponse() ? NotificationType.orderAccepted : NotificationType.orderRejected;
		eventPublisher.publishEvent(new OnNotificationEvent(null, (Long) result[0], null, type, request));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), admOrderForm.isResponse() ? ConstraintesJournal.ADMIN_ACCEPT_PROMOTE 
				: ConstraintesJournal.ADMIN_REJECT_PROMOTE, (String) result[1]));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/orders/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteOrder(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		promoteService.deleteOrder(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_ORDER, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/orders/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteOrders(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		promoteService.deleteOrders(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_ORDERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/orders/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllOrders() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/sponsores", method = RequestMethod.GET)
	public String showSponsores(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminMarketplaceSponsores";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/sponsores-load", method = RequestMethod.GET)
	public String loadSponsores(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", sponsoreService.findSponsoresList(filter, search, sort, rows, page, hasDesc));
		return "adminListSponsores";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/sponsores/new", method = RequestMethod.GET)
	public String showNewSponsore(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("sponsore", new SponsoreForm());
		return "adminMarketplaceSponsore";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/sponsores/edit", method = RequestMethod.GET)
	public String showEditSponsore(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") String config) {
		final SponsoreForm sponsoreForm = sponsoreService.readSponsoreForm(id);
		if(sponsoreForm == null) {
			return "redirect:/admin/marketplace/sponsores?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("sponsore", sponsoreForm);
		return "adminMarketplaceSponsore";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param sponsoreForm
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/sponsores/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveSponsore(@AuthenticationPrincipal final LocalUser localUser, @Valid final SponsoreForm sponsoreForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		Sponsore sponsore = null;
		sponsoreForm.setFile(file);
		if(StringUtils.isEmpty(sponsoreForm.getId())) {
			sponsore = sponsoreService.addSponsore(sponsoreForm);
		} else {
			sponsore = sponsoreService.updateSponsore(sponsoreForm);
			if(sponsore == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), StringUtils.isEmpty(sponsoreForm.getId()) ? ConstraintesJournal.ADMIN_ADD_SPONSORE 
				: ConstraintesJournal.ADMIN_UPDATE_SPONSORE, sponsore.getUrl()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sponsores/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteSponsore(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		final Sponsore sponsore = sponsoreService.deleteSponsore(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_SPONSORE, sponsore.getUrl()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/sponsores/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteSponsores(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		sponsoreService.deleteSponsores(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_SPONSORES, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/sponsores/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllSponsores() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/campaigns", method = RequestMethod.GET)
	public String showCampaigns(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminMarketplaceCampaigns";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/campaigns-load", method = RequestMethod.GET)
	public String loadCampaigns(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", campaignService.findAdmCampaignsList(filter, search, sort, rows, page, hasDesc));
		return "adminListCampaigns";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/campaigns/edit", method = RequestMethod.GET)
	public String showEditCampaign(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmCampaignForm admCampaignForm = campaignService.readAdmCampaignForm(id);
		if(admCampaignForm == null) {
			return "redirect:/admin/marketplace/campaigns?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("campaign", admCampaignForm);
		return "adminMarketplaceCampaign";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param admCampaignForm
	 * @return
	 */
	@RequestMapping(value = "/campaigns/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveCampaign(@AuthenticationPrincipal final LocalUser localUser, @Valid final AdmCampaignForm admCampaignForm) {
		final Campaign campaign = campaignService.updateCampaign(admCampaignForm);
		if(campaign == null) {
			throw new AccessAuthorityException();
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UPDATE_COMPAIGN, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/campaigns/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteCampaign(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		campaignService.deleteCampaign(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_COMPAIGN, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/campaigns/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteCampaigns(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		campaignService.deleteCampaigns(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_COMPAIGNS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/campaigns/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllCampaigns() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/audiances", method = RequestMethod.GET)
	public String showAudiances(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminMarketplaceAudiances";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param fl
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/audiances-load", method = RequestMethod.GET)
	public String loadAudiances(final Model model, @RequestParam("fl") final String fl, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final Boolean filter = !StringUtils.isEmpty(fl) ? fl.equals("true") : null;
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", campaignService.findAdmCampaignOrdersList(filter, search, sort, rows, page, hasDesc));
		return "adminListAudiances";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/audiances/edit", method = RequestMethod.GET)
	public String showEditAudiance(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") String config) {
		final AdmOrderCampagne admOrderCampagne = campaignService.readAdmOrderCampagne(id);
		if(admOrderCampagne == null) {
			return "redirect:/admin/marketplace/audiances?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("campaign", admOrderCampagne);
		model.addAttribute("order", new AdmOrderForm(id));
		return "adminMarketplaceAudiance";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param admOrderForm
	 * @return
	 */
	@RequestMapping(value = "/audiances/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAudiance(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@Valid final AdmOrderForm admOrderForm) {
		final Long userId = campaignService.validateOrder(localUser.getUserId(), admOrderForm);
		if(userId == null) {
			throw new AccessAuthorityException();
		}
		final NotificationType type = admOrderForm.isResponse() ? NotificationType.campaignAccepted : NotificationType.campaignRejected;
		eventPublisher.publishEvent(new OnNotificationEvent(null, userId, null, type, request));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), admOrderForm.isResponse() ? ConstraintesJournal.ADMIN_ACCEPT_COMPAIGN 
				: ConstraintesJournal.ADMIN_REJECT_COMPAIGN, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/audiances/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAudiance(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		campaignService.deleteOrder(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_ORDER, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/audiances/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAudiances(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		campaignService.deleteOrders(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_ORDERS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/audiances/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAudiances() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/ads", method = RequestMethod.GET)
	public String showAnnonces(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminMarketplaceAnnonces";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/ads-load", method = RequestMethod.GET)
	public String loadAnnonces(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", marketplaceService.findAdmAnnoncesList(filter, search, sort, rows, page, hasDesc));
		return "adminListAnnonces";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public String showEmployes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminMarketplaceEmployes";
	}
	
	/**
	 * AUTORITY SUPPORT_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param filter
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/jobs-load", method = RequestMethod.GET)
	public String loadEmployes(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", marketplaceService.findAdmEmployesList(filter, search, sort, rows, page, hasDesc));
		return "adminListEmployes";
	}
	
}
