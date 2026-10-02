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

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.enums.OrderType;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IAdmPremiumService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumFormuleService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.admins.marketplace.AdmOrderForm;
import com.rinitec.algerieoffice.web.form.admins.premium.AdmBudgetForm;
import com.rinitec.algerieoffice.web.form.admins.premium.PremiumForm;
import com.rinitec.algerieoffice.web.form.admins.premium.PricePremiumForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.modal.admins.premium.AdmPremiumOrderLine;
import com.rinitec.algerieoffice.web.modal.user.CurrentTable;

@Controller
@RequestMapping(value = "/admin/premium")
public class AdminPremiumController {

	private IAttributeService attributeService;
	private IAdmPremiumService premiumService;
	private IPremiumFormuleService premiumFormuleService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminPremiumController(IAttributeService attributeService, IAdmPremiumService premiumService, IPremiumFormuleService premiumFormuleService, 
			ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.premiumService = premiumService;
		this.premiumFormuleService = premiumFormuleService;
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
	@RequestMapping(value = "/subscribes", method = RequestMethod.GET)
	public String showSubscribes(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminPremiumSubscribes";
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
	@RequestMapping(value = "/subscribes-load", method = RequestMethod.GET)
	public String loadSubscribes(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", premiumService.findAdmPremiumList(filter, search, sort, rows, page, hasDesc));
		return "adminListPremiumSubscribes";
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
	@RequestMapping(value = "/subscribes/edit", method = RequestMethod.GET)
	public String showEditSubscribe(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final PremiumForm premiumForm = premiumService.readPremiumForm(id);
		if(premiumForm == null) {
			return "redirect:/admin/premium/subscribes?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("subscribe", premiumForm);
		return "adminPremiumSubscribe";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param premiumForm
	 * @return
	 */
	@RequestMapping(value = "/subscribes/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveSubscribe(@AuthenticationPrincipal final LocalUser localUser, @Valid final PremiumForm premiumForm) {
		final Premium premium = premiumService.updatePremium(premiumForm);
		if(premium == null) {
			throw new AccessAuthorityException();
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UPDATE_PREMIUM, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/subscribes/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteSubscribe(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		premiumService.deletePremium(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_PREMIUM, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/subscribes/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteSubscribes(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		premiumService.deletePremiums(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_PREMIUMS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/subscribes/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllSubscribes() {
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
		return "adminPremiumOrders";
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
	@RequestMapping(value = "/orders-load", method = RequestMethod.GET)
	public String loadOrders(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", premiumService.findAdmPremiumOrderList(filter, search, sort, rows, page, hasDesc, OrderType.premium));
		return "adminListPremiumOrders";
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
		final AdmPremiumOrderLine orderLine = premiumService.readAdmPremiumOrderLine(id, OrderType.premium);
		if(orderLine == null) {
			return "redirect:/admin/premium/orders?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("order", orderLine);
		model.addAttribute("premiums", premiumService.findAdmPremiumCompanyList(orderLine.getCompanyId()));
		model.addAttribute("subscribe", new PremiumForm(orderLine.getCompanyId(), orderLine.getId()));
		return "adminPremiumOrder";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param premiumForm
	 * @return
	 */
	@RequestMapping(value = "/orders/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveOrder(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@Valid final PremiumForm premiumForm) {
		final Long userId = premiumService.validateOrder(premiumForm);
		if(userId == null) {
			throw new AccessAuthorityException();
		}
		final NotificationType type = premiumForm.isEnabled() ? NotificationType.premiumAccepted : NotificationType.premiumRejected;
		eventPublisher.publishEvent(new OnNotificationEvent(null, userId, null, type, request));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), premiumForm.isEnabled() ? ConstraintesJournal.ADMIN_ACCEPT_PREMIUM 
				: ConstraintesJournal.ADMIN_REJECT_PREMIUM, null));
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
		premiumService.deleteOrder(id, OrderType.premium);
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
		premiumService.deleteOrders(lines, OrderType.premium);
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
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/formule", method = RequestMethod.GET)
	public String showSocial(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("formule", premiumFormuleService.readPricePremiumForm());
		return "adminPremiumFormule";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param pricePremiumForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/formule/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateSocial(@Valid final PricePremiumForm pricePremiumForm, @AuthenticationPrincipal final LocalUser localUser) {
		premiumFormuleService.updatePricePremium(pricePremiumForm);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UPDATE_FORMULE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @param table
	 * @return
	 */
	@RequestMapping(value = "/emailings", method = RequestMethod.GET)
	public String showEmailings(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminPremiumEmailings";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
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
	@RequestMapping(value = "/emailings-load", method = RequestMethod.GET)
	public String loadEmailings(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", premiumService.findAdmPremiumOrderList(filter, search, sort, rows, page, hasDesc, OrderType.emailing));
		return "adminListPremiumEmailings";
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
	@RequestMapping(value = "/emailings/edit", method = RequestMethod.GET)
	public String showEditEmailing(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmPremiumOrderLine orderLine = premiumService.readAdmPremiumOrderLine(id, OrderType.emailing);
		if(orderLine == null) {
			return "redirect:/admin/premium/emailings?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("order", orderLine);
		model.addAttribute("subscribe", new AdmOrderForm(id, orderLine.getCompanyId()));
		model.addAttribute("currBudget", premiumService.readNewsletterBudget(orderLine.getCompanyId()));
		return "adminPremiumEmailing";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param admOrderForm
	 * @return
	 */
	@RequestMapping(value = "/emailings/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveEmailing(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@Valid final AdmOrderForm admOrderForm) {
		final Object[] result = premiumService.validateEmailing(admOrderForm);
		if(result == null) {
			throw new AccessAuthorityException();
		}
		final NotificationType type = admOrderForm.isResponse() ? NotificationType.emailingAccepted : NotificationType.emailingRejected;
		eventPublisher.publishEvent(new OnNotificationEvent(null, (Long) result[0], null, type, request));
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), admOrderForm.isResponse() ? ConstraintesJournal.ADMIN_ACCEPT_EMAILING 
				: ConstraintesJournal.ADMIN_REJECT_EMAILING, (String) result[1]));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emailings/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteEmailing(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final String id) {
		premiumService.deleteOrder(id, OrderType.emailing);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_EMAILING, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/emailings/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteEmailings(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<String> lines) {
		premiumService.deleteOrders(lines, OrderType.emailing);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_EMAILINGS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/emailings/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllEmailings() {
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
	@RequestMapping(value = "/budgets", method = RequestMethod.GET)
	public String showBudgets(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_TABLE_ADMIN, defaultValue = "") final String table) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("desc", true);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("notFound", request.getParameter("notFound"));
		model.addAttribute("currTable", new CurrentTable(table, RequestUtil.COOKIE_TABLE_ADMIN));
		return "adminPremiumBudgets";
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
	@RequestMapping(value = "/budgets-load", method = RequestMethod.GET)
	public String loadBudgets(final Model model, @RequestParam("fl") final Integer filter, @RequestParam("ch") final String ch, 
			@RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, 
			@RequestParam("dsc") final String desc) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		model.addAttribute("list", premiumService.findAdmBudgetList(search, sort, rows, page, hasDesc));
		return "adminListPremiumBudgets";
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
	@RequestMapping(value = "/budgets/edit", method = RequestMethod.GET)
	public String showEditBudget(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final AdmBudgetForm budgetForm = premiumService.readAdmBudgetForm(id);
		if(budgetForm == null) {
			return "redirect:/admin/premium/budgets?notFound=true";
		}
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("budget", budgetForm);
		return "adminPremiumBudget";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param admBudgetForm
	 * @return
	 */
	@RequestMapping(value = "/budgets/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveBudget(@AuthenticationPrincipal final LocalUser localUser, @Valid final AdmBudgetForm admBudgetForm) {
		final String tradename = premiumService.updateBudget(admBudgetForm);
		if(tradename == null) {
			throw new AccessAuthorityException();
		}
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_UPDATE_BUDGET, tradename));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/budgets/delete", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse deleteBudget(@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "id") final Long id) {
		premiumService.deleteBudget(id);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_BUDGET, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/budgets/delete-lines", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteBudgets(@AuthenticationPrincipal final LocalUser localUser, @RequestParam("lines[]") final List<Long> lines) {
		premiumService.deleteBudgets(lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_DELETE_BUDGETS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	@RequestMapping(value = "/budgets/delete-all", method = RequestMethod.POST)
    @ResponseBody
	public GenericResponse deleteAllBudgets() {
		throw new AccessAuthorityException();
	}
	
}
