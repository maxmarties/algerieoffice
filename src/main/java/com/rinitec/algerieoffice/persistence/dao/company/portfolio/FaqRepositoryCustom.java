package com.rinitec.algerieoffice.persistence.dao.company.portfolio;

import java.util.List;

import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.company.portfolio.FaqLine;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetFaq;

public interface FaqRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllFaqCriteria(Long companyId, Long filter, String search);
	
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
	List<FaqLine> findAllFaqCriteria(Long companyId, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllAutorCriteria(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @return
	 */
	Long countAllExplorerFaqCriteria(Long companyId, String search);
	
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
	List<ExplorerWidgetFaq> findAllExplorerFaqCriteria(Long companyId, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countAllActiveFaq();
	
}
