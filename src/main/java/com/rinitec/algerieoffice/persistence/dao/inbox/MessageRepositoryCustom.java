package com.rinitec.algerieoffice.persistence.dao.inbox;

import java.util.List;

import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.modal.admins.communication.AdmMessageLine;
import com.rinitec.algerieoffice.web.modal.inbox.MessageNotification;
import com.rinitec.algerieoffice.web.modal.inbox.MessagePopup;
import com.rinitec.algerieoffice.web.modal.user.dashboard.DashboardMessage;
import com.rinitec.algerieoffice.web.modal.user.feedback.MessageLine;

public interface MessageRepositoryCustom {

	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param recepientId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<MessagePopup> findAllMessagePopupCriteria(Long userId, Long recepientId, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<MessageNotification> findAllMessageNotification(Long userId, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param recevied
	 * @return
	 */
	List<UserMini> findAllUserMessageCriteria(Long userId, boolean recevied);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param recevied
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllMessageCriteria(Long userId, boolean recevied, Long filter, String search);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param recevied
	 * @param filter
	 * @param search
	 * @param sort
	 * @param rows
	 * @param page
	 * @param hasDesc
	 * @return
	 */
	List<MessageLine> findAllMessageCriteria(Long userId, boolean recevied, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param limit
	 * @return
	 */
	List<DashboardMessage> findLastDashboardMessage(Long userId, int limit);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param filter
	 * @param search
	 * @return
	 */
	Long countAllMessageAdmin(Integer filter, String search);
	
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
	List<AdmMessageLine> findAllMessageAdmin(Integer filter, String search, int sort, int rows, int page, boolean hasDesc);
	
}
