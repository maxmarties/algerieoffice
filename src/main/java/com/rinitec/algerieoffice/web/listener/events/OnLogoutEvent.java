package com.rinitec.algerieoffice.web.listener.events;

import java.util.List;

public class OnLogoutEvent {

	private final String email;
	private final List<Long> usersId;
	
	public OnLogoutEvent(final String email) {
		this.email = email;
		this.usersId = null;
	}
	
	public OnLogoutEvent(final List<Long> usersId) {
		this.email = null;
		this.usersId = usersId;
	}
	
	public String getEmail() {
		return email;
	}

	public List<Long> getUsersId() {
		return usersId;
	}

	@Override
	public String toString() {
		return "OnLogoutEvent [email=" + email + ", usersId=" + usersId + "]";
	}
	
}
