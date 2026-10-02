package com.rinitec.algerieoffice.web.listener.events;

import java.util.List;

public class OnLogoutEvents {

	private final List<String> emails;
	
	public OnLogoutEvents(final List<String> emails) {
		this.emails = emails;
	}

	public List<String> getEmails() {
		return emails;
	}

	@Override
	public String toString() {
		return "OnLogoutEvents [emails=" + emails + "]";
	}
	
}
