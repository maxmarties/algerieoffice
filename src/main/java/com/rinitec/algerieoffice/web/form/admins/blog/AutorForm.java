package com.rinitec.algerieoffice.web.form.admins.blog;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidUrl;

public class AutorForm implements Serializable {
	private static final long serialVersionUID = 7596419149608070637L;
	
	private Long id;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String autorname;
	
	@ValidUrl
	@NotNull
	private String identify;
	
	private String checkedIdentify;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String function;
	
	private String biography;
	private String email;
	private String facebook;
	private String twitter;
	private String linkedin;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	public AutorForm() {
		this.hasAvatar = false;
		this.urlAvatar = "/static/picts/avatars/account-min.jpg";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAutorname() {
		return autorname;
	}

	public void setAutorname(String autorname) {
		this.autorname = autorname;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}
	
	public String getCheckedIdentify() {
		return checkedIdentify;
	}
	
	public void setCheckedIdentify(String checkedIdentify) {
		this.checkedIdentify = checkedIdentify;
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
		return "AutorForm [id=" + id + ", autorname=" + autorname + ", identify=" + identify + ", checkedIdentify="
				+ checkedIdentify + ", function=" + function + ", biography=" + biography + ", email=" + email
				+ ", facebook=" + facebook + ", twitter=" + twitter + ", linkedin=" + linkedin + ", hasAvatar="
				+ hasAvatar + ", hasFileChanged=" + hasFileChanged + ", urlAvatar=" + urlAvatar + "]";
	}

}
