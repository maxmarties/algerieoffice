package com.rinitec.algerieoffice.web.form.admins.marketplace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class AdmPromoteForm implements Serializable {
	private static final long serialVersionUID = -3750179039652899822L;
	
	@NotNull
	private String id;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String title;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String description;
	
	private boolean hasURL;
	private String url;
	
	private boolean enabled;
	
	@NotNull
	private Integer viewCount;
	
	@NotNull
	private Integer creditCount;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	private List<Integer> sectors;
	private List<Integer> wilayas;
	
	public AdmPromoteForm() {
		this.sectors = new ArrayList<Integer>();
		this.wilayas = new ArrayList<Integer>();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isHasURL() {
		return hasURL;
	}

	public void setHasURL(boolean hasURL) {
		this.hasURL = hasURL;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
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
	
	public List<Integer> getSectors() {
		return sectors;
	}
	
	public void setSectors(List<Integer> sectors) {
		this.sectors = sectors;
	}
	
	public List<Integer> getWilayas() {
		return wilayas;
	}
	
	public void setWilayas(List<Integer> wilayas) {
		this.wilayas = wilayas;
	}
	
	public boolean inSectors(final Integer sector) {
		return sectors.contains(sector);
	}
	
	public boolean inWilayas(final Integer wilaya) {
		return wilayas.contains(wilaya);
	}

	@Override
	public String toString() {
		return "AdmPromoteForm [id=" + id + ", title=" + title + ", description=" + description + ", hasURL=" + hasURL
				+ ", url=" + url + ", enabled=" + enabled + ", viewCount=" + viewCount + ", creditCount=" + creditCount
				+ ", hasAvatar=" + hasAvatar + ", hasFileChanged=" + hasFileChanged + ", urlAvatar=" + urlAvatar
				+ ", sectors=" + sectors + ", wilayas=" + wilayas + "]";
	}

}
