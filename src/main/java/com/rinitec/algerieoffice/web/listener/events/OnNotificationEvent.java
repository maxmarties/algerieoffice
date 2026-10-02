package com.rinitec.algerieoffice.web.listener.events;

import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.enums.NotificationType;

public class OnNotificationEvent {

	private final Long fromId;
	private final Long toId;
	private final UUID uuid;
	private final Locale locale;
	private final NotificationType type;
	
	public OnNotificationEvent(final Long fromId, final Long toId, final UUID uuid, final NotificationType type, final HttpServletRequest request) {
		this.fromId = fromId;
		this.toId = toId;
		this.uuid = uuid;
		this.type = type;
		this.locale = RequestContextUtils.getLocale(request);
	}
	
	public Long getFromId() {
		return fromId;
	}

	public Long getToId() {
		return toId;
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public Locale getLocale() {
		return locale;
	}

	public NotificationType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "OnNotificationEvent [fromId=" + fromId + ", toId=" + toId + ", uuid=" + uuid + ", locale=" + locale
				+ ", type=" + type + "]";
	}
	
}
