package com.rinitec.algerieoffice.web.controllers.explorer;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
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

import com.rinitec.algerieoffice.captcha.ICaptchaService;
import com.rinitec.algerieoffice.enums.AccessType;
import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.blog.IBlogSearchService;
import com.rinitec.algerieoffice.services.explorer.IExplorerDetailService;
import com.rinitec.algerieoffice.services.explorer.IExplorerService;
import com.rinitec.algerieoffice.services.explorer.IExplorerVisitorService;
import com.rinitec.algerieoffice.services.publics.ISectorService;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.explorer.DocumentContactForm;
import com.rinitec.algerieoffice.web.form.explorer.ExplorerContactForm;
import com.rinitec.algerieoffice.web.form.feedback.AppointmentForm;
import com.rinitec.algerieoffice.web.form.feedback.NoticeForm;
import com.rinitec.algerieoffice.web.form.feedback.ReportForm;
import com.rinitec.algerieoffice.web.listener.events.OnAccessCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnAccessDocumentEvent;
import com.rinitec.algerieoffice.web.listener.events.OnDetectEvent;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.CurrentVisitor;
import com.rinitec.algerieoffice.web.modal.Langage;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCompany;
import com.rinitec.algerieoffice.web.modal.explorer.ExplorerCurrent;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageAnnonce;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageEmploye;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageEvent;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPagePost;
import com.rinitec.algerieoffice.web.modal.explorer.pages.ExplorerPageWork;

@Controller
@RequestMapping(value = "/entreprises")
public class ExplorerBrowserController {

	private ICaptchaService captchaService;
	private IAttributeService attributeService;
	private ISectorService sectorService;
	private IExplorerService explorerService;
	private IExplorerDetailService explorerDetailService;
	private IExplorerVisitorService explorerVisitorService;
	private IBlogSearchService blogSearchService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public ExplorerBrowserController(ICaptchaService captchaService, IAttributeService attributeService, ISectorService sectorService, 
			IExplorerService explorerService, IExplorerDetailService explorerDetailService, IExplorerVisitorService explorerVisitorService, 
			IBlogSearchService blogSearchService, ApplicationEventPublisher eventPublisher) {
		this.captchaService = captchaService;
		this.attributeService = attributeService;
		this.sectorService = sectorService;
		this.explorerService = explorerService;
		this.explorerDetailService = explorerDetailService;
		this.explorerVisitorService = explorerVisitorService;
		this.blogSearchService = blogSearchService;
		this.eventPublisher = eventPublisher;
	}
	
	private final CurrentVisitor pushFeedbackForms(final Model model, final CurrentUser currentUser, final Long companyId) {
		if(currentUser != null) {
			final CurrentVisitor currentVisitor = explorerVisitorService.readCurrentVisitor(currentUser.getUser(), companyId);
			model.addAttribute("currentVisitor", currentVisitor);
			model.addAttribute("notice", new NoticeForm(companyId));
			model.addAttribute("appointment", new AppointmentForm(companyId));
			model.addAttribute("report", new ReportForm(companyId));
			return currentVisitor;
		} else {
			model.addAttribute("hasCompanyLogin", explorerVisitorService.hasCompanyLogin(companyId));
		}
		return null;
	}
	
	private final void pushAccessEvent(final HttpServletRequest request, final Long companyId, final CurrentUser currentUser, final CurrentVisitor currentVisitor, 
			final AccessType accessType) {
		if(currentVisitor == null || !currentVisitor.isHasLeader()) {
			eventPublisher.publishEvent(new OnAccessCompanyEvent(companyId, currentUser != null ? currentUser.getUserId() : null, request.getHeader("user-agent"), accessType));
		}
	}
	
