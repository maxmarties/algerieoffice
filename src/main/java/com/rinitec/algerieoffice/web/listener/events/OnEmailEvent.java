package com.rinitec.algerieoffice.web.listener.events;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.utils.RequestUtil;

public class OnEmailEvent {

	private final Long id;
	private final String appurl;
    private final Locale locale;
    private final EmailType type;
    
    private String email;
    
    public OnEmailEvent(final Long id, final HttpServletRequest request, final EmailType type) {
		this.id = id;
		this.appurl = RequestUtil.getAppurl(request);
		this.locale = RequestContextUtils.getLocale(request);
		this.type = type;
	}
    
    public String getEmail() {
		return email;
	}
    
    public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getAppurl() {
		return appurl;
	}

	public Locale getLocale() {
		return locale;
	}

	public EmailType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "OnEmailEvent [id=" + id + ", appurl=" + appurl + ", locale=" + locale + ", type=" + type + ", email="
				+ email + "]";
	}
	
}
