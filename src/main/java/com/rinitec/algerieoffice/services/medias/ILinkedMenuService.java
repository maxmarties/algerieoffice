package com.rinitec.algerieoffice.services.medias;

import java.util.List;

import com.rinitec.algerieoffice.persistence.result.PostMini;

public interface ILinkedMenuService {
	public static final int LINKED_COMPANYPAGE = 1;
	public static final int LINKED_POST = 2;
	public static final int LINKED_ANNONCE = 3;

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	boolean hasCompanyPublished(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	String getUrlCompany(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param linkType
	 * @return
	 */
	List<PostMini> findChoseLinked(Long companyId, int linkType);
	
}