	private final void updateLanguage(final HttpServletRequest request, final HttpServletResponse response, final Model model, final String language) {
		if(!RequestContextUtils.getLocale(request).getLanguage().equalsIgnoreCase(language)) {
			final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			localeResolver.setLocale(request, response, new Locale(language));
			model.addAttribute("langage", new Langage(language));
		}
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showHome(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("activities", sectorService.findActivityLink());
		model.addAttribute("marketBlogs", blogSearchService.findLastBlogMarketMini(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getCompaniesMapsiteURL());
		return "homeCompanies";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}", method = RequestMethod.GET)
	public String showCompanyHome(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("hasLoading", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageHome(explorerCurrent, explorerCompany.getMenu().getDisplay()));
		model.addAttribute("action", request.getParameter("action"));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.home);
		if(currentUser != null && !currentUser.admin() && !companyId.equals(currentUser.getCompanyId()) && explorerCurrent.getPremium() > 1) {
			eventPublisher.publishEvent(new OnDetectEvent(currentUser.getUserId(), companyId, true, request));
		}
		return "explorerBrowserHome";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyURL
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/agent-load", method = RequestMethod.GET)
	public String loadAgents(final Model model, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("rows") final Integer rows, @RequestParam("page") final Integer page) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			throw new AccessAuthorityException();
		}
		model.addAttribute("pageAgent", page);
		model.addAttribute("listAgents", explorerService.findExplorerWidgetAgentList(companyId, rows, page));
		return "explorerAgentList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyURL
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/notice-load", method = RequestMethod.GET)
	public String loadNotices(final Model model, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("rows") final Integer rows, @RequestParam("page") final Integer page) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			throw new AccessAuthorityException();
		}
		model.addAttribute("pageNotice", page);
		model.addAttribute("listNotices", explorerService.findExplorerWidgetNoticeList(companyId, rows, page));
		return "explorerNoticeList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/presentation", method = RequestMethod.GET)
	public String showPresentation(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPagePresentation(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.society);
		return "explorerBrowserPresentation";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/historique", method = RequestMethod.GET)
	public String showTimeline(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageTimeline(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.history);
		return "explorerBrowserTimeline";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/actualites", method = RequestMethod.GET)
	public String showActus(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("desc", true);
		model.addAttribute("view", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.actus);
		return "explorerBrowserActus";
	}
	
	private final Long readUserIdAuthentication(final Authentication authentication) {
		if (authentication != null && authentication.getPrincipal() instanceof LocalUser) {
			return ((LocalUser) authentication.getPrincipal()).getUserId();
		}
		return null;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param companyURL
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
	@RequestMapping(value = "/{companyURL}/actus-load", method = RequestMethod.GET)
	public String loadActus(final Model model, final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!companyId.equals(explorerService.findExplorerCompanyId(companyURL))) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final boolean hasView = view.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, false);
		final Long userId = readUserIdAuthentication(authentication);
		model.addAttribute("hasView", hasView);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxActus(userId, explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerActuList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/evenements", method = RequestMethod.GET)
	public String showEvents(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		model.addAttribute("desc", true);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.events);
		return "explorerBrowserEvents";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyURL
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
	@RequestMapping(value = "/{companyURL}/events-load", method = RequestMethod.GET)
	public String loadEvents(final Model model, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!companyId.equals(explorerService.findExplorerCompanyId(companyURL))) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final boolean hasView = view.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, false);
		model.addAttribute("hasView", hasView);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxEvents(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerEventList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/evenements/{identify}", method = RequestMethod.GET)
	public String showEvent(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerPageEvent explorerPage = explorerDetailService.readExplorerPageEvent(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/entreprises/".concat(companyURL).concat("/404");
		}
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final DocumentContactForm documentContact = explorerVisitorService.readDocumentContactForm(currentUser, companyId, explorerPage.getInboxId(), DocumentType.event);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		model.addAttribute("documentContact", documentContact);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		if(currentUser != null) {
			model.addAttribute("favoriteDocument", 
					explorerVisitorService.hasFavoriteDocument(currentUser.getUserId(), explorerPage.getInboxId(), DocumentType.event));
		} else {
			model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		}
		if(currentVisitor == null || !currentVisitor.isHasLeader()) {
			eventPublisher.publishEvent(new OnAccessDocumentEvent(explorerPage.getInboxId(), DocumentType.event, true));
		}
		return "explorerBrowserEvent";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/realisations", method = RequestMethod.GET)
	public String showWorks(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		model.addAttribute("desc", true);
		model.addAttribute("viewWidget", true);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.works);
		return "explorerBrowserWorks";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyURL
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
	@RequestMapping(value = "/{companyURL}/works-load", method = RequestMethod.GET)
	public String loadWorks(final Model model, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!companyId.equals(explorerService.findExplorerCompanyId(companyURL))) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, false);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxWorks(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerWorkList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/realisations/{identify}", method = RequestMethod.GET)
	public String showWork(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerPageWork explorerPage = explorerDetailService.readExplorerPageWork(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/entreprises/".concat(companyURL).concat("/404");
		}
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		return "explorerBrowserWork";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/faqs", method = RequestMethod.GET)
	public String showFaqs(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		model.addAttribute("view", true);
		model.addAttribute("viewList", true);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.faqs);
		return "explorerBrowserFaqs";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyURL
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
	@RequestMapping(value = "/{companyURL}/faqs-load", method = RequestMethod.GET)
	public String loadFaqs(final Model model, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!companyId.equals(explorerService.findExplorerCompanyId(companyURL))) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, false);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxFaqs(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerFaqList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/partenaires", method = RequestMethod.GET)
	public String showPartners(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageElements(companyId, explorerCompany.getMenu().getDisplay()));
		model.addAttribute("view", true);
		model.addAttribute("viewList", true);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.partners);
		return "explorerBrowserPartners";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyURL
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
	@RequestMapping(value = "/{companyURL}/partners-load", method = RequestMethod.GET)
	public String loadPartners(final Model model, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!companyId.equals(explorerService.findExplorerCompanyId(companyURL))) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, false);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxPartners(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerPartnerList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param category
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/produits-et-services", method = RequestMethod.GET)
	public String showPosts(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL,
			@RequestParam(name = "categorie", required = false) final String category, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		if(!StringUtils.isEmpty(category)) {
			final UUIDMini categoryMini = explorerService.findExplorerCategory(companyId, category);
			if(categoryMini == null) {
				return "redirect:/entreprises/".concat(companyURL).concat("/404");
			}
			model.addAttribute("categoryMini", categoryMini);
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPagePosts(companyId, explorerCompany.getMenu().getDisplay()));
		model.addAttribute("desc", true);
		model.addAttribute("viewWidget", true);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.posts);
		return "explorerBrowserPosts";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyURL
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
	@RequestMapping(value = "/{companyURL}/produits-load", method = RequestMethod.GET)
	public String loadPosts(final Model model, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium, @RequestParam("lt") final Boolean lateral) {
		if(!companyId.equals(explorerService.findExplorerCompanyId(companyURL))) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, false);
		model.addAttribute("companyURI", uri);
		model.addAttribute("lateral", lateral);
		model.addAttribute("explorerList", explorerService.readExplorerInboxPosts(explorerCurrent, filter, search, sort, rows, page, hasDesc));
		return "explorerPostList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/produits-et-services/{identify}", method = RequestMethod.GET)
	public String showPost(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerPagePost explorerPage = explorerDetailService.readExplorerPagePost(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/entreprises/".concat(companyURL).concat("/404");
		}
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final DocumentContactForm documentContact = explorerVisitorService.readDocumentContactForm(currentUser, companyId, explorerPage.getInboxId(), DocumentType.post);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		model.addAttribute("documentContact", documentContact);
		model.addAttribute("prospect", request.getParameter("prospect"));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		if(currentUser != null) {
			model.addAttribute("favoriteDocument", explorerVisitorService.hasFavoriteDocument(currentUser.getUserId(), explorerPage.getInboxId(), DocumentType.post));
		} else {
			model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		}
		if(currentVisitor == null || !currentVisitor.isHasLeader()) {
			eventPublisher.publishEvent(new OnAccessDocumentEvent(explorerPage.getInboxId(), DocumentType.post, true));
		}
		return "explorerBrowserPost";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param type
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/marketplace", method = RequestMethod.GET)
	public String showAnnonces(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL,
			@RequestParam(name = "type", required = false) final Integer type, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		if(type != null && type >= 1 && type <= 5) {
			model.addAttribute("typeMarket", type);
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageAnnonces(companyId, explorerCompany.getMenu().getDisplay()));
		model.addAttribute("desc", true);
		model.addAttribute("view", true);
		model.addAttribute("viewList", true);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.market);
		return "explorerBrowserAnnonces";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyURL
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
	@RequestMapping(value = "/{companyURL}/market-load", method = RequestMethod.GET)
	public String loadAnnonces(final Model model, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!companyId.equals(explorerService.findExplorerCompanyId(companyURL))) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, false);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxAnnonces(explorerCurrent, filter, search, sort, rows, page, hasDesc));
		return "explorerAnnonceList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/marketplace/{identify}", method = RequestMethod.GET)
	public String showAnnonce(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerPageAnnonce explorerPage = explorerDetailService.readExplorerPageAnnonce(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/entreprises/".concat(companyURL).concat("/404");
		}
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final DocumentContactForm documentContact = explorerVisitorService.readDocumentContactForm(currentUser, companyId, explorerPage.getInboxId(), DocumentType.annonce);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		model.addAttribute("documentContact", documentContact);
		model.addAttribute("hasAutorized", explorerVisitorService.hasAutorizedAnnonce(currentUser, companyId, explorerPage.getVisibility()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		if(currentUser != null) {
			model.addAttribute("favoriteDocument", explorerVisitorService.hasFavoriteDocument(currentUser.getUserId(), explorerPage.getInboxId(), DocumentType.annonce));
		} else {
			model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		}
		if(currentVisitor == null || !currentVisitor.isHasLeader()) {
			eventPublisher.publishEvent(new OnAccessDocumentEvent(explorerPage.getInboxId(), DocumentType.annonce, true));
		}
		return "explorerBrowserAnnonce";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/offres-emploi", method = RequestMethod.GET)
	public String showEmployes(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		model.addAttribute("desc", true);
		model.addAttribute("view", true);
		model.addAttribute("viewList", true);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageAnnonces(companyId, explorerCompany.getMenu().getDisplay()));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.employe);
		return "explorerBrowserEmployes";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyURL
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
	@RequestMapping(value = "/{companyURL}/employe-load", method = RequestMethod.GET)
	public String loadEmployes(final Model model, @PathVariable("companyURL") final String companyURL, 
			@RequestParam("fl") final String filter, @RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, 
			@RequestParam("rw") final Integer rows, @RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, 
			@RequestParam("vw") final String view, @RequestParam("id") final Long companyId, @RequestParam("uri") final String uri, 
			@RequestParam("pr") final Integer premium) {
		if(!companyId.equals(explorerService.findExplorerCompanyId(companyURL))) {
			throw new AccessAuthorityException();
		}
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final ExplorerCurrent explorerCurrent = new ExplorerCurrent(companyId, uri, premium, false);
		model.addAttribute("currentItem", (page - 1) * rows);
		model.addAttribute("explorerList", explorerService.readExplorerInboxEmployes(explorerCurrent, search, sort, rows, page, hasDesc));
		return "explorerEmployeList";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param identify
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/offres-emploi/{identify}", method = RequestMethod.GET)
	public String showEmploye(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@PathVariable("identify") final String identify, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final ExplorerPageEmploye explorerPage = explorerDetailService.readExplorerPageEmploye(explorerCurrent, identify);
		if(explorerPage == null) {
			return "redirect:/entreprises/".concat(companyURL).concat("/404");
		}
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final DocumentContactForm documentContact = explorerVisitorService.readDocumentContactForm(currentUser, companyId, 
				explorerPage.getInboxId(), DocumentType.employe);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerPage);
		model.addAttribute("documentContact", documentContact);
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		if(currentUser != null) {
			model.addAttribute("favoriteDocument", explorerVisitorService.hasFavoriteDocument(currentUser.getUserId(), explorerPage.getInboxId(), DocumentType.employe));
		} else {
			model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		}
		if(currentVisitor == null || !currentVisitor.isHasLeader()) {
			eventPublisher.publishEvent(new OnAccessDocumentEvent(explorerPage.getInboxId(), DocumentType.employe, true));
		}
		return "explorerBrowserEmploye";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/contact", method = RequestMethod.GET)
	public String showContact(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final CurrentVisitor currentVisitor = pushFeedbackForms(model, currentUser, companyId);
		final ExplorerContactForm explorerContact = explorerVisitorService.readExplorerContactForm(currentUser, companyId);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		if(!StringUtils.isEmpty(request.getParameter("objet"))) {
			explorerContact.pushObject(request.getParameter("objet"));
		}
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPageContact(companyId));
		model.addAttribute("explorerContact", explorerContact);
		if(currentUser == null) {
			model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		}
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushAccessEvent(request, companyId, currentUser, currentVisitor, AccessType.contact);
		return "explorerBrowserContact";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/{companyURL}/404", method = RequestMethod.GET)
	public String show404(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("companyURL") final String companyURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final Long companyId = explorerService.findExplorerCompanyId(companyURL);
		if(companyId == null) {
			return "redirect:/404/entreprises";
		}
		final ExplorerCurrent explorerCurrent = explorerService.readExplorerCurrent(companyId, companyURL, false);
		final CurrentUser currentUser = attributeService.attributeExplorer(model, authentication, config);
		final ExplorerCompany explorerCompany = explorerService.readExplorerCompany(explorerCurrent);
		model.addAttribute("explorerCurrent", explorerCurrent);
		model.addAttribute("explorerCompany", explorerCompany);
		model.addAttribute("explorerPage", explorerService.readExplorerPage404(companyId));
		updateLanguage(request, response, model, explorerCompany.getLanguage());
		pushFeedbackForms(model, currentUser, companyId);
		return "explorerBrowser404";
	}
	
}
