package com.rinitec.algerieoffice.web.controllers.feedback;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rinitec.algerieoffice.services.admins.blog.IBlogSearchService;
import com.rinitec.algerieoffice.services.publics.IMemberService;
import com.rinitec.algerieoffice.services.publics.ISearchDetailService;
import com.rinitec.algerieoffice.services.publics.ISearchService;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.search.SearchAnnonceForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyQuickly;
import com.rinitec.algerieoffice.web.form.search.SearchEmployeForm;
import com.rinitec.algerieoffice.web.form.search.SearchEventForm;
import com.rinitec.algerieoffice.web.form.search.SearchMemberForm;
import com.rinitec.algerieoffice.web.form.search.SearchNewsForm;
import com.rinitec.algerieoffice.web.form.search.SearchPostForm;
import com.rinitec.algerieoffice.web.listener.events.OnAccessBlogEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReferringCompanyEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReferringPostEvent;
import com.rinitec.algerieoffice.web.modal.publics.blog.BlogMarketMini;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompaniesWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.AnnoncesWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.EmployesWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.EventsWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.NewsWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostsWidgetList;

@Controller
@RequestMapping(value = "/feedback/screen")
public class FeedbakScreenController {

	private ISearchService searchService;
	private ISearchDetailService searchDetailService;
	private IBlogSearchService blogSearchService;
	private IMemberService memberService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public FeedbakScreenController(ISearchService searchService, ISearchDetailService searchDetailService, 
			IBlogSearchService blogSearchService, IMemberService memberService, 
			ApplicationEventPublisher eventPublisher) {
		this.searchService = searchService;
		this.searchDetailService = searchDetailService;
		this.blogSearchService = blogSearchService;
		this.memberService = memberService;
		this.eventPublisher = eventPublisher;
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param code
	 * @param wilaya
	 * @param ch
	 * @param sort
	 * @param rows
	 * @param page
	 * @param desc
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/companies-sector", method = RequestMethod.GET)
	public String loadSectorCompanies(final Model model, @RequestParam("cd") final String code, @RequestParam("wl") final Integer wilaya, 
			@RequestParam("ch") final String ch, @RequestParam("sr") final Integer sort, @RequestParam("rw") final Integer rows, 
			@RequestParam("pg") final Integer page, @RequestParam("dsc") final String desc, @RequestParam("id") final Long userId) {
		final boolean hasDesc = desc.equals("true");
		final String search = StringUtils.isEmpty(ch) ? null : ch.replaceAll("\\+", " ");
		final CompaniesWidgetList companiesList = searchService.findCompaniesWidgetList(userId, code, wilaya, search, sort, rows, page, hasDesc);
		if(!companiesList.isEmpty()) {
			if(!StringUtils.isEmpty(search)) {
				eventPublisher.publishEvent(new OnReferringCompanyEvent(companiesList.getCompaniesId(), true, false, false));
			} else if(userId == null && page == 1) {
				model.addAttribute("indexEmptyCompany", ParseUtil.getRandomValue(companiesList.getLines().size()));
			}
		}
		model.addAttribute("list", companiesList);
		return "screenSectorCompanies";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param searchCompanyForm
	 * @return
	 */
	@RequestMapping(value = "/companies-search", method = RequestMethod.POST)
	public String loadSearchCompanies(final Model model, @Valid final SearchCompanyForm searchCompanyForm) {
		final CompaniesWidgetList companiesList = searchService.findCompaniesWidgetList(searchCompanyForm);
		if(!companiesList.isEmpty()) {
			if(searchCompanyForm.hasBeginReaden()) {
				model.addAttribute("indexEmptyCompany", ParseUtil.getRandomValue(companiesList.getLines().size()));
			}
			eventPublisher.publishEvent(new OnReferringCompanyEvent(companiesList.getCompaniesId(), searchCompanyForm.hasPresentToken(),
					searchCompanyForm.hasPresentFilter(), searchCompanyForm.hasPresentKeysword()));
		}
		model.addAttribute("list", companiesList);
		return "screenSearchCompanies";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param searchCompanyQuickly
	 * @return
	 */
	@RequestMapping(value = "/companies-b2c", method = RequestMethod.POST)
	public String loadSearchCompaniesB2C(final Model model, @Valid final SearchCompanyQuickly searchCompanyQuickly) {
		final CompaniesWidgetList companiesList = searchService.findCompaniesWidgetB2CList(searchCompanyQuickly);
		if(!companiesList.isEmpty()) {
			if(searchCompanyQuickly.hasBeginReaden()) {
				model.addAttribute("indexEmptyCompany", ParseUtil.getRandomValue(companiesList.getLines().size()));
			}
			eventPublisher.publishEvent(new OnReferringCompanyEvent(companiesList.getCompaniesB2CId(), searchCompanyQuickly.hasPresentToken(),
					searchCompanyQuickly.hasPresentFilter(), false));
		}
		model.addAttribute("list", companiesList);
		return "screenSearchB2C";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param ch
	 * @param wilaya
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/companies-token", method = RequestMethod.GET)
	public String loadTokenCompanies(final Model model, @RequestParam(name = "ch", required = true) final String ch, 
			@RequestParam(name = "wl", required = false) final Integer wilaya, @RequestParam(name = "id", required = false) final Long userId) {
		final String search = ch.replaceAll("\\+", " ");
		final CompaniesWidgetList companiesList = searchService.findCompaniesWidgetListWithToke(userId, search, wilaya, 30);
		if(!companiesList.isEmpty()) {
			eventPublisher.publishEvent(new OnReferringCompanyEvent(companiesList.getCompaniesId(), true, false, false));
		}
		model.addAttribute("list", companiesList);
		return "screenSearchCompany";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param ch
	 * @param wilaya
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/companies-agent", method = RequestMethod.GET)
	public String loadAgentCompanies(final Model model, @RequestParam(name = "ch", required = true) final String ch, 
			@RequestParam(name = "wl", required = false) final Integer wilaya, @RequestParam(name = "id", required = false) final Long userId) {
		final String search = ch.replaceAll("\\+", " ");
		final CompaniesWidgetList companiesList = searchService.findCompaniesWidgetListWithAgent(userId, search, wilaya, 30);
		if(!companiesList.isEmpty()) {
			eventPublisher.publishEvent(new OnReferringCompanyEvent(companiesList.getCompaniesId(), false, true, false));
		}
		model.addAttribute("list", companiesList);
		return "screenSearchCompany";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param ch
	 * @param wilaya
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/companies-keyword", method = RequestMethod.GET)
	public String loadKeywordCompanies(final Model model, @RequestParam(name = "ch", required = true) final String ch, 
			@RequestParam(name = "wl", required = false) final Integer wilaya, @RequestParam(name = "id", required = false) final Long userId) {
		final String search = ch.replaceAll("\\+", " ");
		final CompaniesWidgetList companiesList = searchService.findCompaniesWidgetListWithKeyword(userId, search, wilaya, 30);
		if(!companiesList.isEmpty()) {
			eventPublisher.publishEvent(new OnReferringCompanyEvent(companiesList.getCompaniesId(), false, false, true));
		}
		model.addAttribute("list", companiesList);
		return "screenSearchCompany";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/companies-logo", method = RequestMethod.GET)
	public String loadSearchCompanylogo(final Model model) {
		model.addAttribute("list", searchService.findLastCompanyLogoMini(10));
		return "screenSearchCompanylogo";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param searchPostForm
	 * @return
	 */
	@RequestMapping(value = "/posts-search", method = RequestMethod.POST)
	public String loadSearchPosts(final Model model, @Valid final SearchPostForm searchPostForm) {
		final PostsWidgetList postsWidgetList = searchService.findPostsWidgetList(searchPostForm);
		if(!postsWidgetList.isEmpty()) {
			if(searchPostForm.hasBeginReaden()) {
				final BlogMarketMini blogMarketMini = blogSearchService.findOneBlogMarketMini();
				if(blogMarketMini != null) {
					model.addAttribute("blogMarket", blogMarketMini);
					model.addAttribute("indexMarket", ParseUtil.getRandomValue(postsWidgetList.getLines().size()));
					eventPublisher.publishEvent(new OnAccessBlogEvent(blogMarketMini.getId(), false));
				}
			}
			eventPublisher.publishEvent(new OnReferringPostEvent(postsWidgetList.getPostsId(), searchPostForm.hasPresentToken(),
					searchPostForm.hasPresentFilter(), searchPostForm.hasPresentKeysword()));
		}
		model.addAttribute("currentItem", searchPostForm.getCurrItem());
		model.addAttribute("list", postsWidgetList);
		return "screenMarketplacePosts";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyId
	 * @param documentId
	 * @return
	 */
	@RequestMapping(value = "/posts-simultude", method = RequestMethod.GET)
	public String loadSimultudePosts(final Model model, @RequestParam("companyId") final Long companyId, 
			@RequestParam("documentId") final String documentId) {
		model.addAttribute("list", searchDetailService.findPostsSimilarList(documentId, companyId, 3));
		return "screenSimilarPosts";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param searchAnnonceForm
	 * @return
	 */
	@RequestMapping(value = "/annonces-search", method = RequestMethod.POST)
	public String loadSearchAnnonces(final Model model, @Valid final SearchAnnonceForm searchAnnonceForm) {
		final AnnoncesWidgetList annoncesWidgetList = searchService.findAnnoncesWidgetList(searchAnnonceForm);
		if(!annoncesWidgetList.isEmpty() && searchAnnonceForm.hasBeginReaden()) {
			final BlogMarketMini blogMarketMini = blogSearchService.findOneBlogMarketMini();
			if(blogMarketMini != null) {
				model.addAttribute("blogMarket", blogMarketMini);
				model.addAttribute("indexMarket", ParseUtil.getRandomValue(annoncesWidgetList.getLines().size()));
				eventPublisher.publishEvent(new OnAccessBlogEvent(blogMarketMini.getId(), false));
			}
		}
		model.addAttribute("currentItem", searchAnnonceForm.getCurrItem());
		model.addAttribute("list", annoncesWidgetList);
		return "screenMarketplaceAnnonces";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyId
	 * @param documentId
	 * @return
	 */
	@RequestMapping(value = "/annonces-simultude", method = RequestMethod.GET)
	public String loadSimultudeAnnonces(final Model model, @RequestParam("companyId") final Long companyId, 
			@RequestParam("documentId") final String documentId) {
		model.addAttribute("docState", 2);
		model.addAttribute("list", searchDetailService.findAnnoncesSimultudeList(documentId, companyId, 5));
		return "screenSimultudeDocuments";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param searchEventForm
	 * @return
	 */
	@RequestMapping(value = "/events-search", method = RequestMethod.POST)
	public String loadSearchEvenets(final Model model, @Valid final SearchEventForm searchEventForm) {
		final EventsWidgetList eventsWidgetList = searchService.findEventsWidgetList(searchEventForm);
		if(!eventsWidgetList.isEmpty() && searchEventForm.hasBeginReaden()) {
			final BlogMarketMini blogMarketMini = blogSearchService.findOneBlogMarketMini();
			if(blogMarketMini != null) {
				model.addAttribute("blogMarket", blogMarketMini);
				model.addAttribute("indexMarket", ParseUtil.getRandomValueMod(eventsWidgetList.getLines().size()));
				eventPublisher.publishEvent(new OnAccessBlogEvent(blogMarketMini.getId(), false));
			}
		}
		model.addAttribute("currentItem", searchEventForm.getCurrItem());
		model.addAttribute("list", eventsWidgetList);
		return "screenMarketplaceEvents";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyId
	 * @param documentId
	 * @return
	 */
	@RequestMapping(value = "/events-simultude", method = RequestMethod.GET)
	public String loadSimultudeEvents(final Model model, @RequestParam("companyId") final Long companyId, 
			@RequestParam("documentId") final String documentId) {
		model.addAttribute("docState", 3);
		model.addAttribute("list", searchDetailService.findEventsSimultudeList(documentId, companyId, 5));
		return "screenSimultudeDocuments";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param searchEmployeForm
	 * @return
	 */
	@RequestMapping(value = "/employes-search", method = RequestMethod.POST)
	public String loadSearchEmployes(final Model model, @Valid final SearchEmployeForm searchEmployeForm) {
		final EmployesWidgetList employesWidgetList = searchService.findEmployesWidgetList(searchEmployeForm);
		if(!employesWidgetList.isEmpty() && searchEmployeForm.hasBeginReaden()) {
			final BlogMarketMini blogMarketMini = blogSearchService.findOneBlogMarketMini();
			if(blogMarketMini != null) {
				model.addAttribute("blogMarket", blogMarketMini);
				model.addAttribute("indexMarket", ParseUtil.getRandomValue(employesWidgetList.getLines().size()));
				eventPublisher.publishEvent(new OnAccessBlogEvent(blogMarketMini.getId(), false));
			}
		}
		model.addAttribute("currentItem", searchEmployeForm.getCurrItem());
		model.addAttribute("list", employesWidgetList);
		return "screenMarketplaceEmployes";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param companyId
	 * @param documentId
	 * @return
	 */
	@RequestMapping(value = "/employes-simultude", method = RequestMethod.GET)
	public String loadSimultudeEmployes(final Model model, @RequestParam("companyId") final Long companyId, 
			@RequestParam("documentId") final String documentId) {
		model.addAttribute("docState", 4);
		model.addAttribute("list", searchDetailService.findEmployesSimultudeList(documentId, companyId, 5));
		return "screenSimultudeDocuments";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param searchNewsForm
	 * @return
	 */
	@RequestMapping(value = "/news-search", method = RequestMethod.POST)
	public String loadSearchNews(final Model model, @Valid final SearchNewsForm searchNewsForm) {
		final NewsWidgetList newsWidgetList = searchService.findNewsWidgetList(searchNewsForm);
		if(!newsWidgetList.isEmpty() && searchNewsForm.hasBeginReaden()) {
			final BlogMarketMini blogMarketMini = blogSearchService.findOneBlogMarketMini();
			if(blogMarketMini != null) {
				model.addAttribute("blogMarket", blogMarketMini);
				model.addAttribute("indexMarket", ParseUtil.getRandomValue(newsWidgetList.getLines().size()));
				eventPublisher.publishEvent(new OnAccessBlogEvent(blogMarketMini.getId(), false));
			}
		}
		model.addAttribute("currentPage", searchNewsForm.getPage());
		model.addAttribute("currentItem", searchNewsForm.getCurrItem());
		model.addAttribute("list", newsWidgetList);
		return "screenMarketplaceNews";
	}
	
	/**
	 * AUTORITY PERMIT_ALL
	 * VERSION BEGIN 03/2021
	 * @param model
	 * @param searchMemberForm
	 * @return
	 */
	@RequestMapping(value = "/members-search", method = RequestMethod.POST)
	public String loadSearchMembers(final Model model, @Valid final SearchMemberForm searchMemberForm) {
		model.addAttribute("currentPage", searchMemberForm.getPage());
		model.addAttribute("list", memberService.findMembersWidgetList(searchMemberForm));
		return "screenSearchMembers";
	}
	
}
