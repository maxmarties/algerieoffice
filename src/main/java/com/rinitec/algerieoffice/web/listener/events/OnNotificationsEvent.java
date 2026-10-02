package com.rinitec.algerieoffice.web.listener.events;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.enums.NotificationType;

public class OnNotificationsEvent {

	private final Long fromId;
	private final List<Long> tosId;
	private final UUID uuid;
	private final Locale locale;
	private final NotificationType type;
	
	public OnNotificationsEvent(final Long fromId, final List<Long> tosId, final UUID uuid, final NotificationType type, final HttpServletRequest request) {
		this.fromId = fromId;
		this.tosId = tosId;
		this.uuid = uuid;
		this.type = type;
		this.locale = RequestContextUtils.getLocale(request);
	}

	public Long getFromId() {
		return fromId;
	}

	public List<Long> getTosId() {
		return tosId;
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
		return "OnNotificationsEvent [fromId=" + fromId + ", tosId=" + tosId + ", uuid=" + uuid + ", locale=" + locale
				+ ", type=" + type + "]";
	}

}
