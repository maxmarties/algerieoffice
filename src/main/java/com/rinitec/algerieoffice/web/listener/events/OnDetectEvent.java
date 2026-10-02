package com.rinitec.algerieoffice.web.listener.events;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

public class OnDetectEvent {

	private final Long fromId;
	private final Long toId;
	private final boolean hasDetect;
	private final Locale locale;
	
	public OnDetectEvent(final Long fromId, final Long toId, final boolean hasDetect, final HttpServletRequest request) {
		this.fromId = fromId;
		this.toId = toId;
		this.hasDetect = hasDetect;
		this.locale = RequestContextUtils.getLocale(request);
	}

	public Long getFromId() {
		return fromId;
	}

	public Long getToId() {
		return toId;
	}

	public boolean isHasDetect() {
		return hasDetect;
	}

	public Locale getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return "OnDetectEvent [fromId=" + fromId + ", toId=" + toId + ", hasDetect=" + hasDetect + ", locale=" + locale + "]";
	}
	
}
