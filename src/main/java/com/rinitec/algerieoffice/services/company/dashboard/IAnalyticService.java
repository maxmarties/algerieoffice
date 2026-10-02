package com.rinitec.algerieoffice.services.company.dashboard;

import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticMarket;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticAccess;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticActivity;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticContact;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticEvaluation;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticFavorite;
import com.rinitec.algerieoffice.web.modal.company.dashboard.AnalyticStats;
import com.rinitec.algerieoffice.web.modal.company.posts.AnalyticPost;

public interface IAnalyticService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	AnalyticFavorite readAnalyticFavorite(Long companyId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	AnalyticFavorite readAnalyticDocument(Long companyId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	AnalyticAccess readAnalyticAccess(Long companyId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticActivity readAnalyticActivity(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @return
	 */
	AnalyticEvaluation readAnalyticEvaluation(Long companyId, Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticStats readAnalyticStatsContact(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticStats readAnalyticStatsDocument(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticStats readAnalyticStatsPost(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticPost readAnalyticPostActivity(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticActivity readCompanyDashboardTrafic(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticContact readCompanyCommunication(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticContact readCompanyProspect(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AnalyticContact readCompanyStatistic(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	AdmAnalyticMarket readCompanyMarket(Long companyId);
	
}
