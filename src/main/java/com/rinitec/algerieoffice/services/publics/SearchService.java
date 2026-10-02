package com.rinitec.algerieoffice.services.publics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.company.CompanyRepository;
import com.rinitec.algerieoffice.persistence.dao.company.CompanySearchRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.AnnonceRepository;
import com.rinitec.algerieoffice.persistence.dao.company.marketplace.EmployeRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.ActualityRepository;
import com.rinitec.algerieoffice.persistence.dao.company.portfolio.EventRepository;
import com.rinitec.algerieoffice.persistence.dao.company.posts.PostRepository;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.search.SearchAnnonceForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyQuickly;
import com.rinitec.algerieoffice.web.form.search.SearchEmployeForm;
import com.rinitec.algerieoffice.web.form.search.SearchEventForm;
import com.rinitec.algerieoffice.web.form.search.SearchNewsForm;
import com.rinitec.algerieoffice.web.form.search.SearchPostForm;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompaniesWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyLogoMini;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanySimultudeLine;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanySimultudesList;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyWidgetB2C;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyWidgetMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.AnnonceWidgetMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.AnnoncesWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.EmployeWidgetMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.EmployesWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.EventWidgetMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.EventsWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.NewsWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.NewsWidgetMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostWidgetMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostsWidgetList;

@Service
public class SearchService implements ISearchService {

	private CompanyRepository companyRepository;
	private CompanySearchRepository companySearchRepository;
	private AnnonceRepository annonceRepository;
	private EmployeRepository employeRepository;
	private EventRepository eventRepository;
	private PostRepository postRepository;
	private ActualityRepository actualityRepository;
	
	@Autowired
	public SearchService(CompanyRepository companyRepository, CompanySearchRepository companySearchRepository, AnnonceRepository annonceRepository, 
			EmployeRepository employeRepository, EventRepository eventRepository, PostRepository postRepository, ActualityRepository actualityRepository) {
		this.companyRepository = companyRepository;
		this.companySearchRepository = companySearchRepository;
		this.annonceRepository = annonceRepository;
		this.employeRepository = employeRepository;
		this.eventRepository = eventRepository;
		this.postRepository = postRepository;
		this.actualityRepository = actualityRepository;
	}
	
	@Transactional
	private final void incrementSimultudes(final List<CompanySimultudeLine> lines) {
		if(!lines.isEmpty()) {
			final List<Long> linesId = new ArrayList<Long>();
			for (final CompanySimultudeLine line : lines) {
				linesId.add(line.getCompanyId());
			}
			companySearchRepository.incrementSimultudes(linesId);
		}
	}
	
