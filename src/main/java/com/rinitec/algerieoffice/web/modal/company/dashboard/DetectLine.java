package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.analytic.AccessCompany;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class DetectLine implements Serializable {
	private static final long serialVersionUID = 2904868446780642267L;
	
	private final String id;
	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final String companyname;
	private final String accessDate;
	private final String type;
	private final String device;
	private final boolean formWeb;
	
	public DetectLine(final AccessCompany accessCompany, final User user, final String tradename) {
		this.id = accessCompany.getId().toString();
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.accessDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(accessCompany.getAccessDate());
		this.type = accessCompany.getAccessType().toString();
		this.device = accessCompany.getDevice();
		this.formWeb = accessCompany.getFromWeb();
		this.companyname = !StringUtils.isEmpty(tradename) ? tradename : "--";
	}

	public String getId() {
		return id;
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

	public String getAccessDate() {
		return accessDate;
	}

	public String getType() {
		return type;
	}

	public String getDevice() {
		return device;
	}

	public boolean isFormWeb() {
		return formWeb;
	}

	@Override
	public String toString() {
		return "DetectLine [id=" + id + ", userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", companyname=" + companyname + ", accessDate=" + accessDate + ", type=" + type + ", device="
				+ device + ", formWeb=" + formWeb + "]";
	}

}
