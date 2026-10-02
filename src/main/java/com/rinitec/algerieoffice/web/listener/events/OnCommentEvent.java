package com.rinitec.algerieoffice.web.listener.events;

import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class OnCommentEvent {

	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final UUID actualityId;
	private final Locale locale;
	
	public OnCommentEvent(final User user, final UUID actualityId, final HttpServletRequest request) {
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=44&height=44"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.actualityId = actualityId;
		this.locale = RequestContextUtils.getLocale(request);
	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public UUID getActualityId() {
		return actualityId;
	}

	public Locale getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return "OnCommentEvent [userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", actualityId=" + actualityId + ", locale=" + locale + "]";
	}
	
}
