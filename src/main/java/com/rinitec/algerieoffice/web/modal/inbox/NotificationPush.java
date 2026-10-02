package com.rinitec.algerieoffice.web.modal.inbox;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.inbox.Notification;
import com.rinitec.algerieoffice.persistence.result.UserMini;

public class NotificationPush {

	private final List<UserMini> users;
	private final Notification notification;
	
	public NotificationPush(final List<UserMini> users, final Notification notification) {
		this.users = users;
		this.notification = notification;
	}

	public List<UserMini> getUsers() {
		return users;
	}

	public Notification getNotification() {
		return notification;
	}

	@Override
	public String toString() {
		return "NotificationPush [users=" + users + ", notification=" + notification + "]";
	}
	
}
