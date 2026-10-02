package com.rinitec.algerieoffice.services.inbox;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.inbox.Notification;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.inbox.NotificationPush;

public interface INotificationService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	Integer countNotification(Long userId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param notificationPush
	 * @return
	 */
	List<Notification> addNotifications(NotificationPush notificationPush);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean hasAllConsulted(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Notification> findAllNotification(Long userId, Integer page, Integer rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void resetInboxNotification(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void updateAllConsulted(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param notificationId
	 * @return
	 */
	String getLinkAndConsultedNotification(Long userId, String notificationId);
	
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
	ElementsList findNotificationList(Long userId, Boolean filter, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void consultNotification(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteNotification(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteNotifications(List<String> lines, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void deleteAllNotifications(Long userId);
	
}
