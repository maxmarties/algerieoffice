package com.rinitec.algerieoffice.web.listener.events;

import com.rinitec.algerieoffice.enums.AccessType;

public class OnAccessCompanyEvent {

	private final Long companyId;
	private final Long userId;
	private final String userAgent;
	private final AccessType accessType;
	
	public OnAccessCompanyEvent(final Long companyId, final Long userId, final String userAgent, final AccessType accessType) {
		this.companyId = companyId;
		this.userId = userId;
		this.userAgent = userAgent;
		this.accessType = accessType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public Long getUserId() {
		return userId;
	}
	
	public String getUserAgent() {
		return userAgent;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	@Override
	public String toString() {
		return "OnAccessCompanyEvent [companyId=" + companyId + ", userId=" + userId + ", userAgent=" + userAgent
				+ ", accessType=" + accessType + "]";
	}
	
}
