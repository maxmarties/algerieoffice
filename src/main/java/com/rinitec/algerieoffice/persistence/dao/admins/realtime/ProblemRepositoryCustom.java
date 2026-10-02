package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.admins.realtime.AdmProblemLine;

public interface ProblemRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllProblemCriteria(Integer filter, String search);
	
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
	List<AdmProblemLine> findAllProblemCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
