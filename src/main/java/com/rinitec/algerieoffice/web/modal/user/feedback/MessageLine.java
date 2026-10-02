package com.rinitec.algerieoffice.web.modal.user.feedback;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class MessageLine implements Serializable {
	private static final long serialVersionUID = -1450635238819389119L;
	
	private final String id;
	private final Long userId;
	private final String urlAvatar;
	private final String username;
	private final String message;
	private final String postedDate;
	private final boolean consulted;
	private final boolean emojis;
	
	private String email;
	private boolean online;
	
	public MessageLine(final User user, final Message message) {
		this.id = message.getId().toString();
		this.userId = user.getId();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.message = message.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(message.getPostedDate());
		this.consulted = message.getConsulted();
		this.emojis = message.isEmojis();
		this.email = user.getEmail();
	}
	
	public Long getUserId() {
		return userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getId() {
		return id;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public boolean isConsulted() {
		return consulted;
	}
	
	public boolean isEmojis() {
		return emojis;
	}
	
	public void updateOnline(final boolean hasOnline) {
		this.online = hasOnline;
		this.email = null;
	}

	@Override
	public String toString() {
		return "MessageLine [id=" + id + ", userId=" + userId + ", urlAvatar=" + urlAvatar + ", username=" + username
				+ ", message=" + message + ", postedDate=" + postedDate + ", consulted=" + consulted + ", emojis="
				+ emojis + ", email=" + email + ", online=" + online + "]";
	}

}
