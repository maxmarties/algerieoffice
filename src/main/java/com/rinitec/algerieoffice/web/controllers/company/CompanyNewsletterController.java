package com.rinitec.algerieoffice.web.controllers.company;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.mail.IEmailService;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.company.newsletter.INewsletterService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.company.newsletter.BudgetForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.EmailingForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.NewsletterForm;
import com.rinitec.algerieoffice.web.form.company.newsletter.TemplateForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnSupportEvent;
import com.rinitec.algerieoffice.web.modal.CurrentCompany;

@Controller
@RequestMapping(value = "/company/newsletter")
public class CompanyNewsletterController {

	private IAttributeService attributeService;
	private INewsletterService newsletterService;
	private IEmailService emailService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyNewsletterController(IAttributeService attributeService, INewsletterService newsletterService, IEmailService emailService, 
			ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.newsletterService = newsletterService;
		this.emailService = emailService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/template", method = RequestMethod.GET)
	public String showTemplate(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("currYear", ParseUtil.getCurrYear());
		model.addAttribute("template", newsletterService.readTemplateForm(localUser.getCompanyId()));
		model.addAttribute("socials", newsletterService.readNewsletterSocial(localUser.getCompanyId()));
		return "companyNewsletterTemplate";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param localUser
	 * @param templateForm
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/template/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateTemplate(@AuthenticationPrincipal final LocalUser localUser, @Valid final TemplateForm templateForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		if(!templateForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		templateForm.setFile(file);
		newsletterService.updateMaintemplate(templateForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), ConstraintesJournal.COMPANY_UPDATE_NEWSLETTER, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/budget", method = RequestMethod.GET)
	public String showBudget(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		final boolean checkOrder = newsletterService.checkOrder(localUser.getUserId());
		if(checkOrder) {
			model.addAttribute("checkOrder", checkOrder);
		} else {
			model.addAttribute("order", new BudgetForm(localUser.getCompanyId()));
		}
		model.addAttribute("currBudget", newsletterService.readNewsletterBudget(localUser.getCompanyId()));
		return "companyNewsletterBudget";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param budgetForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/budget/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateBudget(final HttpServletRequest request, @Valid final BudgetForm budgetForm, 
			@RequestParam(name = "file", required = true) final MultipartFile file, @AuthenticationPrincipal final LocalUser localUser) {
		if(!budgetForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		budgetForm.setFile(file);
		final DocumentOrder documentOrder = newsletterService.addDocumentOrder(localUser.getUserId(), budgetForm);
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), null, documentOrder.getId(), NotificationType.emailing, request));
		eventPublisher.publishEvent(new OnSupportEvent(localUser.getUserId(), localUser.getUser().getEmail(), ConstraintesJournal.SUPPORT_EMAILING, request));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/emailing", method = RequestMethod.GET)
	public String showEmailing(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentCompany currentCompany = attributeService.attributeCompany(model, config, localUser);
		if(currentCompany.isEnabled()) {
			final Long companyId = localUser.getCompanyId();
			model.addAttribute("currYear", ParseUtil.getCurrYear());
			model.addAttribute("info", request.getParameter("info"));
			model.addAttribute("emailing", new EmailingForm(companyId));
			model.addAttribute("template", newsletterService.readNewsletterTemplate(companyId));
			model.addAttribute("budget", newsletterService.readNewsletterBudget(companyId));
			model.addAttribute("easylists", newsletterService.findAllEasylistCompany(localUser.getUserId()));
			model.addAttribute("volume", ConstraintesForm.LIMIT_EMAILING);
		}
		return "companyNewsletterEmailing";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param type
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/emailing/load", method = RequestMethod.GET)
	public String loadEmailingArticles(final Model model, @RequestParam("type") final Integer type, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("currType", type != null && type >= 1 && type <= 5 ? type : 1);
		model.addAttribute("choseArticles", newsletterService.findAllNewsletterArticles(localUser.getCompanyId(), type));
		return "companyEmailingArticles";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/emailing/users", method = RequestMethod.GET)
	public String loadEmailingUsers(final Model model, @AuthenticationPrincipal final LocalUser localUser) {
		model.addAttribute("choseUsers", newsletterService.findAllNewsletterUserCompany(localUser.getCompanyId()));
		return "companyEmailingUsers";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param sect
	 * @param ville
	 * @return
	 */
	@RequestMapping(value = "/emailing/companies", method = RequestMethod.GET)
	public String loadEmailingCompanies(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("sector") final Integer sect, @RequestParam("wilaya") final Integer ville) {
		final Integer sector = sect == null || sect == 32 ? null : sect;
		final Integer wilaya = ville == null || ville == 49 ? null : ville;
		model.addAttribute("choseCompanies", newsletterService.findAllNewsletterCompanies(localUser.getCompanyId(), sector, wilaya));
		return "companyEmailingCompanies";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param easyid
	 * @return
	 */
	@RequestMapping(value = "/emailing/easylist", method = RequestMethod.GET)
	public String loadEmailingEasylist(final Model model, @AuthenticationPrincipal final LocalUser localUser, @RequestParam("easyid") final String easyid) {
		model.addAttribute("choseCompanies", newsletterService.findAllNewsletterEasylist(easyid, localUser.getUserId()));
		return "companyEmailingEasylist";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param type
	 * @param lines
	 * @return
	 */
	@RequestMapping(value = "/emailing/items", method = RequestMethod.GET)
	public String loadEmailingItems(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("type") final Integer type, @RequestParam("lines[]") final List<String> lines) {
		model.addAttribute("emailItems", newsletterService.findAllNewsletterItem(localUser.getCompanyId(), type, lines));
		return "companyEmailingItems";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param emailingForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/emailing/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateEmailing(final HttpServletRequest request, @Valid final EmailingForm emailingForm, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!emailingForm.getCompanyId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		final NewsletterForm newsletterForm = newsletterService.readNewsletterForm(emailingForm, localUser.getUser().getEmail());
		if(emailService.sendNewsletterCompany(request, newsletterForm)) {
			newsletterService.updateConsumeBudget(localUser.getCompanyId(), newsletterForm.getEmails().size());
			eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), ConstraintesJournal.COMPANY_SEND_NEWSLETTER,
					newsletterForm.getSubject()));
		} else {
			throw new AccessUploadException("auth.message.emailing");
		}
		return new GenericResponse("success");
	}
	
}
