package com.rinitec.algerieoffice.services.inbox;

import java.util.UUID;

import com.rinitec.algerieoffice.enums.LiveType;
import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.result.CompanyLive;
import com.rinitec.algerieoffice.web.listener.events.OnAlertEvent;
import com.rinitec.algerieoffice.web.listener.events.OnCommentEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReplyEvent;
import com.rinitec.algerieoffice.web.modal.inbox.NotificationPush;

public interface INotificationPuchService {
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyId
	 * @return
	 */
	CompanyLive findCompanyLive(Long companyId);

	/**
	 * VERSION BEGIN 03/2021
	 * @param fromId
	 * @param toId
	 * @param uuid
	 * @param type
	 * @return
	 */
	NotificationPush readNotificationPush(Long fromId, Long toId, UUID uuid, NotificationType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	NotificationPush readNotificationPushComment(OnCommentEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	NotificationPush readNotificationPushCommentLive(OnCommentEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	NotificationPush readNotificationPushReply(OnReplyEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	NotificationPush readNotificationPushReplies(OnReplyEvent event);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param companyLive
	 * @param identify
	 * @param type
	 * @return
	 */
	NotificationPush readNotificationPushLive(CompanyLive companyLive, String identify, LiveType type);
	
	/**
	 * VERSION BEGIN 03/2021
	 * @param event
	 * @return
	 */
	NotificationPush readNotificationPushAlert(OnAlertEvent event);
	
}
