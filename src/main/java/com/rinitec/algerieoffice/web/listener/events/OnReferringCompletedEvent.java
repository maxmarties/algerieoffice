package com.rinitec.algerieoffice.web.listener.events;

public class OnReferringCompletedEvent {

	private final Long id;
	private final int completed;
	private final boolean hasUser;
	
	public OnReferringCompletedEvent(final Long id, final int completed, final boolean hasUser) {
		this.id = id;
		this.completed = completed;
		this.hasUser = hasUser;
	}

	public Long getId() {
		return id;
	}

	public int getCompleted() {
		return completed;
	}

	public boolean isHasUser() {
		return hasUser;
	}

	@Override
	public String toString() {
		return "OnReferringCompletedEvent [id=" + id + ", completed=" + completed + ", hasUser=" + hasUser + "]";
	}
	
}
