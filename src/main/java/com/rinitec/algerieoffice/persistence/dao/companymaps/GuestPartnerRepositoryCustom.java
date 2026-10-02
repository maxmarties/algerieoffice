package com.rinitec.algerieoffice.persistence.dao.companymaps;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.company.communication.PartnerGuestLine;

public interface GuestPartnerRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllGuestPartnerCriteria(Long companyId, Integer filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<PartnerGuestLine> findAllGuestPartnerCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
