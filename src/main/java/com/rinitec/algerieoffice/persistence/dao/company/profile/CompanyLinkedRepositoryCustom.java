package com.rinitec.algerieoffice.persistence.dao.company.profile;

public interface CompanyLinkedRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param provider
	 * @return
	 */
	Long countSocial(String provider);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countWebsite();
	
}
