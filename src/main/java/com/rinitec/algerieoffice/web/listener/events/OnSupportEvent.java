package com.rinitec.algerieoffice.web.listener.events;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

public class OnSupportEvent {

	private final Long userId;
	private final String email;
	private final int type;
	private final Locale locale;
	
	public OnSupportEvent(final Long userId, final String email, final int type, final HttpServletRequest request) {
		this.userId = userId;
		this.email = email;
		this.type = type;
		this.locale = RequestContextUtils.getLocale(request);
	}

	public Long getUserId() {
		return userId;
	}
	
	public String getEmail() {
		return email;
	}

	public int getType() {
		return type;
	}

	public Locale getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return "OnSupportEvent [userId=" + userId + ", email=" + email + ", type=" + type + ", locale=" + locale + "]";
	}
	
}
