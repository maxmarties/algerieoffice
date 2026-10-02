package com.rinitec.algerieoffice.persistence.dao.company.marketplace;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.form.search.SearchEmployeForm;
import com.rinitec.algerieoffice.web.modal.admins.marketplace.AdmEmployeLine;
import com.rinitec.algerieoffice.web.modal.company.marketplace.EmployeLine;
import com.rinitec.algerieoffice.web.modal.company.newsletter.NewsletterItem;
import com.rinitec.algerieoffice.web.modal.company.tools.RecycleEmployeLine;
import com.rinitec.algerieoffice.web.modal.mapsite.EmployeMapsite;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.EmployeSimultudeMini;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.employes.EmployeWidgetMini;

public interface EmployeRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllEmployeCriteria(Long companyId, Integer filter, String search);
	
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
	List<EmployeLine> findAllEmployeCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param search
	 * @return
	 */
	Long countAllExplorerEmployeCriteria(Long companyId, String search);
	
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
	List<Object[]> findAllExplorerEmployeCriteria(Long companyId, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEmployeForm
	 * @return
	 */
	Long countAllEmployeWidget(SearchEmployeForm searchEmployeForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEmployeForm
	 * @return
	 */
	List<EmployeWidgetMini> findEmployeWidgetList(SearchEmployeForm searchEmployeForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param searchEmployeForm
	 * @return
	 */
	List<UUID> findAllEmployeEasylist(SearchEmployeForm searchEmployeForm);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param employe
	 * @param domaine
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<EmployeSimultudeMini> findEmployeProxisList(Employe employe, Integer domaine, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param employeId
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<EmployeSimultudeMini> findEmployeSourcesList(UUID employeId, Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllAdmEmployeCriteria(Integer filter, String search);
	
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
	List<AdmEmployeLine> findAllAdmEmployeCriteria(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllRecycleEmployeCriteria(Long companyId, Integer filter, String search);
	
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
	List<RecycleEmployeLine> findAllRecycleEmployeCriteria(Long companyId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	Long countPublishedEmploye(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * USED FOR MARKETPLACE AND MAPSITE
	 * @return
	 */
	Long countAllActiveEmploye();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param page
	 * @param limit
	 * @return
	 */
	List<EmployeMapsite> findAllPostMapsite(int page, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param published
	 * @return
	 */
	Long countAllEmploye(Integer filter, boolean published);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllEmployeNewsletterMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param lines
	 * @return
	 */
	List<NewsletterItem> findAllNewsletterItem(Long companyId, List<UUID> lines);
	
}