	@Override
	@Transactional
	public CompanySimultudesList findCompanySimultudesList(final Long companyId, final int limit) {
		final Company company = companyRepository.findById(companyId).get();
		final Integer sector = ((Activity) company.getActivities().toArray()[0]).getSector();
		final Integer wilaya = ((CompanyAddress) company.getAddresses().toArray()[0]).getWilaya();
		final Long countLines = companySearchRepository.countAllCompanySimultude(companyId, sector, wilaya);
		final List<CompanySimultudeLine> lines = countLines == 0L ? new ArrayList<CompanySimultudeLine>() 
				: companySearchRepository.findAllCompanySimultude(companyId, sector, wilaya, limit);
		incrementSimultudes(lines);
		return new CompanySimultudesList(wilaya, countLines, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CompaniesWidgetList findCompaniesWidgetList(final Long userId, final String code, final Integer wilaya, final String search, 
			final int sort, final int rows, final int page, final boolean hasDesc) {
		final Long countResult = companySearchRepository.countAllCompanyWidget(code, wilaya, search);
		final List<CompanyWidgetMini> lines = countResult == 0L ? new ArrayList<CompanyWidgetMini>() 
				: companySearchRepository.findCompanyWidgetList(userId, code, wilaya, search, sort, rows, page, hasDesc);
		return new CompaniesWidgetList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CompaniesWidgetList findCompaniesWidgetList(final SearchCompanyForm searchCompanyForm) {
		final Long countResult = companySearchRepository.countAllCompanyWidget(searchCompanyForm);
		final List<CompanyWidgetMini> lines = countResult == 0L ? new ArrayList<CompanyWidgetMini>() 
				: companySearchRepository.findCompanyWidgetList(searchCompanyForm);
		return new CompaniesWidgetList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CompaniesWidgetList findCompaniesWidgetB2CList(final SearchCompanyQuickly searchCompanyQuickly) {
		final Long countResult = companySearchRepository.countAllCompanyWidgetB2C(searchCompanyQuickly);
		final List<CompanyWidgetB2C> lines = countResult == 0L ? new ArrayList<CompanyWidgetB2C>() 
				: companySearchRepository.findCompanyWidgetB2CList(searchCompanyQuickly);
		return new CompaniesWidgetList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CompaniesWidgetList findCompaniesWidgetListWithToke(final Long userId, final String search, final Integer wilaya, final int limit) {
		final List<CompanyWidgetMini> lines = companySearchRepository.findCompanyWidgetListWithToken(userId, search, wilaya, limit);
		return new CompaniesWidgetList(0L, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CompaniesWidgetList findCompaniesWidgetListWithAgent(final Long userId, final String search, final Integer wilaya, final int limit) {
		final List<CompanyWidgetMini> lines = companySearchRepository.findCompanyWidgetListWithAgent(userId, search, wilaya, limit);
		return new CompaniesWidgetList(0L, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CompaniesWidgetList findCompaniesWidgetListWithKeyword(final Long userId, final String search, final Integer wilaya, final int limit) {
		final List<CompanyWidgetMini> lines = companySearchRepository.findCompanyWidgetListWithKeyword(userId, search, wilaya, limit);
		return new CompaniesWidgetList(0L, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PostsWidgetList findPostsWidgetList(final SearchPostForm searchPostForm) {
		final Long countResult = postRepository.countAllPostWidget(searchPostForm);
		final List<PostWidgetMini> lines = countResult == 0L ? new ArrayList<PostWidgetMini>() 
				: postRepository.findPostWidgetList(searchPostForm);
		return new PostsWidgetList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnnoncesWidgetList findAnnoncesWidgetList(final SearchAnnonceForm searchAnnonceForm) {
		final Long countResult = annonceRepository.countAllAnnonceWidget(searchAnnonceForm);
		final List<AnnonceWidgetMini> lines = countResult == 0L ? new ArrayList<AnnonceWidgetMini>() 
				: annonceRepository.findAnnonceWidgetList(searchAnnonceForm);
		return new AnnoncesWidgetList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public EmployesWidgetList findEmployesWidgetList(final SearchEmployeForm searchEmployeForm) {
		final Long countResult = employeRepository.countAllEmployeWidget(searchEmployeForm);
		final List<EmployeWidgetMini> lines = countResult == 0L ? new ArrayList<EmployeWidgetMini>() 
				: employeRepository.findEmployeWidgetList(searchEmployeForm);
		return new EmployesWidgetList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public EventsWidgetList findEventsWidgetList(final SearchEventForm searchEventForm) {
		final Long countResult = eventRepository.countAllEventWidget(searchEventForm);
		final List<EventWidgetMini> lines = countResult == 0L ? new ArrayList<EventWidgetMini>() 
				: eventRepository.findEventWidgetList(searchEventForm);
		return new EventsWidgetList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public NewsWidgetList findNewsWidgetList(final SearchNewsForm searchNewsForm) {
		final Long countResult = actualityRepository.countAllNewsWidget(searchNewsForm);
		final List<NewsWidgetMini> lines = countResult == 0L ? new ArrayList<NewsWidgetMini>() 
				: actualityRepository.findNewsWidgetList(searchNewsForm);
		return new NewsWidgetList(countResult, lines);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CompanyLogoMini> findLastCompanyLogoMini(final int limit) {
		return companySearchRepository.findLastCompanyLogoMini(limit);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CompanyLogoMini> findLastCompanyPremiumMini(final int limit) {
		return companySearchRepository.findLastCompanyPremiumMini(limit);
	}
	
}
