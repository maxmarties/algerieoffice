package com.rinitec.algerieoffice.web.form.register;

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

public class CompanyForm implements Serializable {
	private static final long serialVersionUID = -5939485854765674022L;

	private boolean hasAccepte = false;
	private boolean hasAvatar = false;
	
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
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_PASSWORD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String denomination;
	
	private String tradename;
	
	@NotNull
	private String activity;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_DESCRIPTION, message = "{message.input.lenght}")
	private String description;
	
	@NotNull(message = "{message.input.required}")
	private String lang;
	
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
	
    private String companymail;
    
    private MultipartFile file;

	public CompanyForm() {
	}
	
	public CompanyForm(final String lang) {
		this.lang = lang;
	}

	public boolean isHasAccepte() {
		return hasAccepte;
	}

	public void setHasAccepte(boolean hasAccepte) {
		this.hasAccepte = hasAccepte;
	}
	
	public boolean isHasAvatar() {
		return hasAvatar;
	}
	
	public void setHasAvatar(boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
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
	
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
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
	
	public String getCompanymail() {
		return companymail;
	}
	
	public void setCompanymail(String companymail) {
		this.companymail = companymail;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "CompanyForm [hasAccepte=" + hasAccepte + ", hasAvatar=" + hasAvatar + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", email=" + email + ", password=" + password + ", denomination="
				+ denomination + ", tradename=" + tradename + ", activity=" + activity + ", description=" + description
				+ ", lang=" + lang + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya + ", phone="
				+ phone + ", companymail=" + companymail + "]";
	}
	
}
