package com.rinitec.algerieoffice.web.listener.events;

public class OnJournalCompanyEvent {

	private final Long companyId;
	private final Long userId;
	private final String action;
	private final String element;
	
	public OnJournalCompanyEvent(final Long companyId, final Long userId, final String action, final String element) {
		this.companyId = companyId;
		this.userId = userId;
		this.action = action;
		this.element = element;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getAction() {
		return action;
	}

	public String getElement() {
		return element;
	}

	@Override
	public String toString() {
		return "OnJournalCompanyEvent [companyId=" + companyId + ", userId=" + userId + ", action=" + action
				+ ", element=" + element + "]";
	}
	
}
