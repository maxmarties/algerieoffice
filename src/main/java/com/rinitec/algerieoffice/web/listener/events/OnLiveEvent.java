package com.rinitec.algerieoffice.web.listener.events;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.enums.LiveType;

public class OnLiveEvent {

	private final Long companyId;
	private final String identify;
	private final LiveType type;
	private final Locale locale;
	
	public OnLiveEvent(final Long companyId, final String identify, final LiveType type, final HttpServletRequest request) {
		this.companyId = companyId;
		this.identify = identify;
		this.type = type;
		this.locale = RequestContextUtils.getLocale(request);
	}

	public Long getCompanyId() {
		return companyId;
	}

	public String getIdentify() {
		return identify;
	}

	public LiveType getType() {
		return type;
	}
	
	public Locale getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return "OnLiveEvent [companyId=" + companyId + ", identify=" + identify + ", type=" + type + ", locale="
				+ locale + "]";
	}
	
}
