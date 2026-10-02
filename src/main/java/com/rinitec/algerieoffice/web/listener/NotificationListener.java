package com.rinitec.algerieoffice.web.listener;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.enums.NotificationType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Notification;
import com.rinitec.algerieoffice.persistence.result.CompanyLive;
import com.rinitec.algerieoffice.services.inbox.INotificationPuchService;
import com.rinitec.algerieoffice.services.inbox.INotificationService;
import com.rinitec.algerieoffice.services.user.IUserService;
import com.rinitec.algerieoffice.services.user.alerts.IAlertSettingService;
import com.rinitec.algerieoffice.ujson.NotificationResponse;
import com.rinitec.algerieoffice.web.listener.events.OnAlertEvent;
import com.rinitec.algerieoffice.web.listener.events.OnCommentEvent;
import com.rinitec.algerieoffice.web.listener.events.OnDetectEvent;
import com.rinitec.algerieoffice.web.listener.events.OnLiveEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationEvent;
import com.rinitec.algerieoffice.web.listener.events.OnNotificationsEvent;
import com.rinitec.algerieoffice.web.listener.events.OnReplyEvent;
import com.rinitec.algerieoffice.web.modal.inbox.NotificationPush;

@Component
public class NotificationListener {

	private MessageSource messages;
	private IUserService userService;
	private SimpMessagingTemplate messagingTemplate;
	private INotificationService notificationService;
	private INotificationPuchService notificationPuchService;
	private IAlertSettingService alertSettingService;
	
	@Autowired
	public NotificationListener(MessageSource messages, IUserService userService, SimpMessagingTemplate messagingTemplate, INotificationService notificationService, 
			INotificationPuchService notificationPuchService, IAlertSettingService alertSettingService) {
		this.messages = messages;
		this.userService = userService;
		this.messagingTemplate = messagingTemplate;
		this.notificationService = notificationService;
		this.notificationPuchService = notificationPuchService;
		this.alertSettingService = alertSettingService;
	}
	
	private final void pushAndSendNotification(final Long fromId, final Long toId, final UUID uuid, 
			final NotificationType type, final Locale locale) {
		final NotificationPush notificationPush = notificationPuchService.readNotificationPush(fromId, toId, uuid, type);
		final List<Notification> notifications = notificationService.addNotifications(notificationPush);
		for (int i = 0; i < notifications.size(); i++) {
			final Notification notification = notifications.get(i);
			final String email = notificationPush.getUsers().get(i).getEmail();
			final NotificationResponse notificationResponse = new NotificationResponse(notification, 
					messages.getMessage(notification.getMessage(), null, locale), locale);
			messagingTemplate.convertAndSendToUser(email, "/queue/notification", notificationResponse);
		}
	}
	
	private final boolean hasNotifiedUser(final Long userId, final NotificationType type) {
		switch(type) {
		case detectVisit: case alertPost: case favoriteAccount: case accessProfile: 
			return alertSettingService.hasNotificationSetting(userId, type);
		default: return true;
		}
	}
	
	@Async
	@EventListener
	public void pushNotification(final OnNotificationEvent event) {
		if(hasNotifiedUser(event.getToId(), event.getType())) {
			pushAndSendNotification(event.getFromId(), event.getToId(), event.getUuid(), event.getType(), event.getLocale());
		}
	}
	
	@Async
	@EventListener
	public void pushNotifications(final OnNotificationsEvent event) {
		for (final Long toId : event.getTosId()) {
			if(hasNotifiedUser(toId, event.getType())) {
				pushAndSendNotification(event.getFromId(), toId, event.getUuid(), event.getType(), event.getLocale());
			}
		}
	}
	
	@Async
	@EventListener
	public void pushDetectNotification(final OnDetectEvent event) {
		if(event.isHasDetect()) {
			final List<Long> usersId = userService.findAllIdByCompany(event.getToId());
			for (final Long userId : usersId) {
				if(hasNotifiedUser(userId, NotificationType.detectVisit)) {
					pushAndSendNotification(event.getFromId(), userId, null, NotificationType.detectVisit, event.getLocale());
				}
			}
		} else if(hasNotifiedUser(event.getToId(), NotificationType.accessProfile)) {
			pushAndSendNotification(event.getFromId(), event.getToId(), null, NotificationType.accessProfile, event.getLocale());
		}
	}
	
	private final void sendNotificationPush(final NotificationPush notificationPush, final Locale locale) {
		if(notificationPush != null) {
			final List<Notification> notifications = notificationService.addNotifications(notificationPush);
			for (int i = 0; i < notifications.size(); i++) {
				final Notification notification = notifications.get(i);
				final String email = notificationPush.getUsers().get(i).getEmail();
				final NotificationResponse notificationResponse = new NotificationResponse(notification, 
						messages.getMessage(notification.getMessage(), null, locale), locale);
				messagingTemplate.convertAndSendToUser(email, "/queue/notification", notificationResponse);
			}
		}
	}
	
	@Async
	@EventListener
	public void pushNotificationLive(final OnLiveEvent event) {
		final CompanyLive companyLive = notificationPuchService.findCompanyLive(event.getCompanyId());
		if(companyLive != null) {
			final NotificationPush notificationPush = notificationPuchService.readNotificationPushLive(companyLive, event.getIdentify(), event.getType());
			sendNotificationPush(notificationPush, event.getLocale());
		}
	}
	
	@Async
	@EventListener
	public void pushNotificationComment(final OnCommentEvent event) {
		final NotificationPush notificationPush = notificationPuchService.readNotificationPushComment(event);
		final NotificationPush notificationPushLive = notificationPuchService.readNotificationPushCommentLive(event);
		sendNotificationPush(notificationPush, event.getLocale());
		sendNotificationPush(notificationPushLive, event.getLocale());
	}
	
	@Async
	@EventListener
	public void pushNotificationAlert(final OnAlertEvent event) {
		if(hasNotifiedUser(event.getAlert().getUserId(), NotificationType.alertPost)) {
			final NotificationPush notificationPush = notificationPuchService.readNotificationPushAlert(event);
			final List<Notification> notifications = notificationService.addNotifications(notificationPush);
			for (int i = 0; i < notifications.size(); i++) {
				final Notification notification = notifications.get(i);
				final String email = notificationPush.getUsers().get(i).getEmail();
				final NotificationResponse notificationResponse = new NotificationResponse(notification, 
						messages.getMessage(notification.getMessage(), null, event.getLocale()), event.getLocale());
				messagingTemplate.convertAndSendToUser(email, "/queue/notification", notificationResponse);
			}
		}
	}

	@Async
	@EventListener
	public void pushTopicComment(final OnReplyEvent event) {
		final NotificationPush notificationReply = notificationPuchService.readNotificationPushReply(event);
		final NotificationPush notificationReplies = notificationPuchService.readNotificationPushReplies(event);
		sendNotificationPush(notificationReply, event.getLocale());
		sendNotificationPush(notificationReplies, event.getLocale());
	}
	
}
