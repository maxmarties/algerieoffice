package com.rinitec.algerieoffice.web.controllers.publics;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.captcha.ICaptchaService;
import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.analytic.ISkillsService;
import com.rinitec.algerieoffice.services.explorer.IExplorerPromoteService;
import com.rinitec.algerieoffice.services.explorer.IExplorerVisitorService;
import com.rinitec.algerieoffice.services.publics.ISearchDetailService;
import com.rinitec.algerieoffice.services.user.favorite.IFavoriteDocumentService;
import com.rinitec.algerieoffice.services.user.feedback.ILikeService;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.explorer.DocumentContactForm;
import com.rinitec.algerieoffice.web.form.search.SearchAnnonceForm;
import com.rinitec.algerieoffice.web.form.search.SearchEmployeForm;
import com.rinitec.algerieoffice.web.form.search.SearchEventForm;
import com.rinitec.algerieoffice.web.form.search.SearchNewsForm;
import com.rinitec.algerieoffice.web.form.search.SearchPostForm;
import com.rinitec.algerieoffice.web.form.user.repport.CommentForm;
import com.rinitec.algerieoffice.web.listener.events.OnAccessDocumentEvent;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.Langage;
import com.rinitec.algerieoffice.web.modal.publics.CompanyAutorMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.ScreenInboxAnnonce;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.ScreenInboxEmploye;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.ScreenInboxEvent;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.ScreenInboxNews;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.ScreenInboxPost;

@Controller
@RequestMapping(value = "/marketplace")
public class MarketplaceController {

