package com.rinitec.algerieoffice.persistence.dao.company.profile;

import com.rinitec.algerieoffice.web.modal.analytic.ChartCompaniesCapital;

public interface CompanyBriefcaseRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @param type
	 * @return
	 */
	Long countByTypeForSector(Integer sector, Integer wilaya, int type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @param briefcase
	 * @return
	 */
	Long countByBriefcaseForSector(Integer sector, Integer wilaya, int briefcase);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @param warehouse
	 * @return
	 */
	Long countByWarehouseForSector(Integer sector, Integer wilaya, boolean warehouse);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesCapital readChartCompaniesCapital(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @param type
	 * @return
	 */
	Long countByTypeForActivity(String code, Integer wilaya, int type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesCapital readChartCompaniesCapital(String code, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @param briefcase
	 * @return
	 */
	Long countByBriefcaseForActivity(String code, Integer wilaya, int briefcase);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @param warehouse
	 * @return
	 */
	Long countByWarehouseForActivity(String code, Integer wilaya, boolean warehouse);
	
}
