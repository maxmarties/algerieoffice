package com.rinitec.algerieoffice.web.form.admins.ads;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class SponsoreForm implements Serializable {
	private static final long serialVersionUID = 4713668059046922638L;
	
	private String id;
	
	@ValidChose
	private Integer type;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DESCRIPTION, message = "{message.input.lenght}")
	private String url;
	
	@NotNull
	private Integer creditCount;
	
	@NotNull
	private Boolean hasPublished;
	
	private boolean hasFile;
	private boolean hasFileChanged = false;
	
	private String filename;
	private String bannerURL;
	
	private MultipartFile file;
	
	public SponsoreForm() {
		this.hasFile = false;
		this.hasPublished = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}

	public boolean isHasFile() {
		return hasFile;
	}

	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}

	public boolean isHasFileChanged() {
		return hasFileChanged;
	}

	public void setHasFileChanged(boolean hasFileChanged) {
		this.hasFileChanged = hasFileChanged;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getBannerURL() {
		return bannerURL;
	}
	
	public void setBannerURL(String bannerURL) {
		this.bannerURL = bannerURL;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "SponsoreForm [id=" + id + ", type=" + type + ", url=" + url + ", creditCount=" + creditCount
				+ ", hasPublished=" + hasPublished + ", hasFile=" + hasFile + ", hasFileChanged=" + hasFileChanged
				+ ", filename=" + filename + ", bannerURL=" + bannerURL + "]";
	}

}
