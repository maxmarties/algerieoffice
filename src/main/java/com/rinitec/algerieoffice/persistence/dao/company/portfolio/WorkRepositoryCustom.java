package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.company.portfolio.WorkLine;

public interface WorkRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllWorkCriteria(Long companyId, String filter, String search);
	
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
	List<WorkLine> findAllWorkCriteria(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @return
	 */
	Long countAllExplorerWorkCriteria(Long companyId, String search);
	
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
	List<Object[]> findAllExplorerWorkCriteria(Long companyId, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countAllActiveWork();
	
}
