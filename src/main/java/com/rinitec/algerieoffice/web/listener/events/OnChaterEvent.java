package com.rinitec.algerieoffice.web.listener.events;

import com.rinitec.algerieoffice.web.modal.inbox.ChaterPush;

public class OnChaterEvent {

	private final Long userId;
	private final String message;
	
	public OnChaterEvent(final ChaterPush chaterPush) {
		this.userId = chaterPush.getUserId();
		this.message = chaterPush.getMessage();
	}

	public Long getUserId() {
		return userId;
	}

	public String getMessage() {
		return message;
	}
	
	public String parseMessage() {
		return message.length() >= 1024 ? message.subSequence(0, 1020).toString().concat("...") : message;
	}

	@Override
	public String toString() {
		return "OnChaterEvent [userId=" + userId + ", message=" + message + "]";
	}
	
}
