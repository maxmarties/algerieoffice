package com.rinitec.algerieoffice.web.form.register;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidEmail;

public class UserForm implements Serializable {
	private static final long serialVersionUID = 1178601610911951903L;

	private boolean hasAccepte = false;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String firstname;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String lastname;
	
	@ValidEmail
    @NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String email;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_PASSWORD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String password;
	
	public UserForm() {
	}

	public boolean isHasAccepte() {
		return hasAccepte;
	}

	public void setHasAccepte(boolean hasAccepte) {
		this.hasAccepte = hasAccepte;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserForm [hasAccepte=" + hasAccepte + ", firstname=" + firstname + ", lastname=" + lastname + ", email="
				+ email + ", password=" + password + "]";
	}
	
}
