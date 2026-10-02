package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmPromoteLine;
import com.rinitec.algerieoffice.web.modal.company.marketplace.PromoteLine;
import com.rinitec.algerieoffice.web.modal.company.tools.RecyclePromoteLine;

public interface PromoteRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllPromoteCriteria(Long companyId, Long filter, String search);
	
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
	List<PromoteLine> findAllPromoteCriteria(Long companyId, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UserMini> findAllAutorCriteria(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllPromoteAdmin(Boolean filter, String search);
	
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
	List<AdmPromoteLine> findAllPromoteAdmin(Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	Promote findPromoteExplorer(Long companyId) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 * @throws Exception
	 */
	Promote findPromoteHome() throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @return
	 */
	Long countAllRecyclePromoteCriteria(Long companyId, String search);
	
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
	List<RecyclePromoteLine> findAllRecyclePromoteCriteria(Long companyId, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	Long countAllActivePromote();
	
}
