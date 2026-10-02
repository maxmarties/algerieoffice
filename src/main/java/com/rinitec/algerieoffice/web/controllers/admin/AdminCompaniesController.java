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

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.companies.IAdmCompanyService;
import com.rinitec.algerieoffice.services.admins.feedback.IAdmFeedbackService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.admins.companies.FeatureForm;
import com.rinitec.algerieoffice.web.form.admins.premium.AdmBudgetForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.listener.events.OnLogoutEvents;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyDelete;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyInfo;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/companies")
public class AdminCompaniesController {

	private IAttributeService attributeService;
	private IAdmCompanyService companyService;
	private IAdmFeedbackService feedbackService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminCompaniesController(IAttributeService attributeService, IAdmCompanyService companyService, IAdmFeedbackService feedbackService, 
			ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.companyService = companyService;
		this.feedbackService = feedbackService;
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
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String showAll(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCompaniesAll";
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
	@RequestMapping(value = "/all-load", method = RequestMethod.GET)
	public String loadAll(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", companyService.findAdmCompanyList(filter, search, sort, rows, page, hasDesc));
		return "adminListCompanies";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param companyId
	 * @param hasLocked
	 * @return
	 */
	@RequestMapping(value = "/all/lock", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse lockReport(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long companyId, 
			@RequestParam(name = "locked") final boolean hasLocked) {
		final Company company = feedbackService.lockCompnyReport(companyId, hasLocked);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), hasLocked ? ConstraintesJournal.ADMIN_LOCK_COMPANY 
				: ConstraintesJournal.ADMIN_UNLOCK_COMPANY, company.getTradename()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/all/disable", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse disableReport(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long companyId) {
		final Company company = feedbackService.disableCompanyReport(companyId);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DISABLE_COMPANY, company.getTradename()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/all/deactivate", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse diactivateReport(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long companyId) {
		final Company company = feedbackService.diactivateCompanyReport(companyId);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_INVISIBLE_COMPANY, company.getTradename()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/all/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteCompany(@RequestParam(name = "id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		final AdmCompanyDelete delete = companyService.deleteCompany(id);
		eventPublisher.publishEvent(new OnLogoutEvents(delete.getUsers()));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_COMPANY, delete.getCompany().getTradename()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/all/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteCompanies(@RequestParam("lines[]") final List<Long> lines) {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/all/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllCompanies() {
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
	@RequestMapping(value = "/profiles", method = RequestMethod.GET)
	public String showProfiles(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCompaniesProfile";
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
	@RequestMapping(value = "/profiles-load", method = RequestMethod.GET)
	public String loadProfiles(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", companyService.findAdmCompanyProfileList(filter, search, sort, rows, page, hasDesc));
		return "adminListCompaniesProfile";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/profiles/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteProfile(@RequestParam(name = "id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		final AdmCompanyDelete delete = companyService.deleteCompany(id);
		eventPublisher.publishEvent(new OnLogoutEvents(delete.getUsers()));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_COMPANY, delete.getCompany().getTradename()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/profiles/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteProfiles(@RequestParam("lines[]") final List<Long> lines) {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/profiles/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllProfiles() {
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
	@RequestMapping(value = "/features", method = RequestMethod.GET)
	public String showFeatures(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCompaniesFeatures";
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
	@RequestMapping(value = "/features-load", method = RequestMethod.GET)
	public String loadFeatures(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", companyService.findAdmCompanyFeatureList(filter, search, sort, rows, page, hasDesc));
		return "adminListCompaniesFeature";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/features/edit", method = RequestMethod.GET)
	public String showEditFeature(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmCompanyInfo companyInfo = companyService.readAdmCompanyInfo(id);
		if(companyInfo == null) {
			return "redirect:/admin/companies/features?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("company", companyInfo);
		model.addAttribute("feature", new FeatureForm(id));
		model.addAttribute("premiums", companyService.findAllPremiumCompanyList(id));
		return "adminCompaniesFeature";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param featureForm
	 * @return
	 */
	@RequestMapping(value = "/features/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveFeature(@AuthenticationPrincipal final LocalUser localUser, @Valid final FeatureForm featureForm) {
		companyService.validateFeature(featureForm);
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/features/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteFeature(@RequestParam(name = "id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		final AdmCompanyDelete delete = companyService.deleteCompany(id);
		eventPublisher.publishEvent(new OnLogoutEvents(delete.getUsers()));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_COMPANY, delete.getCompany().getTradename()));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/features/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteFeatures(@RequestParam("lines[]") final List<Long> lines) {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/features/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllFeatures() {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/features/budget", method = RequestMethod.GET)
	public String showEditBudget(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmBudgetForm budget = companyService.readAdmBudgetForm(id);
		if(budget == null) {
			return "redirect:/admin/companies/features?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("budget", budget);
		return "adminCompaniesBudget";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param admBudgetForm
	 * @return
	 */
	@RequestMapping(value = "/features/update-budget", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveBudget(@AuthenticationPrincipal final LocalUser localUser, @Valid final AdmBudgetForm admBudgetForm) {
		final String tradename = companyService.updateBudget(admBudgetForm);
		if(tradename == null) {
			throw new AccessAuthorityException();
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UPDATE_BUDGET, tradename));
		return new GenericResponse("success");
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
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public String showCustomers(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminCompaniesCustomers";
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
	@RequestMapping(value = "/customers-load", method = RequestMethod.GET)
	public String loadCustomers(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", companyService.findAdmCompanyCustomerList(filter, search, sort, rows, page, hasDesc));
		return "adminListCompaniesCustomer";
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/customers/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteCustomer(@RequestParam(name = "id") final Long id, @AuthenticationPrincipal final LocalUser localUser) {
		final String tradename = companyService.deleteWidgetB2C(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_B2C, tradename));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/customers/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteCustomers(@RequestParam("lines[]") final List<Long> lines) {
		throw new AccessAuthorityException();
	}
	
	/**
	 * AUTORITY SUPPORT_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/customers/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllCustomers() {
		throw new AccessAuthorityException();
	}
	
}
