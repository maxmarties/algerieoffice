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

import com.rinitec.algerieoffice.enums.LiveType;
import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.company.marketplace.IAnnonceService;
import com.rinitec.algerieoffice.services.company.marketplace.ICampaignService;
import com.rinitec.algerieoffice.services.company.marketplace.IEmployeService;
import com.rinitec.algerieoffice.services.company.marketplace.IPromoteService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.MaxPlanException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.marketplace.AnnonceForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.CampaignForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.EmployeForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.OrderForm;
import com.rinitec.algerieoffice.web.form.company.marketplace.PromoteForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnLiveEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnSupportEvent;
import com.rinitec.algerieoffice.web.modal.CurrentCompany;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/company/marketplace")
public class CompanyMarketplaceController {

	private IAttributeService attributeService;
	private IPremiumService premiumService;
	private IPromoteService promoteService;
	private IAnnonceService annonceService;
	private IEmployeService employeService;
	private ICampaignService campaignService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyMarketplaceController(IAttributeService attributeService, IPremiumService premiumService, IPromoteService promoteService, 
			IAnnonceService annonceService, IEmployeService employeService, ICampaignService campaignService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.premiumService = premiumService;
		this.promoteService = promoteService;
		this.annonceService = annonceService;
		this.employeService = employeService;
		this.campaignService = campaignService;
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
	@RequestMapping(value = "/promotes", method = RequestMethod.GET)
	public String showPromotes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_MARKETPLACE1, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_MARKETPLACE1));
		model.addAttribute("autors", promoteService.findAllAutorPromote(localUser.getCompanyId()));
		model.addAttribute("countTrashed", promoteService.countTrashedPromote(localUser.getCompanyId()));
		return "companyMarketplacePromotes";
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
	@RequestMapping(value = "/promotes-load", method = RequestMethod.GET)
	public String loadPromotes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Long filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_MARKETPLACE1, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", promoteService.findPromotesList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListPromotes";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/promotes/new", method = RequestMethod.GET)
	public String showNewPromote(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("promote", new PromoteForm(localUser.getCompanyId()));
		return "companyMarketplacePromote";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/promotes/edit", method = RequestMethod.GET)
	public String showEditPromote(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam(name = "id", required = false) final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/marketplace/promotes";
		}
		final PromoteForm promoteForm = promoteService.readPromoteForm(id, localUser.getCompanyId());
		if(promoteForm == null) {
			return "redirect:/company/marketplace/promotes?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("promote", promoteForm);
		return "companyMarketplacePromote";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param promoteForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/promotes/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse savePromote(final HttpServletRequest request, @Valid final PromoteForm promoteForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!promoteForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		Promote promote = null; 
		promoteForm.setFile(file);
		if(StringUtils.isEmpty(promoteForm.getId())) {
			promote = promoteService.addPromote(promoteForm, localUser.getUserId());
		} else {
			promote = promoteService.updatePromote(promoteForm, localUser.getUserId());
			if(promote == null) {
				throw new AccessAuthorityException();
			}
			if(promote.getCreditCount() > 0 && !promote.isEnabled()) {
				eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), null, promote.getId(), NotificationType.promoteUpdated, request));
			}
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(promoteForm.getId()) ? ConstraintesJournal.COMPANY_ADD_PROMOTE : ConstraintesJournal.COMPANY_UPDATE_PROMOTE, promote.getTitle()));
		return new GenericResponse("success", promote.getId().toString());
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/promotes/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deletePromote(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Promote promote = promoteService.trashPromote(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_PROMOTE, promote.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/promotes/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deletePromotes(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		promoteService.trashPromotes(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_LINES_PROMOTE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/promotes/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllPromotes(@AuthenticationPrincipal final LocalUser localUser) {
		promoteService.trashAllPromotes(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_ALL_PROMOTE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/promotes/order", method = RequestMethod.GET)
	public String showPromoteOrder(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam(name = "id", required = false) final String id, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/marketplace/promotes";
		}
		final OrderForm orderForm = promoteService.readOrderForm(id, localUser.getCompanyId());
		if(orderForm == null) {
			return "redirect:/company/marketplace/promotes?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("order", orderForm);
		model.addAttribute("checkOrder", promoteService.checkOrder(localUser.getUserId(), OrderType.promote));
		return "companyMarketplaceOrder";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param orderForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/promotes/order/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse savePromoteOrder(final HttpServletRequest request, @Valid final OrderForm orderForm, 
			@RequestParam(name = "file", required = true) final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		if(!orderForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		orderForm.setFile(file);
		final DocumentOrder documentOrder = promoteService.updateDocumentOrder(orderForm, localUser.getUserId());
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), null, documentOrder.getId(), NotificationType.order, request));
		eventPublisher.publishEvent(new OnSupportEvent(localUser.getUserId(), localUser.getUser().getEmail(), ConstraintesJournal.SUPPORT_PROMOTE, request));
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
	@RequestMapping(value = "/ads", method = RequestMethod.GET)
	public String showAnnonces(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config,
			@CookieValue(value = RequestUtil.COOKIE_TABLE_MARKETPLACE2, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_MARKETPLACE2));
		model.addAttribute("countTrashed", annonceService.countTrashedAnnonce(localUser.getCompanyId()));
		return "companyMarketplaceAnnonces";
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
	@RequestMapping(value = "/ads-load", method = RequestMethod.GET)
	public String loadAnnonces(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_MARKETPLACE2, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", annonceService.findAnnoncesList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListAnnonces";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/ads/new", method = RequestMethod.GET)
	public String showNewAnnonce(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final CurrentCompany currentCompany = attributeService.attributeCompany(model, config, localUser);
		if(currentCompany.isEnabled()) {
			model.addAttribute("maxkeysword", premiumService.getMaxKeysword(companyId));
			model.addAttribute("urlMarket", ConstraintesURL.URL_MARKETPLACE.concat("/"));
		}
		model.addAttribute("annonce", new AnnonceForm(companyId));
		return "companyMarketplaceAnnonce";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/ads/edit", method = RequestMethod.GET)
	public String showEditAnnonce(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/marketplace/ads";
		}
		final AnnonceForm annonceForm = annonceService.readAnnonceForm(id, localUser.getCompanyId());
		if(annonceForm == null) {
			return "redirect:/company/marketplace/ads?notFound=true";
		}
		final Long companyId = localUser.getCompanyId();
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("annonce", annonceForm);
		model.addAttribute("urlMarket", ConstraintesURL.URL_MARKETPLACE.concat("/"));
		model.addAttribute("maxkeysword", premiumService.getMaxKeysword(companyId));
		return "companyMarketplaceAnnonce";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * @param request
	 * @param annonceForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveAnnonce(final HttpServletRequest request, @Valid final AnnonceForm annonceForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!annonceForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final int maxKeysword = premiumService.getMaxKeysword(annonceForm.getCompanyId());
		annonceForm.setFile(file);
		if(StringUtils.isEmpty(annonceForm.getId())) {
			if(annonceService.hasMaxAnnonce(annonceForm.getCompanyId())) {
				throw new MaxPlanException("message.plan.data");
			}
			final Annonce annonce = annonceService.addAnnonce(annonceForm, maxKeysword, localUser.getUserId());
			if(annonce.getHasPublished()) {
				eventPublisher.publishEvent(new OnLiveEvent(annonce.getCompanyId(), annonce.getIdentify(), LiveType.addAnnonce, request));
			}
		} else {
			final Annonce annonce = annonceService.updateAnnonce(annonceForm, maxKeysword, localUser.getUserId());
			if(annonce == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(annonceForm.getId()) ? ConstraintesJournal.COMPANY_ADD_ADS : ConstraintesJournal.COMPANY_UPDATE_ADS, annonceForm.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/publish", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse publishAnnonce(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Annonce annonce = annonceService.publishAnnonce(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				annonce.getHasPublished() ? ConstraintesJournal.COMPANY_PUBLISH_ADS : ConstraintesJournal.COMPANY_DEPUBLISH_ADS, annonce.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteAnnonce(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Annonce annonce = annonceService.trashAnnonce(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_ADS, annonce.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAnnonces(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		annonceService.trashAnnonces(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_LINES_ADS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/ads/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllAnnonces(@AuthenticationPrincipal final LocalUser localUser) {
		annonceService.trashAllAnnonces(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_ALL_ADS, null));
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
	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public String showEmployes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_MARKETPLACE3, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_MARKETPLACE3));
		model.addAttribute("countTrashed", employeService.countTrashedEmploye(localUser.getCompanyId()));
		return "companyMarketplaceEmployes";
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
	@RequestMapping(value = "/jobs-load", method = RequestMethod.GET)
	public String loadEmployes(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_MARKETPLACE3, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", employeService.findEmployesList(localUser.getCompanyId(), filter, search, sort, rows, page, hasDesc));
		return "companyListEmployes";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/jobs/new", method = RequestMethod.GET)
	public String showNewEmploye(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final CurrentCompany currentCompany = attributeService.attributeCompany(model, config, localUser);
		if(currentCompany.isEnabled()) {
			model.addAttribute("maxkeysword", premiumService.getMaxKeysword(companyId));
			model.addAttribute("urlEmploye", ConstraintesURL.URL_EMPLOYE.concat("/"));
		}
		model.addAttribute("employe", new EmployeForm(companyId));
		return "companyMarketplaceEmploye";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/jobs/edit", method = RequestMethod.GET)
	public String showEditEmploye(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/marketplace/jobs";
		}
		final EmployeForm employeForm = employeService.readEmployeForm(id, localUser.getCompanyId());
		if(employeForm == null) {
			return "redirect:/company/marketplace/jobs?notFound=true";
		}
		final Long companyId = localUser.getCompanyId();
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("employe", employeForm);
		model.addAttribute("urlEmploye", ConstraintesURL.URL_EMPLOYE.concat("/"));
		model.addAttribute("maxkeysword", premiumService.getMaxKeysword(companyId));
		return "companyMarketplaceEmploye";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * @param request
	 * @param employeForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveEmploye(final HttpServletRequest request, @Valid final EmployeForm employeForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!employeForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final int maxKeysword = premiumService.getMaxKeysword(employeForm.getCompanyId());
		if(StringUtils.isEmpty(employeForm.getId())) {
			if(employeService.hasMaxEmploye(employeForm.getCompanyId())) {
				throw new MaxPlanException("message.plan.data");
			}
			final Employe employe = employeService.addEmploye(employeForm, maxKeysword, localUser.getUserId());
			if(employe.getHasPublished()) {
				eventPublisher.publishEvent(new OnLiveEvent(employe.getCompanyId(), employe.getIdentify(), LiveType.addEmploye, request));
			}
		} else {
			final Employe employe = employeService.updateEmploye(employeForm, maxKeysword, localUser.getUserId());
			if(employe == null) {
				throw new AccessAuthorityException();
			}
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(employeForm.getId()) ? ConstraintesJournal.COMPANY_ADD_EMPLOYE : ConstraintesJournal.COMPANY_UPDATE_EMPLOYE, employeForm.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/publish", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse publishEmploye(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Employe employe = employeService.publishEmploye(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				employe.getHasPublished() ? ConstraintesJournal.COMPANY_PUBLISH_EMPLOYE : ConstraintesJournal.COMPANY_DEPUBLISH_EMPLOYE, employe.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEmploye(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final Employe employe = employeService.trashEmploye(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_EMPLOYE, employe.getTitle()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEmployes(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		employeService.trashEmployes(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_LINES_EMPLOYE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/jobs/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEmployes(@AuthenticationPrincipal final LocalUser localUser) {
		employeService.trashAllEmployes(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_TRASH_ALL_EMPLOYE, null));
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
	@RequestMapping(value = "/campaigns", method = RequestMethod.GET)
	public String showCampaigns(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_MARKETPLACE4, defaultValue = "") final String table) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_MARKETPLACE4));
		return "companyMarketplaceCampaigns";
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
	@RequestMapping(value = "/campaigns-load", method = RequestMethod.GET)
	public String loadCampaigns(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_MARKETPLACE4, defaultValue = "") final String table) {
		final boolean hasDesc = desc.equals("true");
		model.addAttribute("currTable", new CurrentTable(table));
		model.addAttribute("list", campaignService.findPromotesList(localUser.getCompanyId(), filter, sort, rows, page, hasDesc));
		return "companyListCampaigns";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/campaigns/new", method = RequestMethod.GET)
	public String showNewCampaign(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("campaign", new CampaignForm(companyId));
		return "companyMarketplaceCampaign";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/campaigns/edit", method = RequestMethod.GET)
	public String showEditCampaign(final Model model, @RequestParam(name = "id", required = false) final String id,
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/marketplace/campaigns";
		}
		final CampaignForm campaignForm = campaignService.readCampaignForm(id, localUser.getCompanyId());
		if(campaignForm == null) {
			return "redirect:/company/marketplace/campaigns?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("campaign", campaignForm);
		return "companyMarketplaceCampaign";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param campaignForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/campaigns/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveCampaign(final HttpServletRequest request, @Valid final CampaignForm campaignForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!campaignForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		String title = null;
		Campaign campaign = null;
		if(StringUtils.isEmpty(campaignForm.getId())) {
			campaign = campaignService.addCampaign(campaignForm, localUser.getUserId());
			title = campaignService.readCampaignTitle(campaign);
		} else {
			campaign = campaignService.updateCampaign(campaignForm, localUser.getUserId());
			if(campaign == null) {
				throw new AccessAuthorityException();
			}
			title = campaignForm.getDocumentTitle();
		}
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				StringUtils.isEmpty(campaignForm.getId()) ? ConstraintesJournal.COMPANY_ADD_PROMOTE : ConstraintesJournal.COMPANY_UPDATE_PROMOTE, title));
		return new GenericResponse("success", campaign.getId().toString());
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/campaigns/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteCampaign(@RequestParam("id") final String id, @AuthenticationPrincipal final LocalUser localUser) {
		final String title = campaignService.deleteCampaign(id, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_CAMPAIGN, title));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/campaigns/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteCampaigns(@RequestParam("lines[]") final List<String> lines, @AuthenticationPrincipal final LocalUser localUser) {
		campaignService.deleteCampaigns(lines, localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_LINES_CAMPAIGN, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/campaigns/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllCampaigns(@AuthenticationPrincipal final LocalUser localUser) {
		campaignService.deleteAllCampaigns(localUser.getCompanyId());
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_DELETE_ALL_CAMPAIGN, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/campaigns/load/posts", method = RequestMethod.GET)
	public String loadPostsCampaigns(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("choseCampaigns", campaignService.findAllPostCampaignMini(localUser.getCompanyId()));
		return "companyListChoserCampaigns";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/campaigns/load/ads", method = RequestMethod.GET)
	public String loadAnnoncesCampaigns(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("choseCampaigns", campaignService.findAllAnnonceCampaignMini(localUser.getCompanyId()));
		return "companyListChoserCampaigns";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/campaigns/load/events", method = RequestMethod.GET)
	public String loadEventsCampaigns(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("choseCampaigns", campaignService.findAllEventCampaignMini(localUser.getCompanyId()));
		return "companyListChoserCampaigns";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param id
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/campaigns/order", method = RequestMethod.GET)
	public String showCampaignOrder(final Model model, @RequestParam(name = "id", required = false) final String id, 
			@AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(id)) {
			return "redirect:/company/marketplace/campaigns";
		}
		final OrderForm orderForm = campaignService.readOrderForm(id, localUser.getCompanyId());
		if(orderForm == null) {
			return "redirect:/company/marketplace/campaigns?notFound=true";
		}
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("order", orderForm);
		model.addAttribute("checkOrder", promoteService.checkOrder(localUser.getUserId(), OrderType.campaign));
		return "companyMarketplaceOrders";
	}
	
	/**
	 * AUTORITY COMPANY_AUTOR_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param orderForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/campaigns/order/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveCampaignOrder(final HttpServletRequest request, @Valid final OrderForm orderForm, 
			@RequestParam(name = "file", required = true) final MultipartFile file, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!orderForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		orderForm.setFile(file);
		final DocumentOrder documentOrder = campaignService.updateDocumentOrder(orderForm, localUser.getUserId());
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), null, documentOrder.getId(), NotificationType.campaign, request));
		eventPublisher.publishEvent(new OnSupportEvent(localUser.getUserId(), localUser.getUser().getEmail(), ConstraintesJournal.SUPPORT_CAMPAIGN, request));
		return new GenericResponse("success");
	}
	
}
