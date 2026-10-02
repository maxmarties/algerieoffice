package com.rinitec.algerieoffice.web.form.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidEmail;
import com.rinitec.algerieoffice.web.validator.ValidPhone;
import com.rinitec.algerieoffice.web.validator.ValidPostal;

public class AddcompanyForm implements Serializable {
	private static final long serialVersionUID = -9164716887233901643L;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_PASSWORD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String denomination;
	
	private String tradename;
	
	@NotNull
	private String activity;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_DESCRIPTION, message = "{message.input.lenght}")
	private String description;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_ADRRESS, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String address;
	
	@ValidPostal
	@NotNull
	private String postal;
	
	@ValidChose
	private Integer wilaya;
	
	@ValidPhone
	@NotNull
	private String phone;
	
	@ValidEmail
    @NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String email;
	
	@NotNull(message = "{message.input.required}")
	private String lang;
	
	private boolean hasAvatar = false;
	
	private MultipartFile file;
	
	public AddcompanyForm() {
	}
	
	public AddcompanyForm(final String lang) {
		this.lang = lang;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public boolean isHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "AddcompanyForm [denomination=" + denomination + ", tradename=" + tradename + ", activity=" + activity
				+ ", description=" + description + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya
				+ ", phone=" + phone + ", email=" + email + ", lang=" + lang + ", hasAvatar=" + hasAvatar + "]";
	}

}
