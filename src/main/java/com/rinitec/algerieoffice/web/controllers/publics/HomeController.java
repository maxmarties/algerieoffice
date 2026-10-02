package com.rinitec.algerieoffice.web.controllers.publics;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rinitec.algerieoffice.captcha.ICaptchaService;
import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Contactus;
import com.rinitec.algerieoffice.services.IAttributeService;
import com.rinitec.algerieoffice.services.admins.blog.IBlogSearchService;
import com.rinitec.algerieoffice.services.admins.realtime.IContactusService;
import com.rinitec.algerieoffice.services.analytic.ISkillsService;
import com.rinitec.algerieoffice.services.explorer.IExplorerPromoteService;
import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.utils.FilesUtil;
import com.rinitec.algerieoffice.utils.RequestUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.form.register.ContactsForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyQuickly;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.modal.CurrentGeolocate;
import com.rinitec.algerieoffice.web.modal.CurrentUser;
import com.rinitec.algerieoffice.web.modal.admins.CurrSocialFooter;

@Controller
public class HomeController {

	private IAttributeService attributeService;
	private ICaptchaService captchaService;
	private ISkillsService skillsService;
	private IBlogSearchService blogSearchService;
	private IContactusService contactusService;
	private IExplorerPromoteService explorerPromoteService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public HomeController(IAttributeService attributeService, ICaptchaService captchaService, ISkillsService skillsService, IBlogSearchService blogSearchService, 
			IContactusService contactusService, IExplorerPromoteService explorerPromoteService, ApplicationEventPublisher eventPublisher) {
		this.attributeService = attributeService;
		this.captchaService = captchaService;
		this.skillsService = skillsService;
		this.blogSearchService = blogSearchService;
		this.contactusService = contactusService;
		this.explorerPromoteService = explorerPromoteService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String home(final HttpServletRequest request, final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config, 
			@CookieValue(value = RequestUtil.COOKIE_GEOLOCATE, defaultValue = "") final String geolocate) {
		final CurrSocialFooter currSocial = attributeService.attributeCurrSocial(model, authentication, config);
		model.addAttribute("hasLoading", true);
		model.addAttribute("logoutSucc", request.getParameter("logoutSucc"));
		model.addAttribute("currGeo", new CurrentGeolocate(geolocate));
		//model.addAttribute("homeBlogs", blogSearchService.findLastBlogHomeMini(3));//home2
		model.addAttribute("list", blogSearchService.findExplorerBlogMini(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.URL_APPLICATION);
		if(currSocial.isPromoted()) {
			model.addAttribute("skills", skillsService.readOfficeSkills());
		}
		return "homeIndex";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postHome() {
		return "redirect:/";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param token
	 * @param keyword
	 * @param wilayaURL
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/recherche/entreprises", method = RequestMethod.GET)
	public String showSearchCompanies(final Model model, final Authentication authentication, @RequestParam(name = "token", required = false) final String token, 
			@RequestParam(name = "tag", required = false) final String keyword, @RequestParam(name = "location", required = false) final String wilayaURL,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication);
		final CurrentConfig currentConfig = new CurrentConfig(config);
		final Integer wilaya = !StringUtils.isEmpty(wilayaURL) ? ConstraintesURL.getIndexWilaya(wilayaURL) : null;
		model.addAttribute("desc", true);
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("searchCompany", new SearchCompanyForm(currentUser != null ? currentUser.getUserId() : null, 
				token, keyword, wilaya, currentConfig.parseDefaultResult()));
		model.addAttribute("marketBlogs", blogSearchService.findExplorerBlogMini(5));
		model.addAttribute("mapsiteURL", ConstraintesURL.getSearchCompaniesMapsiteURL());
		return "homeSearchCompanies";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param token
	 * @param location
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/recherche/entreprise", method = RequestMethod.GET)
	public String showSearchCompany(final Model model, final Authentication authentication, @RequestParam(name = "token", required = false) final String token, 
			@RequestParam(name = "location", required = false) final Integer location,
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		if(StringUtils.isEmpty(token)) {
			return "redirect:/recherche/entreprises";
		}
		attributeService.attributeAuthentified(model, authentication, config);
		final Integer wilaya = location != null && location >= 1 && location <= ConstraintesForm.COUNT_WILAYA ? location : null;
		model.addAttribute("token", token.replaceAll("\\+", " "));
		model.addAttribute("wilaya", wilaya);
		model.addAttribute("marketBlogs", blogSearchService.findExplorerBlogMini(5));
		model.addAttribute("mapsiteURL", ConstraintesURL.getSearchCompanyMapsiteURL(token, wilaya));
		return "homeSearchCompany";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/trouver-vite", method = RequestMethod.GET)
	public String showSearchQuickly(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication);
		final CurrentConfig currentConfig = new CurrentConfig(config);
		model.addAttribute("desc", true);
		model.addAttribute("currentConfig", currentConfig);
		model.addAttribute("searchQuickly", new SearchCompanyQuickly(currentUser != null ? currentUser.getUserId() : null, currentConfig.parseDefaultResult()));
		model.addAttribute("marketBlogs", blogSearchService.findExplorerBlogMini(5));
		model.addAttribute("sponsoreScreen", explorerPromoteService.readSponsoreFeedback(3));
		model.addAttribute("mapsiteURL", ConstraintesURL.getSearchQuicklyMapsiteURL());
		return "homeSearchQuickly";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param authentication
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
	public String showContacts(final Model model, final Authentication authentication, 
			@CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		final CurrentUser currentUser = attributeService.attributeAuthentified(model, authentication, config);
		model.addAttribute("contact", new ContactsForm(currentUser != null ? currentUser.getUserId() : null));
		model.addAttribute("mapsiteURL", ConstraintesURL.getContactsMapsiteURL());
		model.addAttribute("recaptchaSiteKey", captchaService.getReCaptchaSite());
		return "homeContact";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param request
	 * @param contactsForm
	 * @return
	 */
	@RequestMapping(value = "/contacts/save", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse saveContacts(final HttpServletRequest request, @Valid final ContactsForm contactsForm) {
		final String response = request.getParameter("g-recaptcha-response");
		captchaService.processResponse(response);
		final Contactus contactus = contactusService.addContactus(contactsForm);
		eventPublisher.publishEvent(new OnNotificationEvent(null, null, contactus.getId(), NotificationType.contact, request));
		return new GenericResponse("success");
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String showError(final Model model, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeNotfound(model, config);
		return "error";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/404/entreprises", method = RequestMethod.GET)
	public String showCompanyError(final Model model, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeNotfound(model, config);
		model.addAttribute("errorState", "4");
		return "error";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/404/membres", method = RequestMethod.GET)
	public String showMemberError(final Model model, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeNotfound(model, config);
		model.addAttribute("errorState", "5");
		return "error";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String showDeniedAccess(final Model model, @CookieValue(value = RequestUtil.COOKIE_CONFIG, defaultValue = "") final String config) {
		attributeService.attributeNotfound(model, config);
		model.addAttribute("errorAccess", "1");
		model.addAttribute("errorState", "6");
		return "error";
	}
	
	/**
	 * AUTORITY IGNORED_RESOURCE
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = {"/robots.txt", "/robot.txt"})
	@ResponseBody
	public String robots(final HttpServletResponse response) throws IOException {
		response.addHeader("Content-Disposition", "inline;filename=\"robot.txt\"");
		response.setContentType("text/plain");
		try {
			final File file = new ClassPathResource("robot.txt").getFile();
			FilesUtil.downloadFile(file, response);
		} catch (IOException e) {
			final String robot = ""
					+ "User-agent: *" + System.lineSeparator()
					+ "Disallow: /users/" + System.lineSeparator()
					+ "Disallow: /website/" + System.lineSeparator() 
					+ "Disallow: /admin/" + System.lineSeparator() 
					+ "Disallow: /company/" + System.lineSeparator() 
					+ "Disallow: /user/" + System.lineSeparator() 
					+ "Disallow: /guest/" + System.lineSeparator() 
					+ "Disallow: /inbox/" + System.lineSeparator() 
					+ "Disallow: /proxy/" + System.lineSeparator() 
					+ "Disallow: /document/" + System.lineSeparator() 
					+ "Disallow: /envelope/" + System.lineSeparator()
					+ "Disallow: /config/" + System.lineSeparator()
					+ "Disallow: /explorer/" + System.lineSeparator()
					+ "Disallow: /register/" + System.lineSeparator() 
					+ "Disallow: /feedback/*" + System.lineSeparator() 
					+ "Disallow: /cookie-config/*" + System.lineSeparator() 
					+ "Allow: /" + System.lineSeparator() 
					+ "Sitemap: https://www.algerieoffice.net/mapsite/static.xml" + System.lineSeparator() 
					+ "Sitemap: https://www.algerieoffice.net/mapsite/entreprises.xml" + System.lineSeparator() 
					+ "Sitemap: https://www.algerieoffice.net/mapsite/produits.xml" + System.lineSeparator() 
					+ "Sitemap: https://www.algerieoffice.net/mapsite/annonces.xml" + System.lineSeparator() 
					+ "Sitemap: https://www.algerieoffice.net/mapsite/evenements.xml" + System.lineSeparator() 
					+ "Sitemap: https://www.algerieoffice.net/mapsite/offres-emploi.xml" + System.lineSeparator()
					+ "Sitemap: https://www.algerieoffice.net/mapsite/actualites.xml" + System.lineSeparator()
					+ "Sitemap: https://www.algerieoffice.net/mapsite/blogs.xml";
			response.getWriter().print(robot);
			response.getWriter().flush();
		}
		return null;
	}
	
}