	private ICaptchaService captchaService;
	private IAttributeService attributeService;
	private ISearchDetailService searchDetailService;
	private IFavoriteDocumentService favoriteService;
	private ILikeService likeService;
	private ISkillsService skillsService;
	private IExplorerVisitorService explorerVisitorService;
	private IExplorerPromoteService explorerPromoteService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public MarketplaceController(ICaptchaService captchaService, IAttributeService attributeService, ISearchDetailService searchDetailService, 
			IFavoriteDocumentService favoriteService, ILikeService likeService, ISkillsService skillsService, IExplorerVisitorService explorerVisitorService, 
			IExplorerPromoteService explorerPromoteService, ApplicationEventPublisher eventPublisher) {
		this.captchaService = captchaService;
		this.attributeService = attributeService;
		this.searchDetailService = searchDetailService;
		this.favoriteService = favoriteService;
		this.likeService = likeService;
		this.skillsService = skillsService;
		this.explorerVisitorService = explorerVisitorService;
		this.explorerPromoteService = explorerPromoteService;
		this.eventPublisher = eventPublisher;
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
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplaceMapsiteURL());
		return "homeMarketplace";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param token
	 * @param keyword
	 * @param location
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/produits-et-services", method = RequestMethod.GET)
	public String showPosts(final Model model, final Authentication authentication, @RequestParam(name = "token", required = false) final String token,
			@RequestParam(name = "tag", required = false) final String keyword, @RequestParam(name = "secteur", required = false) final Integer sectorParam,
			@RequestParam(name = "location", required = false) final Integer location, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication);
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final Integer sector = sectorParam != null && sectorParam >= 1 && sectorParam <= ConstraintesForm.COUNT_SECTOR_ACTIITY ? sectorParam : null;
		final Integer wilaya = location != null && location >= 1 && location <= ConstraintesForm.COUNT_WILAYA ? location : null;
		final Long userId = currentUser != null ? currentUser.getUserId() : null;
		model.addAttribute("desc", true);
		model.addAttribute("token", token);
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("searchPost", new SearchPostForm(userId, token, keyword, sector, wilaya, currentConfig.parseDefaultResult()));
		model.addAttribute("skills", skillsService.readMarketplaceSkills());
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplacePostsMapsiteURL());
		return "homeMarketplacePosts";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param postURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/produits-et-services/{companyURL}/{postURL}", method = RequestMethod.GET)
	public String showPost(final HttpServletRequest request, final HttpServletResponse response, final Model model, final Authentication authentication, 
			@PathVariable("companyURL") final String companyURL, @PathVariable("postURL") final String postURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CompanyAutorMini company = searchDetailService.findCompanyAutorMini(companyURL);
		if(company == null) {
			return "redirect:/404";
		}
		final Long companyId = company.getId();
		final ScreenInboxPost inbox = searchDetailService.readScreenInboxPost(companyId, postURL);
		if(inbox == null) {
			return "redirect:/404";
		}
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication, config);
		final DocumentContactForm documentContact = explorerVisitorService.readDocumentContactForm(currentUser, companyId, inbox.getId(), DocumentType.post);
		if(currentUser != null) {
			model.addAttribute("hasLeader", companyId.equals(currentUser.getCompanyId()));
			model.addAttribute("hasFavorite", favoriteService.hasFavoriteDocument(currentUser.getUserId(), inbox.getId(), DocumentType.post));
		} else {
			model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		}
		model.addAttribute("company", company);
		model.addAttribute("inbox", inbox);
		model.addAttribute("documentContact", documentContact);
		model.addAttribute("prospect", request.getParameter("prospect"));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplacePostMapsiteURL(companyURL, postURL));
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		updateLanguage(request, response, model, company.getLanguage());
		if(currentUser == null || !companyId.equals(currentUser.getCompanyId())) {
			eventPublisher.publishEvent(new OnAccessDocumentEvent(inbox.getId(), DocumentType.post, true));
		}
		return "homeMarketplacePost";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param token
	 * @param keyword
	 * @param location
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/annonces", method = RequestMethod.GET)
	public String showAnnonces(final Model model, final Authentication authentication, @RequestParam(name = "token", required = false) final String token,
			@RequestParam(name = "tag", required = false) final String keyword, @RequestParam(name = "secteur", required = false) final Integer sectorParam, 
			@RequestParam(name = "location", required = false) final Integer location,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication);
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final Integer sector = sectorParam != null && sectorParam >= 1 && sectorParam <= ConstraintesForm.COUNT_SECTOR_ACTIITY ? sectorParam : null;
		final Integer wilaya = location != null && location >= 1 && location <= ConstraintesForm.COUNT_WILAYA ? location : null;
		final Long userId = currentUser != null ? currentUser.getUserId() : null;
		model.addAttribute("desc", true);
		model.addAttribute("token", token);
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("searchAnnonce", new SearchAnnonceForm(userId, token, keyword, sector, wilaya, currentConfig.parseDefaultResult()));
		model.addAttribute("skills", skillsService.readMarketplaceSkills());
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplaceAnnoncesMapsiteURL());
		return "homeMarketplaceAnnonces";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param annonceURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/annonces/{companyURL}/{annonceURL}", method = RequestMethod.GET)
	public String showAnnonce(final HttpServletRequest request, final HttpServletResponse response, final Model model, final Authentication authentication, 
			@PathVariable("companyURL") final String companyURL, @PathVariable("annonceURL") final String annonceURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CompanyAutorMini company = searchDetailService.findCompanyAutorMini(companyURL);
		if(company == null) {
			return "redirect:/404";
		}
		final Long companyId = company.getId();
		final ScreenInboxAnnonce inbox = searchDetailService.readScreenInboxAnnonce(companyId, annonceURL);
		if(inbox == null) {
			return "redirect:/404";
		}
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication, config);
		final DocumentContactForm documentContact = explorerVisitorService.readDocumentContactForm(currentUser, companyId, inbox.getId(), DocumentType.annonce);
		if(currentUser != null) {
			model.addAttribute("hasLeader", companyId.equals(currentUser.getCompanyId()));
			model.addAttribute("hasFavorite", favoriteService.hasFavoriteDocument(currentUser.getUserId(), inbox.getId(), DocumentType.annonce));
		} else {
			model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		}
		model.addAttribute("company", company);
		model.addAttribute("inbox", inbox);
		model.addAttribute("documentContact", documentContact);
		model.addAttribute("hasAutorized", explorerVisitorService.hasAutorizedAnnonce(currentUser, companyId, inbox.getVisibility()));
		model.addAttribute("prospect", request.getParameter("prospect"));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplaceAnnonceMapsiteURL(companyURL, annonceURL));
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		updateLanguage(request, response, model, company.getLanguage());
		if(currentUser == null || !companyId.equals(currentUser.getCompanyId())) {
			eventPublisher.publishEvent(new OnAccessDocumentEvent(inbox.getId(), DocumentType.annonce, true));
		}
		return "homeMarketplaceAnnonce";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param token
	 * @param keyword
	 * @param location
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/evenements", method = RequestMethod.GET)
	public String showEvents(final Model model, final Authentication authentication, @RequestParam(name = "token", required = false) final String token,
			@RequestParam(name = "tag", required = false) final String keyword, @RequestParam(name = "secteur", required = false) final Integer sectorParam, 
			@RequestParam(name = "location", required = false) final Integer location,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication);
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final Integer sector = sectorParam != null && sectorParam >= 1 && sectorParam <= ConstraintesForm.COUNT_SECTOR_ACTIITY ? sectorParam : null;
		final Integer wilaya = location != null && location >= 1 && location <= ConstraintesForm.COUNT_WILAYA ? location : null;
		final Long userId = currentUser != null ? currentUser.getUserId() : null;
		model.addAttribute("desc", true);
		model.addAttribute("token", token);
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("searchEvent", new SearchEventForm(userId, token, keyword, sector, wilaya, currentConfig.parseDefaultResult()));
		model.addAttribute("skills", skillsService.readMarketplaceSkills());
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplaceEventsMapsiteURL());
		return "homeMarketplaceEvents";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param eventURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/evenements/{companyURL}/{eventURL}", method = RequestMethod.GET)
	public String showEvent(final HttpServletRequest request, final HttpServletResponse response, final Model model, final Authentication authentication, 
			@PathVariable("companyURL") final String companyURL, @PathVariable("eventURL") final String eventURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CompanyAutorMini company = searchDetailService.findCompanyAutorMini(companyURL);
		if(company == null) {
			return "redirect:/404";
		}
		final Long companyId = company.getId();
		final ScreenInboxEvent inbox = searchDetailService.readScreenInboxEvent(companyId, eventURL);
		if(inbox == null) {
			return "redirect:/404";
		}
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication, config);
		final DocumentContactForm documentContact = explorerVisitorService.readDocumentContactForm(currentUser, companyId, inbox.getId(), DocumentType.event);
		if(currentUser != null) {
			model.addAttribute("hasLeader", companyId.equals(currentUser.getCompanyId()));
			model.addAttribute("hasFavorite", favoriteService.hasFavoriteDocument(currentUser.getUserId(), inbox.getId(), DocumentType.event));
		} else {
			model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		}
		model.addAttribute("company", company);
		model.addAttribute("inbox", inbox);
		model.addAttribute("documentContact", documentContact);
		model.addAttribute("prospect", request.getParameter("prospect"));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplaceEventMapsiteURL(companyURL, eventURL));
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		updateLanguage(request, response, model, company.getLanguage());
		if(currentUser == null || !companyId.equals(currentUser.getCompanyId())) {
			eventPublisher.publishEvent(new OnAccessDocumentEvent(inbox.getId(), DocumentType.event, true));
		}
		return "homeMarketplaceEvent";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param token
	 * @param keyword
	 * @param location
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/offres-emploi", method = RequestMethod.GET)
	public String showEmployes(final Model model, final Authentication authentication, @RequestParam(name = "token", required = false) final String token,
			@RequestParam(name = "tag", required = false) final String keyword, @RequestParam(name = "secteur", required = false) final Integer sectorParam,  
			@RequestParam(name = "location", required = false) final Integer location,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication);
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final Integer domaine = sectorParam != null && sectorParam >= 1 && sectorParam <= ConstraintesForm.COUNT_DOMAINES ? sectorParam : null;
		final Integer wilaya = location != null && location >= 1 && location <= ConstraintesForm.COUNT_WILAYA ? location : null;
		final Long userId = currentUser != null ? currentUser.getUserId() : null;
		model.addAttribute("desc", true);
		model.addAttribute("token", token);
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("searchEmploye", new SearchEmployeForm(userId, token, keyword, domaine, wilaya, currentConfig.parseDefaultResult()));
		model.addAttribute("skills", skillsService.readMarketplaceSkills());
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplaceEmployesMapsiteURL());
		return "homeMarketplaceEmployes";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param companyURL
	 * @param employeURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/offres-emploi/{companyURL}/{employeURL}", method = RequestMethod.GET)
	public String showEmploye(final HttpServletRequest request, final HttpServletResponse response, final Model model, final Authentication authentication, 
			@PathVariable("companyURL") final String companyURL, @PathVariable("employeURL") final String employeURL, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CompanyAutorMini company = searchDetailService.findCompanyAutorMini(companyURL);
		if(company == null) {
			return "redirect:/404";
		}
		final Long companyId = company.getId();
		final ScreenInboxEmploye inbox = searchDetailService.readScreenInboxEmploye(companyId, employeURL);
		if(inbox == null) {
			return "redirect:/404";
		}
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication, config);
		final DocumentContactForm documentContact = explorerVisitorService.readDocumentContactForm(currentUser, companyId, inbox.getId(), DocumentType.employe);
		if(currentUser != null) {
			model.addAttribute("hasLeader", companyId.equals(currentUser.getCompanyId()));
			model.addAttribute("hasFavorite", favoriteService.hasFavoriteDocument(currentUser.getUserId(), inbox.getId(), DocumentType.employe));
		} else {
			model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		}
		model.addAttribute("company", company);
		model.addAttribute("inbox", inbox);
		model.addAttribute("documentContact", documentContact);
		model.addAttribute("prospect", request.getParameter("prospect"));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplaceEmployeMapsiteURL(companyURL, employeURL));
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		updateLanguage(request, response, model, company.getLanguage());
		if(currentUser == null || !companyId.equals(currentUser.getCompanyId())) {
			eventPublisher.publishEvent(new OnAccessDocumentEvent(inbox.getId(), DocumentType.employe, true));
		}
		return "homeMarketplaceEmploye";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param authentication
	 * @param token
	 * @param location
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/actualites", method = RequestMethod.GET)
	public String showActus(final HttpServletRequest request, final Model model, final Authentication authentication, 
			@RequestParam(name = "token", required = false) final String token, @RequestParam(name = "location", required = false) final Integer location, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication);
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final Integer wilaya = location != null && location >= 1 && location <= ConstraintesForm.COUNT_WILAYA ? location : null;
		final Long userId = currentUser != null ? currentUser.getUserId() : null;
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("searchNews", new SearchNewsForm(userId, token, wilaya, currentConfig.parseDefaultResult()));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplaceNewsMapsiteURL());
		return "homeMarketplaceActus";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param response
	 * @param model
	 * @param authentication
	 * @param actuId
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/actualites/{actuId}", method = RequestMethod.GET)
	public String showActu(final HttpServletRequest request, final HttpServletResponse response, final Model model, 
			final Authentication authentication, @PathVariable("actuId") final String actuId,  
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final ScreenInboxNews inbox = searchDetailService.findScreenInboxNews(actuId);
		if(inbox == null) {
			return "redirect:/404";
		}
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication, config);
		if(currentUser != null) {
			model.addAttribute("comment", new CommentForm(currentUser.getUserId(), actuId));
			model.addAttribute("hasLiked", likeService.hasLikeActu(currentUser.getUserId(), actuId));
		}
		model.addAttribute("inbox", inbox);
		model.addAttribute("info", request.getParameter("info"));
		model.addAttribute("mapsiteURL", ConstraintesURL.getMarketplaceNewMapsiteURL(actuId));
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		updateLanguage(request, response, model, inbox.getLanguage());
		return "homeMarketplaceActu";
	}
	
}
