package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class DetectAccess implements Serializable {
	private static final long serialVersionUID = -8009738574227146044L;
	
	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final String companyname;
	private final String companyURL;
	private final String accessDate;
	private final Long countAccess;
	
	private String email;
	private boolean online;
	
	public DetectAccess(final User user, final String tradename, final String url, final DateTime accessDate, final Long countAccess) {
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=42&height=42"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.companyname = tradename;
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.accessDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(accessDate);
		this.countAccess = countAccess;
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

	public Long getUserId() {
		return userId;
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
	
	public String getCompanyURL() {
		return companyURL;
	}
	
	public String getAccessDate() {
		return accessDate;
	}

	public Long getCountAccess() {
		return countAccess;
	}
	
	public void updateOnline(final boolean hasOnline) {
		this.online = hasOnline;
		this.email = null;
	}

	@Override
	public String toString() {
		return "DetectAccess [userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", companyname=" + companyname + ", companyURL=" + companyURL + ", accessDate=" + accessDate
				+ ", countAccess=" + countAccess + ", email=" + email + ", online=" + online + "]";
	}

}
