package com.rinitec.algerieoffice.persistence.dao.company.posts;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.company.posts.PostAccess;
import com.rinitec.algerieoffice.web.modal.company.posts.PostStatLine;

public interface PostSearchRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllPostStatCriteria(Long companyId, Boolean filter, String search);
	
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
	List<PostStatLine> findAllPostStatCriteria(Long companyId, Boolean filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param limit
	 * @return
	 */
	List<PostAccess> findPostAccessList(Long companyId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @param attribut
	 * @param service
	 * @return
	 */
	Long countPostStatistic(Long companyId, String attribut, boolean service);
	
}
