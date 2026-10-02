package com.rinitec.algerieoffice.web.listener.events;

import java.util.Locale;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.user.alerts.AlertPostResult;

public class OnAlertEvent {

	private final AlertPostResult alert;
	private final String appurl;
    private final Locale locale;
    private final int type;
    
    public OnAlertEvent(final AlertPostResult alert, final int type) {
		this.alert = alert;
		this.appurl = ConstraintesURL.URL_APPLICATION;
		this.locale = Locale.FRANCE;
		this.type = type;
	}

	public AlertPostResult getAlert() {
		return alert;
	}

	public String getAppurl() {
		return appurl;
	}

	public Locale getLocale() {
		return locale;
	}
	
	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "OnAlertEvent [alert=" + alert + ", appurl=" + appurl + ", locale=" + locale + ", type=" + type + "]";
	}
    
}
