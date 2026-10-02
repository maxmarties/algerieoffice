package com.rinitec.algerieoffice.web.modal.user.dashboard;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class DashboardMessage implements Serializable {
	private static final long serialVersionUID = 5404405196712738913L;
	
	private final Long userId;
	private final Long senderId;
	private final String avatarURL;
	private final String username;
	private final String message;
	private final DateTime postedDate;
	private final boolean emojis;
	
	public DashboardMessage(final User user, final Message message) {
		this.userId = user.getId();
		this.senderId = message.getSenderId();
		this.avatarURL = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.message = message.getMessage();
		this.postedDate = message.getPostedDate();
		this.emojis = message.isEmojis();
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

	public boolean isEmojis() {
		return emojis;
	}

	@Override
	public String toString() {
		return "DashboardMessage [userId=" + userId + ", senderId=" + senderId + ", avatarURL=" + avatarURL
				+ ", username=" + username + ", message=" + message + ", postedDate=" + postedDate + ", emojis="
				+ emojis + "]";
	}

}
