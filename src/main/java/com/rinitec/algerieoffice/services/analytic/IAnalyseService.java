package com.rinitec.algerieoffice.services.analytic;

import com.rinitec.algerieoffice.web.modal.analytic.ChartCompaniesCapital;
import com.rinitec.algerieoffice.web.modal.analytic.ChartCompaniesDetail;
import com.rinitec.algerieoffice.web.modal.analytic.ChartCompaniesType;

public interface IAnalyseService {

	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesType(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesAgent(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesCapital readChartCompaniesCapital(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesBuild(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesBriefcase(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesMonth(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesWarhouse(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesEffectif(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesStrict(Integer sector, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param sector
	 * @return
	 */
	ChartCompaniesType readChartCompaniesRegion(Integer sector);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesType(String code, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesAgent(String code, Integer wilaya);
	
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
	 * @return
	 */
	ChartCompaniesType readChartCompaniesBuild(String code, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesBriefcase(String code, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesMonth(String code, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesWarhouse(String code, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesEffectif(String code, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @param wilaya
	 * @return
	 */
	ChartCompaniesType readChartCompaniesStrict(String code, Integer wilaya);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param code
	 * @return
	 */
	ChartCompaniesType readChartCompaniesRegion(String code);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	ChartCompaniesDetail readChartCompaniesDetail();
	
}
