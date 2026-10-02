package com.rinitec.algerieoffice.web.form.company.portfolio;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidCalendar;

public class ActualityForm implements Serializable {
	private static final long serialVersionUID = -3637535969273287316L;
	
	private String id;
	
	@NotNull
	private Long companyId;
	
	@ValidCalendar
	@NotNull
	private String actuDate;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String title;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_MESSAGE, message = "{message.input.lenght}")
	private String description;
	
	private String urlExtern;
	
	@NotNull
	private Boolean hasPublished;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	private boolean hasNotified;
	
	public ActualityForm() {
	}
	
	public ActualityForm(final Long companyId) {
		this.companyId = companyId;
		this.hasAvatar = false;
		this.hasPublished = this.hasNotified = true;
		this.urlAvatar = "/static/picts/avatars/actuality-min.jpg";
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

	public String getActuDate() {
		return actuDate;
	}

	public void setActuDate(String actuDate) {
		this.actuDate = actuDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public void setUrlExtern(String urlExtern) {
		this.urlExtern = urlExtern;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
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
	
	public boolean isHasNotified() {
		return hasNotified;
	}
	
	public void setHasNotified(boolean hasNotified) {
		this.hasNotified = hasNotified;
	}

	@Override
	public String toString() {
		return "ActualityForm [id=" + id + ", companyId=" + companyId + ", actuDate=" + actuDate + ", title=" + title
				+ ", description=" + description + ", urlExtern=" + urlExtern + ", hasPublished=" + hasPublished
				+ ", hasAvatar=" + hasAvatar + ", hasFileChanged=" + hasFileChanged + ", urlAvatar=" + urlAvatar
				+ ", hasNotified=" + hasNotified + "]";
	}

}
