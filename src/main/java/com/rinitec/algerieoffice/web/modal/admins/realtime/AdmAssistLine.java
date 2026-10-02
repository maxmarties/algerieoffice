package com.rinitec.algerieoffice.web.modal.admins.realtime;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Assist;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmAssistLine implements Serializable {
	private static final long serialVersionUID = -3198239284917652946L;
	
	private final String id;
	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final String companyname;
	private final String object;
	private final String message;
	private final String postedDate;
	private final Integer app;
	private final Integer management;
	private final Integer program;
	
	public AdmAssistLine(final Assist assist, final User user, final String tradename) {
		this.id = assist.getId().toString();
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.object = assist.getObject();
		this.message = assist.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(assist.getPostedDate());
		this.app = assist.getApp();
		this.management = assist.getManagement();
		this.program = assist.getProgram();
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

	public String getObject() {
		return object;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public Integer getApp() {
		return app;
	}

	public Integer getManagement() {
		return management;
	}

	public Integer getProgram() {
		return program;
	}

	@Override
	public String toString() {
		return "AdmAssistLine [id=" + id + ", userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", companyname=" + companyname + ", object=" + object + ", message=" + message + ", postedDate="
				+ postedDate + ", app=" + app + ", management=" + management + ", program=" + program + "]";
	}

}
