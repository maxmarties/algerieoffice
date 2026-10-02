package com.rinitec.algerieoffice.persistence.dao.company.posts;

import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.result.CategoryMini;
import com.rinitec.algerieoffice.persistence.result.PostMini;
import com.rinitec.algerieoffice.persistence.result.UUIDMini;
import com.rinitec.algerieoffice.web.modal.company.posts.CategoryLine;

public interface CategoryRepositoryCustom {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param categoryId
	 * @return
	 */
	List<UUIDMini> findAllChoseCategoryMini(Long companyId, UUID categoryId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllFilterCategoryMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<UUIDMini> findAllCategoryMini(Long companyId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllCategoryCriteria(Long companyId, String filter, String search);
	
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
	List<CategoryLine> findAllCategoryCriteria(Long companyId, String filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param parentUUID
	 * @return
	 */
	List<CategoryMini> findAllExplorerCategory(Long companyId, UUID parentUUID);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param identify
	 * @return
	 * @throws Exception
	 */
	UUIDMini findExplorerCategory(Long companyId, String identify) throws Exception;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	List<PostMini> findAllPingledCategoryMini(Long companyId);
	
}
