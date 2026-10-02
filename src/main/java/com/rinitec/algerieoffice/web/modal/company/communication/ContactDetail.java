package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class ContactDetail implements Serializable {
	private static final long serialVersionUID = -3066400033838943640L;
	
	private final boolean pro;
	private final boolean sexe;
	private final Integer object;
	private final String username;
	private final String function;
	private final String phone;
	private final String email;
	private final String postal;
	private final String message;
	private final String postedDate;
	
	public ContactDetail(final Contact contact) {
		this.pro = contact.getPro();
		this.sexe = contact.getSexe();
		this.object = contact.getObject();
		this.username = contact.getFirstName().concat(" ").concat(contact.getLastName());
		this.function = !StringUtils.isEmpty(contact.getFunction()) ? contact.getFunction() : "-";
		this.phone = ParseUtil.getFormattedPhone(contact.getPhone());
		this.email = contact.getEmail();
		this.postal = contact.getPostal();
		this.message = contact.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(contact.getPostedDate());
	}

	public boolean isPro() {
		return pro;
	}

	public boolean isSexe() {
		return sexe;
	}

	public Integer getObject() {
		return object;
	}

	public String getUsername() {
		return username;
	}

	public String getFunction() {
		return function;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getPostal() {
		return postal;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	@Override
	public String toString() {
		return "ContactDetail [pro=" + pro + ", sexe=" + sexe + ", object=" + object + ", username=" + username
				+ ", function=" + function + ", phone=" + phone + ", email=" + email + ", postal=" + postal
				+ ", message=" + message + ", postedDate=" + postedDate + "]";
	}

}
