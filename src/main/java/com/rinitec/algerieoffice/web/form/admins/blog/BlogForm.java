package com.rinitec.algerieoffice.web.form.admins.blog;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidUrl;

public class BlogForm implements Serializable {
	private static final long serialVersionUID = -7811504771697678587L;
	
	private String id;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String title;
	
	@ValidUrl
	@NotNull
	private String identify;
	
	private String checkedIdentify;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_DESCRIPTION, message = "{message.input.lenght}")
	private String description;
	
	@NotNull(message = "{message.input.required}")
	private String detail;
	
	@ValidChose
	private Integer category;
	
	@NotNull
	private Long autorId;
	
	private String keysword;
	
	@NotNull(message = "{message.input.required}")
	private String language;
	
	@NotNull
	private Boolean hasPublished;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	public BlogForm() {
	}
	
	public BlogForm(final HttpServletRequest request) {
		this.hasAvatar = false;
		this.hasPublished = true;
		this.urlAvatar = "/static/picts/avatars/actuality-min.jpg";
		this.language = RequestContextUtils.getLocale(request).getLanguage();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	public String getKeysword() {
		return keysword;
	}

	public void setKeysword(String keysword) {
		this.keysword = keysword;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	@Override
	public String toString() {
		return "BlogForm [id=" + id + ", title=" + title + ", identify=" + identify + ", checkedIdentify="
				+ checkedIdentify + ", description=" + description + ", detail=" + detail + ", category=" + category
				+ ", autorId=" + autorId + ", keysword=" + keysword + ", language=" + language + ", hasPublished="
				+ hasPublished + ", hasAvatar=" + hasAvatar + ", hasFileChanged=" + hasFileChanged + ", urlAvatar="
				+ urlAvatar + "]";
	}

}
