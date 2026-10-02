package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagePush implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Long senderId;
	private Long recepientId;
	private String senderName;
	private String senderAvatar;
	private String message;
	private String time;
	private boolean emojis;
	
	public MessagePush() {
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getRecepientId() {
		return recepientId;
	}

	public void setRecepientId(Long recepientId) {
		this.recepientId = recepientId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAvatar() {
		return senderAvatar;
	}

	public void setSenderAvatar(String senderAvatar) {
		this.senderAvatar = senderAvatar;
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
	
	public boolean isEmojis() {
		return emojis;
	}
	
	public void setEmojis(boolean emojis) {
		this.emojis = emojis;
	}
	
	public String parseMessage() {
		return message.length() >= 512 ? message.subSequence(0, 500).toString().concat("...") : message;
	}

	@Override
	public String toString() {
		return "MessagePush [id=" + id + ", senderId=" + senderId + ", recepientId=" + recepientId + ", senderName="
				+ senderName + ", senderAvatar=" + senderAvatar + ", message=" + message + ", time=" + time
				+ ", emojis=" + emojis + "]";
	}
	
}
