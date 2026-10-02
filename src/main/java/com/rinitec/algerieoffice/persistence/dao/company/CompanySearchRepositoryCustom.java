package com.rinitec.algerieoffice.persistence.dao.company;

import java.util.List;

import com.rinitec.algerieoffice.web.form.search.SearchCompanyForm;
import com.rinitec.algerieoffice.web.form.search.SearchCompanyQuickly;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyLogoMini;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanySimultudeLine;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyWidgetB2C;
import com.rinitec.algerieoffice.web.modal.publics.companies.CompanyWidgetMini;

public interface CompanySearchRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	Long countAllCompanySimultude(Long companyId, Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param sector
	 * @param wilaya
	 * @param limit
	 * @return
	 */
	List<CompanySimultudeLine> findAllCompanySimultude(Long companyId, Integer sector, Integer wilaya, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @param search
	 * @return
	 */
	Long countAllCompanyWidget(String code, Integer wilaya, String search);
	
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
	List<CompanyWidgetMini> findCompanyWidgetList(Long userId, String code, Integer wilaya, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @param search
	 * @return
	 */
	List<Long> findAllCompanyEasylist(String code, Integer wilaya, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchCompanyForm
	 * @return
	 */
	Long countAllCompanyWidget(SearchCompanyForm searchCompanyForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchCompanyForm
	 * @return
	 */
	List<CompanyWidgetMini> findCompanyWidgetList(SearchCompanyForm searchCompanyForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchCompanyForm
	 * @return
	 */
	List<Long> findAllCompanyEasylist(SearchCompanyForm searchCompanyForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchCompanyQuickly
	 * @return
	 */
	Long countAllCompanyWidgetB2C(SearchCompanyQuickly searchCompanyQuickly);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchCompanyQuickly
	 * @return
	 */
	List<CompanyWidgetB2C> findCompanyWidgetB2CList(SearchCompanyQuickly searchCompanyQuickly);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param wilaya
	 * @param limit
	 * @return
	 */
	List<CompanyWidgetMini> findCompanyWidgetListWithToken(Long userId, String search, Integer wilaya, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param wilaya
	 * @param limit
	 * @return
	 */
	List<CompanyWidgetMini> findCompanyWidgetListWithAgent(Long userId, String search, Integer wilaya, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param wilaya
	 * @param limit
	 * @return
	 */
	List<CompanyWidgetMini> findCompanyWidgetListWithKeyword(Long userId, String search, Integer wilaya, int limit);
	
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
