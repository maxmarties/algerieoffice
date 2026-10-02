package com.rinitec.algerieoffice.ujson;

import java.io.Serializable;
import java.util.Locale;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.inbox.Notification;

public class NotificationResponse implements Serializable {
	private static final long serialVersionUID = 6842625250938346040L;
	
	private final boolean hasIcon;
	private final String name;
	private final String icon;
	private final String message;
	private final String link;
	private final String date;
	private final String cmsms;
	
	public NotificationResponse(final Notification notification, final String message, final Locale locale) {
		this.hasIcon = notification.getHasIcon();
		this.name = notification.getNotifiedname();
		this.icon = notification.getIconimage();
		this.message = message;
		this.link = "/inbox/notification/href?uuid=".concat(notification.getId().toString());
		this.date = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm:ss").withLocale(locale).print(notification.getNotifiedDate());
		this.cmsms = notification.getCmsms();
	}

	public boolean isHasIcon() {
		return hasIcon;
	}

	public String getName() {
		return name;
	}

	public String getIcon() {
		return icon;
	}

	public String getMessage() {
		return message;
	}

	public String getLink() {
		return link;
	}

	public String getDate() {
		return date;
	}
	
	public String getCmsms() {
		return cmsms;
	}

	@Override
	public String toString() {
		return "NotificationResponse [hasIcon=" + hasIcon + ", name=" + name + ", icon=" + icon + ", message=" + message
				+ ", link=" + link + ", date=" + date + ", cmsms=" + cmsms + "]";
	}
	
}
