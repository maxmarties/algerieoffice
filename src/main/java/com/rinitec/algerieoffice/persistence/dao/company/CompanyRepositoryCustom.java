package com.rinitec.algerieoffice.persistence.dao.company;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.result.CompanyAvatar;
import com.rinitec.algerieoffice.persistence.result.CompanyLive;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.CurrentCompany;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyCustomer;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyFeature;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyLine;
import com.rinitec.algerieoffice.web.modal.admins.companies.AdmCompanyProfile;
import com.rinitec.algerieoffice.web.modal.company.CurrentCommunication;
import com.rinitec.algerieoffice.web.modal.company.CurrentProspect;
import com.rinitec.algerieoffice.web.modal.mapsite.CompanyMapsite;
import com.rinitec.algerieoffice.web.modal.publics.CompanyAutorMini;

public interface CompanyRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	Long checkCompanyPublished(Long companyId) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	CurrentCompany getCurrentCompany(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 * @throws Exception
	 */
	Long getCompanyIdExplorer(String url) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	CompanyAvatar readCompanyAvatar(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	CompanyLive readCompanyLive(Long companyId) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param url
	 * @return
	 * @throws Exception
	 */
	CompanyAutorMini findCompanyAutorMini(String url) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	CurrentCommunication readCurrentCommunication(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	CurrentProspect readCurrentProspect(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	Long countCompaniesForSector(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countCompaniesByBuildForSector(Integer sector, Integer wilaya, DateTime begin, DateTime end);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param region
	 * @return
	 */
	Long countCompaniesByRegionForSector(Integer sector, int region);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @return
	 */
	Long countCompaniesForActivity(String code, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countCompaniesByBuildForActivity(String code, Integer wilaya, DateTime begin, DateTime end);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param region
	 * @return
	 */
	Long countCompaniesByRegionForActivity(String code, int region);	
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	Object[] findCompanyHrefFromUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	Object[] findCompanyInfoFromUserId(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllCompanyAdmin(Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmCompanyLine> findAllCompanyAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmCompanyProfile> findAllCompanyProfileAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmCompanyFeature> findAllCompanyFeatureAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllCompanyB2CAdmin(Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmCompanyCustomer> findAllCompanyB2CAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countAllCompanyForMapsite();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param limit
	 * @return
	 */
	List<CompanyMapsite> findAllCompanyMapsite(int page, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param completed
	 * @return
	 */
	Long countAllActiveCompanyCompleted(int completed);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param begin
	 * @param end
	 * @param create
	 * @return
	 */
	Long countAllCompany(DateTime begin, DateTime end, boolean create);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	List<UserMini> findAllNewsletterCompany(Long companyId, Integer sector, Integer wilaya);
	
}
