package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.users.feedback.Contact;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class ContactLine implements Serializable {
	private static final long serialVersionUID = 6490654898085014351L;
	
	private final String id;
	private final Integer object;
	private final String username;
	private final String phone;
	private final String email;
	private final String postedDate;
	private final String approuvedBy;
	private final boolean pro;
	private final boolean approuved;
	
	public ContactLine(final Contact contact, final String autor) {
		this.id = contact.getId().toString();
		this.object = contact.getObject();
		this.username = contact.getFirstName().concat(" ").concat(contact.getLastName());
		this.phone = ParseUtil.getFormattedPhone(contact.getPhone());
		this.email = contact.getEmail();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(contact.getPostedDate());
		this.approuvedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.approuved = contact.isApprouved();
		this.pro = contact.getPro();
	}

	public String getId() {
		return id;
	}

	public Integer getObject() {
		return object;
	}

	public String getUsername() {
		return username;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public String getApprouvedBy() {
		return approuvedBy;
	}

	public boolean isPro() {
		return pro;
	}

	public boolean isApprouved() {
		return approuved;
	}

	@Override
	public String toString() {
		return "ContactLine [id=" + id + ", object=" + object + ", username=" + username + ", phone=" + phone
				+ ", email=" + email + ", postedDate=" + postedDate + ", approuvedBy=" + approuvedBy + ", pro=" + pro
				+ ", approuved=" + approuved + "]";
	}
	
}
