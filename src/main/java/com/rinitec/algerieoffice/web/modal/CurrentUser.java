package com.rinitec.algerieoffice.web.modal;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CurrentUser implements Serializable {
	private static final long serialVersionUID = 176153888938402950L;

	private final User user;
	
	public CurrentUser(final User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
	
	public boolean admin() {
		return user.getAdmin();
	}
	
	public Long getUserId() {
		return user.getId();
	}
	
	public Long getCompanyId() {
		return user.getCompanyId();
	}
	
	public boolean hasCompany() {
		return user.getCompanyId() != null;
	}
	
	public String getUsername() {
		return user.getDisplayName();
	}
	
	public String getIconUrl() {
		return user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=48&height=48"
				: "/static/picts/avatars/account_mini-min.jpg";
	}
	
	public String getRoleName() {
		for (final Role role : user.getRoles()) {
			if(!role.getName().equals("ROLE_ACCOUNT") && !role.getName().equals("ROLE_VISITOR")) {
				return role.getName().split("_")[2].toLowerCase();
			}
		}
		return "";
	}
	
	public String parseAutoritySocket() {
		return user.getAdmin() ? "admin" : user.getCompanyId() != null ? "company" : "visitor";
	}
	
	@Override
	public String toString() {
		return user.hashString();
	}
	
}
