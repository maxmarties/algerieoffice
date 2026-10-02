package com.rinitec.algerieoffice.web.controllers.company;

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
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.data.IActivityService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.company.profile.IIdentityService;
import com.rinitec.algerieoffice.services.company.profile.ISuitcaseService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.company.profile.BriefcaseForm;
import com.rinitec.algerieoffice.web.form.company.profile.CreditForm;
import com.rinitec.algerieoffice.web.form.company.profile.IdentityForm;
import com.rinitec.algerieoffice.web.form.company.profile.LinkedForm;
import com.rinitec.algerieoffice.web.form.company.profile.LocationForm;
import com.rinitec.algerieoffice.web.form.company.profile.SheduleForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnSupportEvent;
import com.rinitec.algerieoffice.web.modal.CurrentCompany;
import com.rinitec.algerieoffice.web.modal.company.profile.IdentityState;

@Controller
@RequestMapping(value = "/company/profile")
public class CompanyProfileController {

	private IAttributeService attributeService;
	private IPremiumService premiumService;
	private IIdentityService identityService;
	private ISuitcaseService suitcaseService;
	private IActivityService activityService;
	private ApplicationEventPublisher eventPublisher;
		
	@Autowired
	public CompanyProfileController(IAttributeService attributeService, IPremiumService premiumService, IIdentityService identityService,
			ISuitcaseService suitcaseService, IActivityService activityService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.premiumService = premiumService;
		this.identityService = identityService;
		this.suitcaseService = suitcaseService;
		this.activityService = activityService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/identity", method = RequestMethod.GET)
	public String showIdentity(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getUser().getCompanyId();
		final CurrentCompany currentCompany = attributeService.attributeCompany(model, config, localUser);
		IdentityForm identityForm = null;
		if(!currentCompany.isEnabled()) {
			final IdentityState identityState = identityService.readIdentityState(companyId);
			if(!identityState.isExists() || identityState.isConsulted()) {
				identityForm = identityService.readIdentityForm(companyId);
			}
			model.addAttribute("identityState", identityState);
		} else {
			identityForm = identityService.readIdentityForm(companyId);
		}
		if(identityForm != null) {
			model.addAttribute("identity", identityForm);
			model.addAttribute("choseActivities", activityService.findAllChoseActivity());
			model.addAttribute("urlCompanies", ConstraintesURL.URL_COMPANIES.concat("/"));
		}
		return "companyProfileIdentity";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param identityForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/identity/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateIdentity(final HttpServletRequest request, @Valid final IdentityForm identityForm, 
			@RequestParam(name = "file", required = true) MultipartFile file, 
			@AuthenticationPrincipal final LocalUser localUser) {
		if(!identityForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		identityForm.setFile(file);
		identityService.updateCompanyIdentity(identityForm, localUser.getUserId());
		eventPublisher.publishEvent(new OnNotificationEvent(localUser.getCompanyId(), null, null, NotificationType.identity, request));
		eventPublisher.publishEvent(new OnSupportEvent(localUser.getUserId(), localUser.getUser().getEmail(), ConstraintesJournal.SUPPORT_IDENTITY, request));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), ConstraintesJournal.COMPANY_UPDATE_IDENTITY, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/briefcase", method = RequestMethod.GET)
	public String showBriefcase(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("brief", suitcaseService.readBriefcaseForm(localUser.getCompanyId()));
		return "companyProfileBriefcase";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param briefcaseForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/briefcase/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateBriefcase(@Valid final BriefcaseForm briefcaseForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!briefcaseForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		suitcaseService.updateCompanyBriefcase(briefcaseForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_BRIEFCASE, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String showShedule(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("shedule", suitcaseService.readSheduleForm(localUser.getCompanyId()));
		return "companyProfileShedule";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param sheduleForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/contact/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateShedule(@Valid final SheduleForm sheduleForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!sheduleForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		suitcaseService.updateCompanyShedule(sheduleForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_CONTACT, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public String showLocation(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("location", suitcaseService.readLocationForm(localUser.getCompanyId()));
		return "companyProfileLocation";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param locationForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/location/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateLocation(@Valid final LocationForm locationForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!locationForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		suitcaseService.updateCompanyLocation(locationForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_LOCATION, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/linked", method = RequestMethod.GET)
	public String showLinked(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("linked", suitcaseService.readLinkedForm(localUser.getCompanyId()));
		model.addAttribute("maxkeysword", premiumService.getMaxKeysword(localUser.getCompanyId()));
		model.addAttribute("urlCompanies", ConstraintesURL.URL_COMPANIES.concat("/"));
		return "companyProfileLinked";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "/linked/check-url", method = RequestMethod.GET)
	@ResponseBody
	public GenericResponse checkCompanyURL(@RequestParam("url") final String url) {
		if(identityService.existsByURL(url)) {
			throw new UrlUnavailableException("message.error.url");
		}
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param linkedForm
	 * @param files
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/linked/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateLinked(@AuthenticationPrincipal final LocalUser localUser, @Valid final LinkedForm linkedForm, 
			@RequestParam(name = "files", required = false) final MultipartFile[] files) {
		if(!linkedForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		linkedForm.setFiles(files);
		suitcaseService.updateCompanyLinked(linkedForm, premiumService.getMaxKeysword(localUser.getCompanyId()));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_LINKED, null));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/credit", method = RequestMethod.GET)
	public String showCredit(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("credit", suitcaseService.readCreditForm(localUser.getCompanyId()));
		return "companyProfileCredit";
	}
	
	/**
	 * AUTORITY COMPANY_ADMIN_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param creditForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/credit/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateCredit(@Valid final CreditForm creditForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!creditForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		suitcaseService.updateCompanyCredit(creditForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_CREDIT, null));
		return new GenericResponse("success");
	}
	
}
