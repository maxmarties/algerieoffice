package com.rinitec.algerieoffice.web.modal.admins.realtime;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Contactus;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmContactLine implements Serializable {
	private static final long serialVersionUID = -8592643554754225035L;
	
	private final String id;
	private final String username;
	private final String userId;
	private final String phone;
	private final String email;
	private final String message;
	private final String postedDate;
	private final int object;
	private final boolean consulted;
	
	public AdmContactLine(final Contactus contactus) {
		this.id = contactus.getId().toString();
		this.username = contactus.getFirstName().concat(" ").concat(contactus.getLastName());
		this.userId = contactus.getUserId() != null ? contactus.getUserId().toString() : "--";
		this.phone = ParseUtil.getFormattedPhone(contactus.getPhone());
		this.email = contactus.getEmail();
		this.message = contactus.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(contactus.getPostedDate());
		this.object = contactus.getObject();
		this.consulted = contactus.isConsulted();
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getUserId() {
		return userId;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
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

	public boolean isConsulted() {
		return consulted;
	}

	@Override
	public String toString() {
		return "AdmContactLine [id=" + id + ", username=" + username + ", userId=" + userId + ", phone=" + phone
				+ ", email=" + email + ", message=" + message + ", postedDate=" + postedDate + ", object=" + object
				+ ", consulted=" + consulted + "]";
	}

}
