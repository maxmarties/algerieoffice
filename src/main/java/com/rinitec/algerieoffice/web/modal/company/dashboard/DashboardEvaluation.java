package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class DashboardEvaluation implements Serializable {
	private static final long serialVersionUID = 154483998122337153L;
	
	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final String tradename;
	private final boolean liked;
	private final int note;
	
	public DashboardEvaluation(final User user, final String tradename, final Boolean liked, final Integer note) {
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.tradename = !StringUtils.isEmpty(tradename) ? tradename : "--";
		this.liked = liked;
		this.note = note;
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

	public boolean isLiked() {
		return liked;
	}

	public int getNote() {
		return note;
	}

	@Override
	public String toString() {
		return "DashboardEvaluation [userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", tradename=" + tradename + ", liked=" + liked + ", note=" + note + "]";
	}

}
