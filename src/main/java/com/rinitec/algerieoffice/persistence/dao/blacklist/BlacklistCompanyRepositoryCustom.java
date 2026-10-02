package com.rinitec.algerieoffice.persistence.dao.blacklist;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.company.tools.BlacklistLine;

public interface BlacklistCompanyRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @return
	 */
	Long countAllBlacklistCriteria(Long companyId, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<BlacklistLine> findAllBlacklistCriteria(Long companyId, String search, int sort, int rows, int page, boolean hasDesc);
	
}
