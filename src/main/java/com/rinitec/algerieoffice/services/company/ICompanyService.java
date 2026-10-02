package com.rinitec.algerieoffice.services.company;

public interface ICompanyService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 */
	void incrementVisits(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	String findCompanymail(Long companyId);
	
}
