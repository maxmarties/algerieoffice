package com.rinitec.algerieoffice.web.form.admins.ads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class AdmMailingForm implements Serializable {
	private static final long serialVersionUID = 8523523330811521586L;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String title;
	
	@NotNull(message = "{message.input.required}")
	private String detail;
	
	private List<String> users;
	
	public AdmMailingForm() {
		this.users = new ArrayList<String>();
	}
	
	public AdmMailingForm(final List<String> users) {
		this.users = users;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "MailingForm [title=" + title + ", detail=" + detail + ", users=" + users + "]";
	}

}
