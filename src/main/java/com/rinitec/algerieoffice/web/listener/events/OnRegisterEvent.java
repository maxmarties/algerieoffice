package com.rinitec.algerieoffice.web.listener.events;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.RequestUtil;

public class OnRegisterEvent {

    private final User user;
    private final String appurl;
    private final Locale locale;
    private final EmailType type;
    
    private String token;
    
    public OnRegisterEvent(final User user, final HttpServletRequest request, final EmailType type) {
    	this.user = user;
		this.appurl = RequestUtil.getAppurl(request);
		this.locale = RequestContextUtils.getLocale(request);
		this.type = type;
	}
    
    public OnRegisterEvent(final User user, final HttpServletRequest request, final EmailType type, final String token) {
    	this.user = user;
		this.appurl = RequestUtil.getAppurl(request);
		this.locale = RequestContextUtils.getLocale(request);
		this.type = type;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
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
		return "OnRegisterEvent [user=" + user + ", appurl=" + appurl + ", locale=" + locale + ", type=" + type
				+ ", token=" + token + "]";
	}
	
}
