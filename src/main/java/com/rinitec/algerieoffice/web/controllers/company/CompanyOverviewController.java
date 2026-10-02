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

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.company.overview.IOverviewService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.company.overview.MainaboutForm;
import com.rinitec.algerieoffice.web.form.company.overview.MaincatalogForm;
import com.rinitec.algerieoffice.web.form.company.overview.MainheaderForm;
import com.rinitec.algerieoffice.web.form.company.overview.MainsliderForm;
import com.rinitec.algerieoffice.web.form.company.overview.MainthinkForm;
import com.rinitec.algerieoffice.web.form.company.overview.PresentationForm;
import com.rinitec.algerieoffice.web.form.company.overview.TimelineForm;
import com.rinitec.algerieoffice.web.listener.events.OnJournalCompanyEvent;

@Controller
@RequestMapping(value = "/company/overview")
public class CompanyOverviewController {

	private IAttributeService attributeService;
	private IOverviewService overviewService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CompanyOverviewController(IAttributeService attributeService, IOverviewService overviewService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.overviewService = overviewService;
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
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public String showHeader(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("mainheader", overviewService.readMainheaderForm(localUser.getCompanyId()));
		return "companyOverviewHeader";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param mainheaderForm
	 * @param files
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/header/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateHeader(@AuthenticationPrincipal final LocalUser localUser, @Valid final MainheaderForm mainheaderForm, 
			@RequestParam(name = "files", required = false) final MultipartFile[] files) {
		if(!mainheaderForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		mainheaderForm.setFiles(files);
		overviewService.updateMainheader(mainheaderForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_HEADER, null));
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
	@RequestMapping(value = "/presentation", method = RequestMethod.GET)
	public String showPresentation(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("presentation", overviewService.readPresentationForm(localUser.getCompanyId()));
		return "companyOverviewPresentation";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param presentationForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/presentation/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updatePresentation(@AuthenticationPrincipal final LocalUser localUser, @Valid final PresentationForm presentationForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		if(!presentationForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		presentationForm.setFile(file);
		overviewService.updatePresentation(presentationForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_PRESENTATION, null));
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
	@RequestMapping(value = "/catalog", method = RequestMethod.GET)
	public String showCatalog(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("maincatalog", overviewService.readMaincatalogForm(localUser.getCompanyId()));
		return "companyOverviewCatalog";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param maincatalogForm
	 * @param files
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/catalog/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateCatalog(@AuthenticationPrincipal final LocalUser localUser, @Valid final MaincatalogForm maincatalogForm, 
			@RequestParam(name = "files", required = false) final MultipartFile[] files) {
		if(!maincatalogForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		maincatalogForm.setFiles(files);
		overviewService.updateMaincatalog(maincatalogForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_CATALOG, null));
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
	@RequestMapping(value = "/slider", method = RequestMethod.GET)
	public String showSlider(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("mainslider", overviewService.readMainsliderForm(localUser.getCompanyId()));
		return "companyOverviewSlider";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param mainsliderForm
	 * @param files
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/slider/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateSlider(@AuthenticationPrincipal final LocalUser localUser, @Valid final MainsliderForm mainsliderForm, 
			@RequestParam(name = "files", required = false) final MultipartFile[] files) {
		if(!mainsliderForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		mainsliderForm.setFiles(files);
		overviewService.updateMainslider(mainsliderForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_SLIDER, null));
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
	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	public String showTimeline(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("timeline", overviewService.readTimelineForm(localUser.getCompanyId()));
		return "companyOverviewTimeline";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param timelineForm
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/timeline/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateTimeline(@Valid final TimelineForm timelineForm, @AuthenticationPrincipal final LocalUser localUser) {
		if(!timelineForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		overviewService.updateTimeline(timelineForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_TIMELINE, null));
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
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String showAbout(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("about", overviewService.readMainaboutForm(localUser.getCompanyId()));
		return "companyOverviewAbout";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param mainaboutForm
	 * @param file
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/about/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateAbout(@AuthenticationPrincipal final LocalUser localUser, @Valid final MainaboutForm mainaboutForm, 
			@RequestParam(name = "file", required = false) final MultipartFile file) {
		if(!mainaboutForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		mainaboutForm.setFile(file);
		overviewService.updateMainabout(mainaboutForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_ABOUT, null));
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
	@RequestMapping(value = "/think", method = RequestMethod.GET)
	public String showThink(final HttpServletRequest request, final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeCompany(model, config, localUser);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("mainthink", overviewService.readMainthinkForm(localUser.getCompanyId()));
		return "companyOverviewThink";
	}
	
	/**
	 * AUTORITY COMPANY_EDIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param mainthinkForm
	 * @param files
	 * @param localUser
	 * @return
	 */
	@RequestMapping(value = "/think/update", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse updateThink(@AuthenticationPrincipal final LocalUser localUser, @Valid final MainthinkForm mainthinkForm, 
			@RequestParam(name = "files", required = false) final MultipartFile[] files) {
		if(!mainthinkForm.getId().equals(localUser.getCompanyId())) {
			throw new AccessAuthorityException();
		}
		mainthinkForm.setFiles(files);
		overviewService.updateMainthink(mainthinkForm);
		eventPublisher.publishEvent(new OnJournalCompanyEvent(localUser.getCompanyId(), localUser.getUserId(), 
				ConstraintesJournal.COMPANY_UPDATE_THINK, null));
		return new GenericResponse("success");
	}
	
}
