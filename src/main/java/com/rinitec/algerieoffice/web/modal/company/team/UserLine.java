package com.rinitec.algerieoffice.web.modal.company.team;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class UserLine implements Serializable {
	private static final long serialVersionUID = -7985662254379802487L;
	
	private final Long id;
	private final String urlAvatar;
	private final String username;
	private final String role;
	private final String email;
	private final String createDate;
	private final String loginDate;
	private final Long numberOfVisit;
	private final boolean hasEnabled;
	
	public UserLine(final User user, final Account account) {
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
		this.hasEnabled = user.isEnabled() && !user.isLocked() && !user.isExpired();
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

	public boolean isHasEnabled() {
		return hasEnabled;
	}

	@Override
	public String toString() {
		return "UserLine [id=" + id + ", urlAvatar=" + urlAvatar + ", username=" + username + ", role=" + role
				+ ", email=" + email + ", createDate=" + createDate + ", loginDate=" + loginDate + ", numberOfVisit="
				+ numberOfVisit + ", hasEnabled=" + hasEnabled + "]";
	}

}
