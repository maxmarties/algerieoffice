package com.rinitec.algerieoffice.web.modal.admins.feedback;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmBlockLine implements Serializable {
	private static final long serialVersionUID = -4625293878837085614L;
	
	private final Long id;
	private final String username;
	private final String urlAvatar;
	private final String email;
	private final String postal;
	private final String phone;
	private final String createdDate;
	private final boolean hasPro;
	
	public AdmBlockLine(final User user, final Profile profile, final DateTime createdDate) {
		this.id = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=40&height=40"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.email = user.getEmail();
		if(profile != null) {
			this.postal = profile.getPostal();
			this.phone = profile.getPhone();
		} else {
			this.postal = this.phone = "--";
		}
		this.createdDate =  DateTimeFormat.forPattern("dd/MM/yyyy").print(createdDate);
		this.hasPro = user.getCompanyId() != null;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getEmail() {
		return email;
	}

	public String getPostal() {
		return postal;
	}

	public String getPhone() {
		return phone;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public boolean isHasPro() {
		return hasPro;
	}

	@Override
	public String toString() {
		return "AdmBlockLine [id=" + id + ", username=" + username + ", urlAvatar=" + urlAvatar + ", email=" + email
				+ ", postal=" + postal + ", phone=" + phone + ", createdDate=" + createdDate + ", hasPro=" + hasPro + "]";
	}

}
