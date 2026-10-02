package com.rinitec.algerieoffice.ujson;

import java.io.Serializable;

public class TopicReplyResponse implements Serializable {
	private static final long serialVersionUID = -9008835231727194534L;
	
	private Long userId;
	private String topicId;
	private String commentId;
	private String parentId;
	private String userAvatar;
	private String username;
	private String message;
	private String time;
	
	public TopicReplyResponse() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	
	public String getCommentId() {
		return commentId;
	}
	
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Override
	public String toString() {
		return "TopicReplyResponse [userId=" + userId + ", topicId=" + topicId + ", commentId=" + commentId
				+ ", parentId=" + parentId + ", userAvatar=" + userAvatar + ", username=" + username + ", message="
				+ message + ", time=" + time + "]";
	}

}
