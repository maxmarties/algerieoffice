package com.rinitec.algerieoffice.web.form.company.portfolio;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class PartneruserForm implements Serializable {
	private static final long serialVersionUID = -7170917007290868498L;
	
	private String id;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String name;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_DESCRIPTION, message = "{message.input.lenght}")
	private String biography;
	
	@URL(regexp = "^(https|http|ftps|ftp).*")
	@NotNull
	private String url;
	
	@NotNull
	private Boolean hasPingled;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	public PartneruserForm() {
	}
	
	public PartneruserForm(final Long companyId) {
		this.companyId = companyId;
		this.hasAvatar = false;
		this.urlAvatar = "/static/picts/avatars/company-min.jpg";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@Override
	public String toString() {
		return "PartneruserForm [id=" + id + ", companyId=" + companyId + ", name=" + name + ", biography=" + biography
				+ ", url=" + url + ", hasPingled=" + hasPingled + ", hasAvatar=" + hasAvatar + ", hasFileChanged="
				+ hasFileChanged + ", urlAvatar=" + urlAvatar + "]";
	}

}
