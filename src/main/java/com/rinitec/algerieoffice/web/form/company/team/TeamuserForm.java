package com.rinitec.algerieoffice.web.form.company.team;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidEmail;

public class TeamuserForm implements Serializable {
	private static final long serialVersionUID = -911408420426356948L;
	
	private Long id;
	
	@NotNull
	private Long companyId;
	
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
	private boolean hasRandomPassword;
	
	@ValidChose
	private Integer role;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	public TeamuserForm() {
		this.hasAvatar = this.hasRandomPassword = false;
	}
	
	public TeamuserForm(final Long companyId) {
		this();
		this.companyId = companyId;
		this.urlAvatar = "/static/picts/avatars/account-min.jpg";
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

	public boolean isHasRandomPassword() {
		return hasRandomPassword;
	}

	public void setHasRandomPassword(boolean hasRandomPassword) {
		this.hasRandomPassword = hasRandomPassword;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
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

	@Override
	public String toString() {
		return "TeamuserForm [id=" + id + ", companyId=" + companyId + ", firstname=" + firstname + ", lastname="
				+ lastname + ", email=" + email + ", password=" + password + ", hasRandomPassword=" + hasRandomPassword
				+ ", role=" + role + ", hasAvatar=" + hasAvatar + ", hasFileChanged=" + hasFileChanged + ", urlAvatar="
				+ urlAvatar + "]";
	}

}
