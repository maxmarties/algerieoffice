package com.rinitec.algerieoffice.persistence.dao.analytic;

import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticAutentified;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardDetect;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DetectAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DetectLine;

public interface AccessCompanyRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllDetectCriteria(Long companyId, Integer filter, String search);

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<DetectLine> findAllDetectCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<DetectAccess> findDetectAccessListCriteria(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countAccessCompany(Long companyId, DateTime begin, DateTime end);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param type
	 * @param filter
	 * @return
	 */
	Long countAccessCompanyByType(Long companyId, Integer type, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param web
	 * @param filter
	 * @return
	 */
	Long countAccessCompanyByWeb(Long companyId, boolean web, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param authentified
	 * @param filter
	 * @return
	 */
	Long countAccessCompanyByAuthentified(Long companyId, boolean authentified, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<AnalyticAutentified> findAllAnalyticAutentified(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<DashboardDetect> findLastDashboardDetect(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param begin
	 * @param end
	 * @return
	 */
	Long countAccessUser(Long userId, DateTime begin, DateTime end);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param begin
	 * @param end
	 * @param authentified
	 * @return
	 */
	Long countAllAccess(DateTime begin, DateTime end, boolean authentified);
	
}
