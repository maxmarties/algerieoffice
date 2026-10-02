package com.rinitec.algerieoffice.web.modal.admins.team;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmManagerLine implements Serializable {
	private static final long serialVersionUID = -5489391678881760589L;
	
	private final Long id;
	private final String urlAvatar;
	private final String username;
	private final String role;
	private final String email;
	private final String createDate;
	private final String loginDate;
	private final Long numberOfVisit;
	private final boolean locked;
	
	public AdmManagerLine(final User user, final Account account) {
		this.id = user.getId();
		this.urlAvatar = user.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32" 
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.role = ParseUtil.getRoleMessage(user.getRoles());
		this.email = user.getEmail();
		this.createDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(account.getCreateDate());
		this.loginDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(account.getLastLoginDate());
		this.numberOfVisit = account.getNumberOfVisits();
		this.locked = user.isLocked();
	}

	public Long getId() {
		return id;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public String getEmail() {
		return email;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public Long getNumberOfVisit() {
		return numberOfVisit;
	}

	public boolean isLocked() {
		return locked;
	}
	
	public boolean hasSuperAdmin() {
		return role.equals("admin");
	}

	@Override
	public String toString() {
		return "AdmManagerLine [id=" + id + ", urlAvatar=" + urlAvatar + ", username=" + username + ", role=" + role
				+ ", email=" + email + ", createDate=" + createDate + ", loginDate=" + loginDate + ", numberOfVisit="
				+ numberOfVisit + ", locked=" + locked + "]";
	}

}
