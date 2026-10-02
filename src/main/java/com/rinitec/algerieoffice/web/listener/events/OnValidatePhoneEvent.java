package com.rinitec.algerieoffice.web.listener.events;

public class OnValidatePhoneEvent {

	private final Long userId;
	private final String token;
	
	public OnValidatePhoneEvent(final Long userId, final String token) {
		this.userId = userId;
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "OnValidatePhoneEvent [userId=" + userId + ", token=" + token + "]";
	}
	
}
