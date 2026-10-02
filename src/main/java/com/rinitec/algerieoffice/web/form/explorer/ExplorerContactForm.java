package com.rinitec.algerieoffice.web.form.explorer;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidEmail;
import com.rinitec.algerieoffice.web.validator.ValidPhone;
import com.rinitec.algerieoffice.web.validator.ValidPostal;

public class ExplorerContactForm implements Serializable {
	private static final long serialVersionUID = -8517150750206751731L;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	private Boolean pro;
	
	@ValidChose
	private Integer object;
	
	@NotNull
	private Boolean sexe;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String firstname;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String lastname;
	
	private String function;
	
	@ValidPhone
	@NotNull
	private String phone;
	
	@ValidEmail
    @NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String email;
	
	@ValidPostal
	@NotNull
	private String postal;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_NOTE, message = "{message.input.lenght}")
	private String message;
	
	public ExplorerContactForm() {
		this.pro = this.sexe = true;
	}
	
	public ExplorerContactForm(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getPro() {
		return pro;
	}

	public void setPro(Boolean pro) {
		this.pro = pro;
	}

	public Integer getObject() {
		return object;
	}

	public void setObject(Integer object) {
		this.object = object;
	}

	public Boolean getSexe() {
		return sexe;
	}

	public void setSexe(Boolean sexe) {
		this.sexe = sexe;
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

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void pushObject(final String param) {
		switch(param) {
		case "infos": this.object = 1; break;
		case "devis": this.object = 2; break;
		case "ads": this.object = 3;
		}
	}

	@Override
	public String toString() {
		return "ExplorerContactForm [companyId=" + companyId + ", pro=" + pro + ", object=" + object + ", sexe=" + sexe
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", function=" + function + ", phone=" + phone
				+ ", email=" + email + ", postal=" + postal + ", message=" + message + "]";
	}

}
