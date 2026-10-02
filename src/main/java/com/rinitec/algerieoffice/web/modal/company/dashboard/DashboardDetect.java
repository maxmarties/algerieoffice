package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class DashboardDetect implements Serializable {
	private static final long serialVersionUID = -2242653429601819158L;
	
	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final String tradename;
	private final DateTime accessDate;

	public DashboardDetect(final User user, final String tradename, final DateTime accessDate) {
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.accessDate = accessDate;
		this.tradename = !StringUtils.isEmpty(tradename) ? tradename : "--";
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

	public String getTradename() {
		return tradename;
	}

	public DateTime getAccessDate() {
		return accessDate;
	}

	@Override
	public String toString() {
		return "DashboardDetect [userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", tradename=" + tradename + ", accessDate=" + accessDate + "]";
	}

}
