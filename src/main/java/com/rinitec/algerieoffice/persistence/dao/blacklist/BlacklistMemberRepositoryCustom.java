package com.rinitec.algerieoffice.persistence.dao.blacklist;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.company.tools.BlacklistLine;

public interface BlacklistMemberRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @return
	 */
	Long countAllBlacklistCriteria(Long userId, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<BlacklistLine> findAllBlacklistCriteria(Long userId, String search, int sort, int rows, int page, boolean hasDesc);
	
}
