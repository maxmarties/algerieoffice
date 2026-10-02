package com.rinitec.algerieoffice.services.admins.dashboard;

import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticAccess;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticJournal;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticMarket;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticPremium;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticVisit;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmDashboardLinked;

public interface IAdminAnalyticService {

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[][] findDashboardMarketplace();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[] findDashboardFeedback();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[][] findDashboardContent();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmDashboardLinked findAdmDashboardLinked();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[] findDashboardAdmin();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[] findDashboardFavorite();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmAnalyticAccess readAdmAnalyticAccess();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmAnalyticPremium readAdmAnalyticPremium();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmAnalyticVisit readAdmAnalyticVisit();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @return
	 */
	AdmAnalyticMarket readAdmAnalyticMarket(Integer filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmAnalyticAccess readAdmAnalyticAnalyse();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmAnalyticJournal readAdmAnalyticJournal();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmAnalyticJournal readAdmAnalyticGuest();
	
}
