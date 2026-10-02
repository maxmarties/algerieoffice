package com.rinitec.algerieoffice.web.form.user.account;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidDate;
import com.rinitec.algerieoffice.web.validator.ValidPhone;
import com.rinitec.algerieoffice.web.validator.ValidPostal;

public class CoordinatesForm implements Serializable {
	private static final long serialVersionUID = -9042568885003715453L;
	
	@NotNull
	private Long id;
	
	@NotNull
	private Boolean sexe;
	
	@ValidDate
	@NotNull
	private String birthDate;
	
	private String function;
	private String biography;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_ADRRESS, max = ConstraintesForm.MAX_LENGTH_MINADRRESS, message = "{message.input.lenght}")
	private String address;
	
	@ValidPostal
	@NotNull
	private String postal;
	
	@ValidChose
	@NotNull
	private Integer wilaya;
	
	@ValidPhone
	@NotNull
	private String phone;
	
	private String website;
	
	private boolean hasPhone;
	
	public CoordinatesForm() {
		this.sexe = this.hasPhone = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getSexe() {
		return sexe;
	}

	public void setSexe(Boolean sexe) {
		this.sexe = sexe;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public boolean isHasPhone() {
		return hasPhone;
	}

	public void setHasPhone(boolean hasPhone) {
		this.hasPhone = hasPhone;
	}

	@Override
	public String toString() {
		return "CoordinatesForm [id=" + id + ", sexe=" + sexe + ", birthDate=" + birthDate + ", function=" + function
				+ ", biography=" + biography + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya
				+ ", phone=" + phone + ", website=" + website + ", hasPhone=" + hasPhone + "]";
	}

}
