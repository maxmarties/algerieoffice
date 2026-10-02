package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmAssistLine;

public interface AssistRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @return
	 */
	Long countAllAssistCriteria(String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<AdmAssistLine> findAllAssistCriteria(String search, int sort, int rows, int page, boolean hasDesc);
	
}
