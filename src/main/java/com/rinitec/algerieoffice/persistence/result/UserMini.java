package com.rinitec.algerieoffice.persistence.result;

import org.joda.time.DateTime;

public class UserMini {

	private final Long userId;
	private final String email;
	
	public UserMini(final Long userId, final String email) {
		this.userId = userId;
		this.email = email;
	}
	
	public UserMini(final Long userId, final String email, final DateTime forOrder) {
		this.userId = userId;
		this.email = email;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "UserMini [userId=" + userId + ", email=" + email + "]";
	}
	
}
