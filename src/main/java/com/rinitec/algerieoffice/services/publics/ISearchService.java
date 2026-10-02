package com.rinitec.algerieoffice.services.publics;

import java.util.List;

import com.rinitec.algerieoffice.web.form.search.SearchAnnonceForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyQuickly;
import com.rinitec.algerieoffice.web.form.search.SearchEmployeForm;
import com.rinitec.algerieoffice.web.form.search.SearchEventForm;
import com.rinitec.algerieoffice.web.form.search.SearchNewsForm;
import com.rinitec.algerieoffice.web.form.search.SearchPostForm;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompaniesWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyLogoMini;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanySimultudesList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces.AnnoncesWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.EmployesWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.events.EventsWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.news.NewsWidgetList;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.posts.PostsWidgetList;

public interface ISearchService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	CompanySimultudesList findCompanySimultudesList(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param code
	 * @param wilaya
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	CompaniesWidgetList findCompaniesWidgetList(Long userId, String code, Integer wilaya, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchCompanyForm
	 * @return
	 */
	CompaniesWidgetList findCompaniesWidgetList(SearchCompanyForm searchCompanyForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchCompanyQuickly
	 * @return
	 */
	CompaniesWidgetList findCompaniesWidgetB2CList(SearchCompanyQuickly searchCompanyQuickly);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param wilaya
	 * @param limit
	 * @return
	 */
	CompaniesWidgetList findCompaniesWidgetListWithToke(Long userId, String search, Integer wilaya, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param wilaya
	 * @param limit
	 * @return
	 */
	CompaniesWidgetList findCompaniesWidgetListWithAgent(Long userId, String search, Integer wilaya, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param wilaya
	 * @param limit
	 * @return
	 */
	CompaniesWidgetList findCompaniesWidgetListWithKeyword(Long userId, String search, Integer wilaya, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchPostForm
	 * @return
	 */
	PostsWidgetList findPostsWidgetList(SearchPostForm searchPostForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchAnnonceForm
	 * @return
	 */
	AnnoncesWidgetList findAnnoncesWidgetList(SearchAnnonceForm searchAnnonceForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEmployeForm
	 * @return
	 */
	EmployesWidgetList findEmployesWidgetList(SearchEmployeForm searchEmployeForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEventForm
	 * @return
	 */
	EventsWidgetList findEventsWidgetList(SearchEventForm searchEventForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchNewsForm
	 * @return
	 */
	NewsWidgetList findNewsWidgetList(SearchNewsForm searchNewsForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<CompanyLogoMini> findLastCompanyLogoMini(int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param limit
	 * @return
	 */
	List<CompanyLogoMini> findLastCompanyPremiumMini(int limit);
	
}
