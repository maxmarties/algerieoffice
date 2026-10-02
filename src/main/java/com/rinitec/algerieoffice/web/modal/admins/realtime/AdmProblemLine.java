package com.rinitec.algerieoffice.web.modal.admins.realtime;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Problem;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmProblemLine implements Serializable {
	private static final long serialVersionUID = -725020002289184465L;
	
	private final String id;
	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final String companyname;
	private final String message;
	private final String postedDate;
	private final String fileUrl;
	private final int type;
	
	public AdmProblemLine(final Problem problem, final User user, final String tradename) {
		this.id = problem.getId().toString();
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.companyname = !StringUtils.isEmpty(tradename) ? tradename : "--";
		this.message = problem.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(problem.getPostedDate());
		this.fileUrl = problem.getFileUUID() != null ? ConstraintesURL.URL_ENVELOPES + "?fileId=".concat(problem.getFileUUID().toString()) : null;
		this.type = problem.getType();
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

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "AdmProblemLine [id=" + id + ", userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", companyname=" + companyname + ", message=" + message + ", postedDate=" + postedDate + ", fileUrl="
				+ fileUrl + ", type=" + type + "]";
	}

}
