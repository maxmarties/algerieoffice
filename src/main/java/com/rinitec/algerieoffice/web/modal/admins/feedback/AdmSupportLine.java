package com.rinitec.algerieoffice.web.modal.admins.feedback;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Support;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmSupportLine implements Serializable {
	private static final long serialVersionUID = 8419887269167735304L;
	
	private final String id;
	private final Long userId;
	private final String adminName;
	private final String urlAvatar;
	private final String username;
	private final String message;
	private final String postedDate;
	private final boolean consulted;
	private final boolean screenshot;
	
	public AdmSupportLine(final User user, final Support support) {
		this.id = support.getId().toString();
		this.userId = user.getId();
		this.adminName = null;
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.message = support.getScreenUUID() == null ? support.getMessage() : ConstraintesURL.URL_SCREENSHOT + "?id=" + support.getScreenUUID().toString();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(support.getPostedDate());
		this.consulted = support.getConsulted();
		this.screenshot = support.getScreenUUID() != null;
	}
	
	public AdmSupportLine(final User user, final Support support, final String adminName) {
		this.id = support.getId().toString();
		this.userId = user.getId();
		this.adminName = adminName;
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.message = support.getScreenUUID() == null ? support.getMessage() : ConstraintesURL.URL_SCREENSHOT + "?id=" + support.getScreenUUID().toString();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(support.getPostedDate());
		this.consulted = support.getConsulted();
		this.screenshot = support.getScreenUUID() != null;
	}

	public String getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public String getAdminName() {
		return adminName;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public boolean isScreenshot() {
		return screenshot;
	}

	@Override
	public String toString() {
		return "AdmSupportLine [id=" + id + ", userId=" + userId + ", adminName=" + adminName + ", urlAvatar="
				+ urlAvatar + ", username=" + username + ", message=" + message + ", postedDate=" + postedDate
				+ ", consulted=" + consulted + ", screenshot=" + screenshot + "]";
	}

}
