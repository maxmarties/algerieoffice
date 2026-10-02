package com.rinitec.algerieoffice.web.modal.explorer.profile;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerCompanyMessenger implements Serializable {
	private static final long serialVersionUID = 485244496192024070L;
	
	private final Long userId;
	private final String avatarURL;
	private final String username;
	
	public ExplorerCompanyMessenger(final Long userId, final Boolean hasAvatar, final String username) {
		this.userId = userId;
		this.avatarURL = hasAvatar ? ConstraintesURL.URL_AVATARS + "?postedId=" + userId + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = username;
	}
	
	public Long getUserId() {
		return userId;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return "ExplorerCompanyMessenger [userId=" + userId + ", avatarURL=" + avatarURL + ", username=" + username + "]";
	}

}
