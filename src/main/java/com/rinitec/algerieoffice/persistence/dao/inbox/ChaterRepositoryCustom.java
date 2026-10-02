package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.communication.AdmChaterLine;
import com.rinitec.algerieoffice.web.modal.inbox.ChaterPush;

public interface ChaterRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param rows
	 * @return
	 */
	List<ChaterPush> findAllChatterCriteria(int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllChaterAdmin(Integer filter, String search);
	
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
	List<AdmChaterLine> findAllChaterAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
