package com.rinitec.algerieoffice.services.company.help;

import com.rinitec.algerieoffice.web.modal.company.dashboard.DashboardTask;
import com.rinitec.algerieoffice.web.modal.company.help.BegginerTask;

public interface ITaskHelpService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	BegginerTask readBegginerTask(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	DashboardTask readDashboardTask(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	int countPersentCompleted(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countAllActiveCompanies();
	
}
