package com.rinitec.algerieoffice.persistence.dao.admins.ads;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Sponsore;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.SponsoreLine;

public interface SponsoreRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllSponsoreCriteria(Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<SponsoreLine> findAllSponsoreCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param type
	 * @return
	 */
	Sponsore findSponsoreExplorer(Integer type);
	
}
