package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.user.feedback.NotificationLine;

public interface NotificationRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @return
	 */
	Long countAllNotificationCriteria(Long userId, Boolean filter);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param filter
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<NotificationLine> findAllNotificationCriteria(Long userId, Boolean filter, int sort, int rows, int page, boolean hasDesc);
	
}
