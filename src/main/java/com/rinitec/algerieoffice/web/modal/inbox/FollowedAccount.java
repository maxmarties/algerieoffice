package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class FollowedAccount implements Serializable {
	private static final long serialVersionUID = -4290724368377575585L;
	
	private final Long id;
	private final String username;
	private final String urlAvatar;
	private final String companyname;
	private final boolean alert;
	
	private String email;
	private boolean online;
	
	public FollowedAccount(final User user, final String tradename, final boolean alert) {
		this.id = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.companyname = tradename;
		this.alert = alert;
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

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getCompanyname() {
		return companyname;
	}

	public boolean isAlert() {
		return alert;
	}
	
	public void updateOnline(final boolean hasOnline) {
		this.online = hasOnline;
		this.email = null;
	}

	@Override
	public String toString() {
		return "FollowedAccount [id=" + id + ", username=" + username + ", urlAvatar=" + urlAvatar + ", companyname="
				+ companyname + ", alert=" + alert + ", email=" + email + ", online=" + online + "]";
	}

}
