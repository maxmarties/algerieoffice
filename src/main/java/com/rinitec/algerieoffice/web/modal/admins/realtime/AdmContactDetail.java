package com.rinitec.algerieoffice.web.modal.admins.realtime;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Contactus;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmContactDetail implements Serializable {
	private static final long serialVersionUID = 1829777272147854385L;
	
	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final String tradename;
	private final String firstName;
	private final String lastName;
	private final String phone;
	private final String email;
	private final String company;
	private final String message;
	private final String postedDate;
	private final int object;
	
	public AdmContactDetail(final Contactus contactus, final User user, final String tradename) {
		if(user != null) {
			this.userId = user.getId();
			this.username = user.getDisplayName();
			this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
					: "/static/picts/avatars/account_mini-min.jpg";
		} else {
			this.userId = null;
			this.username = this.urlAvatar = null;
		}
		this.tradename = !StringUtils.isEmpty(tradename) ? tradename : "--";
		this.firstName = contactus.getFirstName();
		this.lastName = contactus.getLastName();
		this.phone = ParseUtil.getFormattedPhone(contactus.getPhone());
		this.email = contactus.getEmail();
		this.company = !StringUtils.isEmpty(contactus.getCompany()) ? contactus.getCompany() : "--";
		this.message = contactus.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(contactus.getPostedDate());
		this.object = contactus.getObject();
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

	public String getTradename() {
		return tradename;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getCompany() {
		return company;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public int getObject() {
		return object;
	}

	@Override
	public String toString() {
		return "AdmContactDetail [userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", tradename=" + tradename + ", firstName=" + firstName + ", lastName=" + lastName + ", phone="
				+ phone + ", email=" + email + ", company=" + company + ", message=" + message + ", postedDate="
				+ postedDate + ", object=" + object + "]";
	}

}
