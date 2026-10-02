package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Support;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class SupportNotification implements Serializable {
	private static final long serialVersionUID = -7980562396951011L;
	
	private final Long userId;
	private final Long adminId;
	private final String avatarURL;
	private final String username;
	private final String message;
	private final DateTime postedDate;
	private final boolean consulted;
	private final boolean screenshot;
	
	public SupportNotification(final User user, final Support support) {
		this.userId = user.getId();
		this.adminId = support.getAdminId();
		this.avatarURL = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.message = support.getScreenUUID() == null ? support.getMessage() : null;
		this.postedDate = support.getPostedDate();
		this.consulted = support.getConsulted();
		this.screenshot = support.getScreenUUID() != null;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getAdminId() {
		return adminId;
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

	public boolean isScreenshot() {
		return screenshot;
	}
	
	public String getDatePattern() {
		return ParseUtil.hasToday(postedDate) ? "HH:mm" : "dd MMM";
	}

	@Override
	public String toString() {
		return "SupportNotification [userId=" + userId + ", adminId=" + adminId + ", avatarURL=" + avatarURL
				+ ", username=" + username + ", message=" + message + ", postedDate=" + postedDate + ", consulted="
				+ consulted + ", screenshot=" + screenshot + "]";
	}

}
