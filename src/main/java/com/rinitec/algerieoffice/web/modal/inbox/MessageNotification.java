package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class MessageNotification implements Serializable {
	private static final long serialVersionUID = -427055971417170060L;
	
	private final Long userId;
	private final Long senderId;
	private final String avatarURL;
	private final String username;
	private final String message;
	private final DateTime postedDate;
	private final boolean consulted;
	private final boolean emojis;
	
	private String email;
	private boolean online;
	
	public MessageNotification(final User user, final Message message) {
		this.userId = user.getId();
		this.senderId = message.getSenderId();
		this.avatarURL = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.message = message.getMessage();
		this.postedDate = message.getPostedDate();
		this.consulted = message.getConsulted();
		this.emojis = message.isEmojis();
		this.email = user.getEmail();
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

	public Long getUserId() {
		return userId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public String getUsername() {
		return username;
	}

	public String getMessage() {
		return message;
	}

	public DateTime getPostedDate() {
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
	
	public String getDatePattern() {
		return ParseUtil.hasToday(postedDate) ? "HH:mm" : "dd MMM";
	}

	@Override
	public String toString() {
		return "MessageNotification [userId=" + userId + ", senderId=" + senderId + ", avatarURL=" + avatarURL
				+ ", username=" + username + ", message=" + message + ", postedDate=" + postedDate + ", consulted="
				+ consulted + ", emojis=" + emojis + ", email=" + email + ", online=" + online + "]";
	}

}
