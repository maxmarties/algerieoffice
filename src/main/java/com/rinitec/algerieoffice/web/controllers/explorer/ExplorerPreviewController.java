package com.rinitec.algerieoffice.web.controllers.explorer;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.explorer.IExplorerDetailService;
import com.rinitec.algerieoffice.services.explorer.IExplorerService;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.explorer.ExplorerContactForm;
import com.rinitec.algerieoffice.web.modal.Langage;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCompany;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCurrent;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageAnnonce;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageEmploye;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageEvent;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPagePost;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageWork;

@Controller
@RequestMapping(value = "/explorer/preview")
public class ExplorerPreviewController {

	private IExplorerService explorerService;
	private IExplorerDetailService explorerDetailService;
	private IAttributeService attributeService;
	
	@Autowired
	public ExplorerPreviewController(IExplorerService explorerService, IExplorerDetailService explorerDetailService, IAttributeService attributeService) {
		this.explorerService = explorerService;
		this.explorerDetailService = explorerDetailService;
		this.attributeService = attributeService;
	}
	
	private final void updateLanguage(final HttpServletRequest request, final HttpServletResponse response, final Model model, final String language) {
		if(!RequestContextUtils.getLocale(request).getLanguage().equalsIgnoreCase(language)) {
			final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("langage", new Langage(language));
		}
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showHome(final HttpServletRequest request, final HttpServletResponse response, final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("hasLoading", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageHome(explorerCurrent, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewHome";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param localUser
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/agent-load", method = RequestMethod.GET)
	public String loadAgents(final Model model, @AuthenticationPrincipal final LocalUser localUser,
			@RequestParam("rows") final Integer rows, @RequestParam("page") final Integer page) {
		model.addAttribute("pageAgent", page);
		model.addAttribute("hasPreview", true);
		model.addAttribute("listAgents", explorerService.findExplorerWidgetAgentList(localUser.getCompanyId(), rows, page));
		return "explorerAgentList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/presentation", method = RequestMethod.GET)
	public String showPresentation(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPagePresentation(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewPresentation";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/historique", method = RequestMethod.GET)
	public String showTimeline(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageTimeline(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewTimeline";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/actualites", method = RequestMethod.GET)
	public String showActus(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("desc", true);
		model.addAttribute("view", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewActus";
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
	 * @param view
	 * @param companyId
	 * @param uri
	 * @param premium
	 * @return
	 */
	@RequestMapping(value = "/actus-load", method = RequestMethod.GET)
	public String loadActus(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!localUser.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final boolean hasView = view.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, true);
		model.addAttribute("hasView", hasView);
		model.addAttribute("hasPreview", true);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxActus(localUser.getUserId(), explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerActuList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/evenements", method = RequestMethod.GET)
	public String showEvents(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("desc", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewEvents";
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
	 * @param view
	 * @param companyId
	 * @param uri
	 * @param premium
	 * @return
	 */
	@RequestMapping(value = "/events-load", method = RequestMethod.GET)
	public String loadEvents(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!localUser.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final boolean hasView = view.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, true);
		model.addAttribute("hasView", hasView);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxEvents(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerEventList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/evenements/{identify}", method = RequestMethod.GET)
	public String showEvent(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, @PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerPageEvent explorerPage = explorerDetailService.readExplorerPageEvent(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/explorer/preview/404";
		}
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewEvent";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/realisations", method = RequestMethod.GET)
	public String showWorks(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("desc", true);
		model.addAttribute("viewWidget", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewWorks";
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
	 * @param view
	 * @param companyId
	 * @param uri
	 * @param premium
	 * @return
	 */
	@RequestMapping(value = "/works-load", method = RequestMethod.GET)
	public String loadWorks(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!localUser.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, true);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxWorks(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerWorkList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/realisations/{identify}", method = RequestMethod.GET)
	public String showWork(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, @PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerPageWork explorerPage = explorerDetailService.readExplorerPageWork(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/explorer/preview/404";
		}
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewWork";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/faqs", method = RequestMethod.GET)
	public String showFaqs(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("view", true);
		model.addAttribute("viewList", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewFaqs";
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
	 * @param view
	 * @param companyId
	 * @param uri
	 * @param premium
	 * @return
	 */
	@RequestMapping(value = "/faqs-load", method = RequestMethod.GET)
	public String loadFaqs(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!localUser.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, true);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxFaqs(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerFaqList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/partenaires", method = RequestMethod.GET)
	public String showPartners(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("view", true);
		model.addAttribute("viewList", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewPartners";
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
	 * @param view
	 * @param companyId
	 * @param uri
	 * @param premium
	 * @return
	 */
	@RequestMapping(value = "/partners-load", method = RequestMethod.GET)
	public String loadPartners(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!localUser.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, true);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxPartners(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerPartnerList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/produits-et-services", method = RequestMethod.GET)
	public String showPosts(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, @RequestParam(name = "categorie", required = false) final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		if(!StringUtils.isEmpty(identify)) {
			final UUIDMini categoryMini = explorerService.findExplorerCategory(companyId, identify);
			if(categoryMini == null) {
				return "redirect:/explorer/preview/404";
			}
			model.addAttribute("categoryMini", categoryMini);
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("desc", true);
		model.addAttribute("viewWidget", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPagePosts(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewPosts";
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
	 * @param view
	 * @param companyId
	 * @param uri
	 * @param premium
	 * @param lateral
	 * @return
	 */
	@RequestMapping(value = "/produits-load", method = RequestMethod.GET)
	public String loadPosts(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium, @RequestParam("lt") final Boolean lateral) {
		if(!localUser.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, true);
		model.addAttribute("companyURI", uri);
		model.addAttribute("lateral", lateral);
		model.addAttribute("explorerList", explorerService.readExplorerInboxPosts(explorerCurrent, filter, search, sort, rows, page, hasDesc));
		return "explorerPostList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/produits-et-services/{identify}", method = RequestMethod.GET)
	public String showPost(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, @PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerPagePost explorerPage = explorerDetailService.readExplorerPagePost(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/explorer/preview/404";
		}
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewPost";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/marketplace", method = RequestMethod.GET)
	public String showAnnonces(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("desc", true);
		model.addAttribute("view", true);
		model.addAttribute("viewList", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageAnnonces(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewAnnonces";
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
	 * @param view
	 * @param companyId
	 * @param uri
	 * @param premium
	 * @return
	 */
	@RequestMapping(value = "/market-load", method = RequestMethod.GET)
	public String loadAnnonces(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!localUser.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, true);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxAnnonces(explorerCurrent, filter, search, sort, rows, page, hasDesc));
		return "explorerAnnonceList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/marketplace/{identify}", method = RequestMethod.GET)
	public String showAnnonce(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, @PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerPageAnnonce explorerPage = explorerDetailService.readExplorerPageAnnonce(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/explorer/preview/404";
		}
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("hasAutorized", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewAnnonce";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/offres-emploi", method = RequestMethod.GET)
	public String showEmployes(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("desc", true);
		model.addAttribute("view", true);
		model.addAttribute("viewList", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageAnnonces(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewEmployes";
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
	 * @param view
	 * @param companyId
	 * @param uri
	 * @param premium
	 * @return
	 */
	@RequestMapping(value = "/employe-load", method = RequestMethod.GET)
	public String loadEmployes(final Model model, @AuthenticationPrincipal final LocalUser localUser, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!localUser.getCompanyId().equals(companyId)) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, true);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxEmployes(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerEmployeList";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/offres-emploi/{identify}", method = RequestMethod.GET)
	public String showEmploye(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, @PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerPageEmploye explorerPage = explorerDetailService.readExplorerPageEmploye(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/explorer/preview/404";
		}
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewEmploye";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String showContact(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageContact(companyId));
		model.addAttribute("explorerContact", new ExplorerContactForm(companyId));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreviewContact";
	}
	
	/**
	 * AUTORITY COMPANY_VISIT_PRIVILEGE
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param localUser
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String show404(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			@AuthenticationPrincipal final LocalUser localUser, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = localUser.getCompanyId();
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, null, true);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		attributeService.attributePreview(model, localUser, config);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPage404(companyId));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerPreview404";
	}
	
}
