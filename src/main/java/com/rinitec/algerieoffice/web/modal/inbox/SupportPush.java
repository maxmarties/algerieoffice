package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

public class SupportPush implements Serializable {
	private static final long serialVersionUID = 3719370924764851198L;
	
	private String id;
	private Long userId;
	private Long adminId;
	private String username;
	private String userAvatar;
	private String message;
	private String time;
	private boolean screenshot;
	
	public SupportPush() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getAdminId() {
		return adminId;
	}
	
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isScreenshot() {
		return screenshot;
	}

	public void setScreenshot(boolean screenshot) {
		this.screenshot = screenshot;
	}
	
	public String parseMessage() {
		return message.length() >= 1024 ? message.subSequence(0, 1020).toString().concat("...") : message;
	}

	@Override
	public String toString() {
		return "SupportPush [id=" + id + ", userId=" + userId + ", adminId=" + adminId + ", username=" + username
				+ ", userAvatar=" + userAvatar + ", message=" + message + ", time=" + time + ", screenshot="
				+ screenshot + "]";
	}
	
}
