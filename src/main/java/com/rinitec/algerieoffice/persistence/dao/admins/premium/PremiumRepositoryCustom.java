package com.rinitec.algerieoffice.persistence.dao.admins.premium;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.modal.admins.premium.AdmPremiumLine;
import com.rinitec.algerieoffice.web.modal.company.tools.PremiumLine;

public interface PremiumRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllPremiumAdmin(Integer filter, String search);
	
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
	List<AdmPremiumLine> findAllPremiumAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<AdmPremiumLine> findPremiumAdminCompany(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	Long countAllPremiumCriteria(Long companyId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<PremiumLine> findAllPremiumCriteria(Long companyId, Integer filter, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param pass
	 * @return
	 */
	Long countAllActivePremium(Integer pass);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param pass
	 * @param begin
	 * @param end
	 * @param create
	 * @return
	 */
	Long countAllPremium(Integer pass, DateTime begin, DateTime end, boolean create);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<PremiumLine> findAllPremium(Long companyId);
	
}
