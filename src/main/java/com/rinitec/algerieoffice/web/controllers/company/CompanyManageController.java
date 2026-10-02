package com.rinitec.algerieoffice.web.controllers.company;

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

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.premium.IPremiumService;
import com.rinitec.algerieoffice.services.company.manage.IManageService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.company.manage.AppearanceForm;
import com.rinitec.algerieoffice.web.form.company.manage.MaindisplayForm;
import com.rinitec.algerieoffice.web.form.company.manage.PreferenceForm;
import com.rinitec.algerieoffice.web.form.company.manage.StickyForm;
import com.rinitec.algerieoffice.web.form.company.manage.WidgetB2CForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;

@Controller
@RequestMapping(value = "/company/manage")
public class CompanyManageController {

	private IPremiumService premiumService;
	private IAttributeService attributeService;
	private IManageService manageService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyManageController(IPremiumService premiumService, IAttributeService attributeService, IManageService manageService,
			ApplicationEventPublisher eventPublisher) {
		this.premiumService = premiumService;
		this.attributeService = attributeService;
		this.manageService = manageService;
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
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public String showDisplay(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("maindisplay", manageService.readMaindisplayForm(localUser.getCompanyId()));
		return "companyManageDisplay";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param maindisplayForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/display/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateDisplay(@Valid final MaindisplayForm maindisplayForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!maindisplayForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		manageService.updateMaindisplay(maindisplayForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_DISPLAY, null));
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
	@RequestMapping(value = "/sticky", method = RequestMethod.GET)
	public String showSticky(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("sticky", manageService.readStickyForm(localUser.getCompanyId()));
		return "companyManageSticky";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param stickyForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/sticky/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateSticky(@Valid final StickyForm stickyForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!stickyForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		manageService.updateSticky(stickyForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_STICKY, null));
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
	@RequestMapping(value = "/widget", method = RequestMethod.GET)
	public String showWidget(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("widget", manageService.readWidgetB2CForm(localUser.getCompanyId()));
		model.addAttribute("widgetView", manageService.readWidgetView(localUser.getCompanyId()));
		return "companyManageWidget";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param widgetB2CForm
	 * @param localUser
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/widget/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateWidget(@Valid final WidgetB2CForm widgetB2CForm, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		if(!widgetB2CForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		widgetB2CForm.setFile(file);
		manageService.upadteWidgetB2C(widgetB2CForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_WIDGET, null));
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
	@RequestMapping(value = "/appearance", method = RequestMethod.GET)
	public String showAppearance(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final long companyId = localUser.getCompanyId();
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("appearanceView", manageService.readAppearanceView(companyId));
		model.addAttribute("appearance", manageService.readAppearanceForm(companyId));
		return "companyManageAppearance";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param appearanceForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/appearance/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateAppearance(@Valid final AppearanceForm appearanceForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!appearanceForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		manageService.updateAppearance(appearanceForm, premiumService.hasPremium(localUser.getCompanyId()));
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_APPEARANCE, null));
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
	@RequestMapping(value = "/preferences", method = RequestMethod.GET)
	public String showPreference(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final long companyId = localUser.getCompanyId();
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("choseUsers", manageService.findAllMessengers(companyId));
		model.addAttribute("preference", manageService.readPreferenceForm(companyId));
		return "companyManagePreference";
	}
	
	/**
	 * AUTORITY COMPANY_MANAGER_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param preferenceForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/preferences/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updatePreference(@Valid final PreferenceForm preferenceForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!preferenceForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		manageService.updatePreferences(preferenceForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_PREFERENCE, null));
		return new GenericResponse("success");
	}
	
}
