package com.rinitec.algerieoffice.services.admins.dashboard;

import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticCompany;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticLogin;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmAnalyticUser;
import com.rinitec.algerieoffice.web.modal.admins.dashboard.AdmDashboardInbox;

public interface IAdminDashboardService {

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[][] findDashboardData();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[] findDashboardPremium();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[] findDashboardMedia();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[] findDashboardAlert();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[] findDashboardJournal();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String[] findDashboardDatakey();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmDashboardInbox findAdmDashboardInbox();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmAnalyticLogin findAdmAnalyticLogin();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmAnalyticCompany findAdmAnalyticCompany();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	AdmAnalyticUser findAdmAnalyticUser();
	
}
