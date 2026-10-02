package com.rinitec.algerieoffice.web.controllers.admin;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.mail.IEmailService;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.ads.IAdmNewsletterService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.admins.ads.AdmMailingForm;
import com.rinitec.algerieoffice.web.form.admins.ads.AdmMarketplaceForm;
import com.rinitec.algerieoffice.web.form.admins.ads.AdmNewsletterForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalAdminEvent;
import com.rinitec.algerieoffice.web.modal.admins.ads.AnnonceNewsletterMini;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogNewsletterMini;

@Controller
@RequestMapping(value = "/admin/mailing")
public class AdminMailingController {

	private IAttributeService attributeService;
	private IAdmNewsletterService newsletterService;
	private IEmailService emailService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public AdminMailingController(IAttributeService attributeService, IAdmNewsletterService newsletterService, IEmailService emailService, 
			ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.newsletterService = newsletterService;
		this.emailService = emailService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/newsletter", method = RequestMethod.GET)
	public String showNewsletter(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("newsletter", new AdmNewsletterForm(newsletterService.findAllNewsletter(ConstraintesForm.LIMIT_NEWSLETTER), newsletterService.findAllBolgNewsletter(10)));
		return "adminMailingNewsletter";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param newsletterForm
	 * @return
	 */
	@RequestMapping(value = "/newsletter/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse sendNewsletter(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@Valid final AdmNewsletterForm newsletterForm) {
		final List<BlogNewsletterMini> lines = newsletterService.findAllBlogNewsletterMini(newsletterForm.getNews());
		emailService.sendNewsletterBlog(request, newsletterForm.getUsers(), lines);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_NEWSLETTER_BLOG, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/marketplace", method = RequestMethod.GET)
	public String showMarketplace(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("marketplace", newsletterService.readMarketplaceForm(ConstraintesForm.LIMIT_NEWSLETTER, 100));
		return "adminMailingMarketplace";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param marketplaceForm
	 * @return
	 */
	@RequestMapping(value = "/marketplace/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse sendMarketplace(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@Valid final AdmMarketplaceForm marketplaceForm) {
		final List<AnnonceNewsletterMini> lines = newsletterService.findAllAnnonceNewsletterMini(marketplaceForm.getNews());
		emailService.sendNewsletterAnnonces(request, marketplaceForm.getUsers(), lines, marketplaceForm.getTodays(), marketplaceForm.getOffers());
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_NEWSLETTER_ADS, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/actualities", method = RequestMethod.GET)
	public String showActualities(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeUser(model, config, localUser);
		model.addAttribute("mailing", new AdmMailingForm(newsletterService.findAllNewsletter(ConstraintesForm.LIMIT_NEWSLETTER)));
		return "adminMailingActualities";
	}
	
	/**
	 * AUTORITY SUPPORT_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param localUser
	 * @param mailingForm
	 * @return
	 */
	@RequestMapping(value = "/actualities/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse sendActualities(final HttpServletRequest request, @AuthenticationPrincipal final LocalUser localUser, 
			@Valid final AdmMailingForm mailingForm) {
		emailService.sendNewsletterMailing(request, mailingForm);
		eventPublisher.publishEvent(new OnJournalAdminEvent(localUser.getUserId(), ConstraintesJournal.ADMIN_NEWSLETTER_ACTUS, null));
		return new GenericResponse("success");
	}
	
}
