package com.rinitec.algerieoffice.web.form.company.marketplace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class PromoteForm implements Serializable {
	private static final long serialVersionUID = -7557412958689467023L;
	
	private String id;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String title;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String description;
	
	@ValidChose
	private Integer label;
	
	private boolean hasPageonly;
	private boolean hasURL;
	
	private String url;
	
	private List<Integer> sectors;
	private List<Integer> wilayas;
	
	private boolean updateSectors = false;
	private boolean updateWilayas = false;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	public PromoteForm() {
		this.sectors = new ArrayList<Integer>();
		this.wilayas = new ArrayList<Integer>();
	}
	
	public PromoteForm(final Long companyId) {
		this();
		this.companyId = companyId;
		this.hasAvatar = false;
		this.hasURL = true;
		this.urlAvatar = "/static/picts/avatars/promote-min.jpg";
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

	public Integer getLabel() {
		return label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	public boolean isHasPageonly() {
		return hasPageonly;
	}
	
	public void setHasPageonly(boolean hasPageonly) {
		this.hasPageonly = hasPageonly;
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

	public boolean isUpdateSectors() {
		return updateSectors;
	}

	public void setUpdateSectors(boolean updateSectors) {
		this.updateSectors = updateSectors;
	}

	public boolean isUpdateWilayas() {
		return updateWilayas;
	}

	public void setUpdateWilayas(boolean updateWilayas) {
		this.updateWilayas = updateWilayas;
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
	
	public boolean inSectors(final Integer sector) {
		return sectors.contains(sector);
	}
	
	public boolean inWilayas(final Integer wilaya) {
		return wilayas.contains(wilaya);
	}

	@Override
	public String toString() {
		return "PromoteForm [id=" + id + ", companyId=" + companyId + ", title=" + title + ", description="
				+ description + ", label=" + label + ", hasPageonly=" + hasPageonly + ", hasURL=" + hasURL + ", url="
				+ url + ", sectors=" + sectors + ", wilayas=" + wilayas + ", updateSectors=" + updateSectors
				+ ", updateWilayas=" + updateWilayas + ", hasAvatar=" + hasAvatar + ", hasFileChanged=" + hasFileChanged
				+ ", urlAvatar=" + urlAvatar + "]";
	}

}
