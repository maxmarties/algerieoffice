package com.rinitec.algerieoffice.web.modal.user.feedback;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.inbox.Notification;

public class NotificationLine implements Serializable {
	private static final long serialVersionUID = -5218839350417171028L;
	
	private final String id;
	private final String icon;
	private final String name;
	private final String message;
	private final String link;
	private final String noitifiedDate;
	private final String cmscs;
	private final boolean hasIcon;
	private final boolean consulted;
	
	public NotificationLine(final Notification notification) {
		this.id = notification.getId().toString();
		this.icon = notification.getIconimage();
		this.name = notification.getNotifiedname();
		this.message = notification.getMessage();
		this.link = notification.getLink();
		this.noitifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(notification.getNotifiedDate());
		this.cmscs = notification.getCmsms();
		this.hasIcon = notification.getHasIcon();
		this.consulted = notification.getConsulted();
	}

	public String getId() {
		return id;
	}

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	public String getLink() {
		return link;
	}

	public String getNoitifiedDate() {
		return noitifiedDate;
	}

	public String getCmscs() {
		return cmscs;
	}

	public boolean isHasIcon() {
		return hasIcon;
	}

	public boolean isConsulted() {
		return consulted;
	}

	@Override
	public String toString() {
		return "NotificationLine [id=" + id + ", icon=" + icon + ", name=" + name + ", message=" + message + ", link="
				+ link + ", noitifiedDate=" + noitifiedDate + ", cmscs=" + cmscs + ", hasIcon=" + hasIcon
				+ ", consulted=" + consulted + "]";
	}

}
