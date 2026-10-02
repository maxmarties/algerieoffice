package com.rinitec.algerieoffice.persistence.dao.users.alerts;

import java.util.List;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.web.modal.user.alerts.AlertPostLine;
import com.rinitec.algerieoffice.web.modal.user.alerts.AlertPostResult;

public interface AlertPostRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param type
	 * @return
	 */
	Long countAllAlertPostCriteria(Long userId, Integer filter, String search, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @param type
	 * @return
	 */
	List<AlertPostLine> findAllAlertPostCriteria(Long userId, Integer filter, String search, int sort, int rows, int page, boolean hasDesc, DocumentType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param type
	 * @return
	 */
	Object[] countAlertPostUserByType(Long userId, Integer type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param type
	 * @param day
	 * @param limit
	 * @return
	 */
	List<AlertPostResult> findLastAlertPost(DocumentType type, int day, int limit);
	
}
