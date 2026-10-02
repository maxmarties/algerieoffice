package com.rinitec.algerieoffice.web.listener.events;

public class OnJournalUserEvent {

	private final Long userId;
	private final String action;
	private final String element;
	
	public OnJournalUserEvent(final Long userId, final String action, final String element) {
		this.userId = userId;
		this.action = action;
		this.element = element;
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
		return "OnJournalUserEvent [userId=" + userId + ", action=" + action + ", element=" + element + "]";
	}
	
}
