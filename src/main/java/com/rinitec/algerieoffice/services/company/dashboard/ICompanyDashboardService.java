package com.rinitec.algerieoffice.services.company.dashboard;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticAutentified;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticSearch;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardData;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardDetect;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardEvaluation;
import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardJournal;

public interface ICompanyDashboardService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticSearch readAnalyticSearch(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<AnalyticAutentified> findAnalyticAutentifiedList(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param published
	 * @return
	 */
	List<DashboardData> findDashboardDataList(Long companyId, boolean published);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<DashboardJournal> findLastDashboardJournal(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<DashboardDetect> findLastDashboardDetect(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<DashboardEvaluation> findLastDashboardEvaluation(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	String countFormattedFavoriteCompany(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AdmAnalyticAccess readAnalyticAccess(Long companyId);
	
}
