package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class FollowedUser implements Serializable {
	private static final long serialVersionUID = 8474208737318265341L;
	
	private final Long id;
	private final String username;
	private final String urlAvatar;
	private final String role;
	
	private String email;
	private boolean online;
	
	public FollowedUser(final User user) {
		this.id = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.role = ParseUtil.getRoleMessage(user.getRoles());
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

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getRole() {
		return role;
	}
	
	public void updateOnline(final boolean hasOnline) {
		this.online = hasOnline;
		this.email = null;
	}

	@Override
	public String toString() {
		return "FollowedUser [id=" + id + ", username=" + username + ", urlAvatar=" + urlAvatar + ", role=" + role
				+ ", email=" + email + ", online=" + online + "]";
	}

}
