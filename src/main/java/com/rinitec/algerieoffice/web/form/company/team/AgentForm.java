package com.rinitec.algerieoffice.web.form.company.team;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidEmail;
import com.rinitec.algerieoffice.web.validator.ValidPhone;

public class AgentForm implements Serializable {
	private static final long serialVersionUID = -5183199189884116878L;
	
	private Long id;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	private Boolean sexe;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String firstname;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String lastname;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String function;
	
	private String biography;
	
	@ValidEmail
    @NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String email;
	
	@ValidPhone
	@NotNull
	private String phone;
	
	private String facebook;
	private String twitter;
	private String linkedin;
	
	private Long userId;
	
	@NotNull
	private Boolean hasPingled;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	public AgentForm() {
		this.hasAvatar = false;
		this.sexe = true;
	}
	
	public AgentForm(final Long companyId) {
		this();
		this.companyId = companyId;
		this.urlAvatar = "/static/picts/avatars/agent-male-min.jpg";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Boolean getHasPingled() {
		return hasPingled;
	}
	
	public void setHasPingled(Boolean hasPingled) {
		this.hasPingled = hasPingled;
	}

	public boolean isHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}

	public boolean isHasFileChanged() {
		return hasFileChanged;
	}

	public void setHasFileChanged(boolean hasFileChanged) {
		this.hasFileChanged = hasFileChanged;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public boolean hasPresentUserId() {
		return userId != null && userId != -1;
	}
	
	public String getDisplayName() {
		return firstname.concat(" ").concat(lastname);
	}

	@Override
	public String toString() {
		return "AgentForm [id=" + id + ", companyId=" + companyId + ", sexe=" + sexe + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", function=" + function + ", biography=" + biography + ", email=" + email
				+ ", phone=" + phone + ", facebook=" + facebook + ", twitter=" + twitter + ", linkedin=" + linkedin
				+ ", userId=" + userId + ", hasPingled=" + hasPingled + ", hasAvatar=" + hasAvatar + ", hasFileChanged="
				+ hasFileChanged + ", urlAvatar=" + urlAvatar + "]";
	}

}
