package com.rinitec.algerieoffice.web.form.admins.team;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidEmail;

public class AdmManagerForm implements Serializable {
	private static final long serialVersionUID = 1437260701977438024L;
	
	private Long id;
	
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

	private String password;
	
	@ValidChose
	private Integer role;
	
	public AdmManagerForm() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AdmManagerForm [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", password=" + password + ", role=" + role + "]";
	}

}
