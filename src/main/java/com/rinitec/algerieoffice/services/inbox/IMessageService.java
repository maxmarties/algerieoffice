package com.rinitec.algerieoffice.services.inbox;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.result.UserMini;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.modal.admins.ElementsList;
import com.rinitec.algerieoffice.web.modal.inbox.MessageInfo;
import com.rinitec.algerieoffice.web.modal.inbox.MessageNotification;
import com.rinitec.algerieoffice.web.modal.inbox.MessagePopup;
import com.rinitec.algerieoffice.web.modal.inbox.MessagePush;

public interface IMessageService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	Integer countMessages(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @return
	 */
	boolean hasAllConsulted(Long userId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param senderId
	 * @param recepientId
	 * @return
	 */
	String findEmailRecepient(Long senderId, Long recepientId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param messagePush
	 * @return
	 */
	Message addMessage(MessagePush messagePush);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param recepientId
	 * @return
	 */
	MessageInfo readMessageInfo(Long userId, Long recepientId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param recepientId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<MessagePopup> findAllMessagePopup(Long userId, Long recepientId, int page, int rows);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param messageId
	 */
	void updateConsulted(Long userId, String messageId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 */
	void updateAllConsulted(Long userId);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param senderId
	 */
	void updateAllConsulted(Long userId, Long senderId);
	
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
	List<UserMini> findAllUserMessage(Long userId, boolean recevied);
	
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
	ElementsList findMessagesList(Long userId, boolean recevied, Long filter, String search, int sort, int rows, int page, boolean hasDesc);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void consultMessage(String id, Long userId) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param id
	 * @param userId
	 * @param recevied
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteMessage(String id, Long userId, boolean recevied) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param lines
	 * @param userId
	 * @param recevied
	 * @throws NotFoundException
	 * @throws InvalidResourceException
	 */
	void deleteMessages(List<String> lines, Long userId, boolean recevied) throws NotFoundException, InvalidResourceException;
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param userId
	 * @param recevied
	 */
	void deleteAllMessages(Long userId, boolean recevied);
	
}
