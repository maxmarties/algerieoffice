package com.rinitec.algerieoffice.persistence.modal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IdentityID implements Serializable {
	private static final long serialVersionUID = -1129042360239258628L;

	@Column(nullable = false, updatable = false)
	private Long userId;
	
	@Column(length = 10, nullable = false)
	private String provider;
	
	public IdentityID() {
	}
	
	public IdentityID(Long userId, String provider) {
		this.userId = userId;
		this.provider = provider;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Override
	public String toString() {
		return "IdentityID [userId=" + userId + ", provider=" + provider + "]";
	}
	
}
