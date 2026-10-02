package com.rinitec.algerieoffice.web.modal.user.account;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.users.Identity;

public class IdentityProvider implements Serializable {
	private static final long serialVersionUID = -3008469965284889905L;
	
	private final String usermail;
	private final String displayname;
	private final String imageurl;
	
	public IdentityProvider(final Identity identity) {
		this.usermail = identity.getUsermail();
		this.displayname = identity.getDisplayname();
		this.imageurl = !StringUtils.isEmpty(identity.getImageurl()) ? identity.getImageurl() : "/static/picts/avatars/account_mini-min.jpg";
	}

	public String getUsermail() {
		return usermail;
	}

	public String getDisplayname() {
		return displayname;
	}

	public String getImageurl() {
		return imageurl;
	}

	@Override
	public String toString() {
		return "IdentityProvider [usermail=" + usermail + ", displayname=" + displayname + ", imageurl=" + imageurl + "]";
	}

}
