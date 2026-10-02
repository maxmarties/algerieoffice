package com.rinitec.algerieoffice.web.modal.admins.team;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmUserLine implements Serializable {
	private static final long serialVersionUID = -4750415577679633732L;
	
	private final Long id;
	private final String urlAvatar;
	private final String username;
	private final String email;
	private final String createDate;
	private final String loginDate;
	private final String ip;
	private final Long numberOfVisit;
	private final boolean pro;
	private final boolean enabled;
	private final boolean locked;
	
	public AdmUserLine(final User user, final Account account) {
		this.id = user.getId();
		this.urlAvatar = user.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32" 
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.email = user.getEmail();
		this.createDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(account.getCreateDate());
		this.loginDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(account.getLastLoginDate());
		this.ip = account.getIp();
		this.numberOfVisit = account.getNumberOfVisits();
		this.pro = user.getCompanyId() != null;
		this.enabled = user.isEnabled();
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

	public String getEmail() {
		return email;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getLoginDate() {
		return loginDate;
	}
	
	public String getIp() {
		return ip;
	}

	public Long getNumberOfVisit() {
		return numberOfVisit;
	}

	public boolean isPro() {
		return pro;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isLocked() {
		return locked;
	}
	
	public boolean isPublished() {
		return enabled && !locked;
	}

	@Override
	public String toString() {
		return "AdmUserLine [id=" + id + ", urlAvatar=" + urlAvatar + ", username=" + username + ", email=" + email
				+ ", createDate=" + createDate + ", loginDate=" + loginDate + ", ip=" + ip + ", numberOfVisit="
				+ numberOfVisit + ", pro=" + pro + ", enabled=" + enabled + ", locked=" + locked + "]";
	}

}
