package com.rinitec.algerieoffice.web.form.company.marketplace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidCalendar;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidUrl;

public class AnnonceForm implements Serializable {
	private static final long serialVersionUID = 5187386188813269126L;
	
	private String id;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String title;
	
	@ValidUrl
	@NotNull
	private String identify;
	
	@ValidChose
	private Integer type;
	
	@ValidCalendar
	@NotNull
	private String startDate;
	
	@ValidCalendar
	@NotNull
	private String endDate;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_NOTE, message = "{message.input.lenght}")
	private String description;
	
	private String keysword;
	private String urlExtern;
	
	@NotNull(message = "{message.input.required}")
	private String detail;
	
	@ValidChose
	private Integer visibility;
	
	@NotNull
	private Boolean hasPublished;
	
	private List<Integer> sectors;
	private List<Integer> wilayas;
	
	private boolean updateSectors = false;
	private boolean updateWilayas = false;
	
	private boolean hasFile;
	private boolean hasFileChanged = false;
	
	private String filename;
	
	private MultipartFile file;
	
	public AnnonceForm() {
		this.sectors = new ArrayList<Integer>();
		this.wilayas = new ArrayList<Integer>();
	}
	
	public AnnonceForm(final Long companyId) {
		this();
		this.companyId = companyId;
		this.hasFile = false;
		this.hasPublished = true;
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

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeysword() {
		return keysword;
	}

	public void setKeysword(String keysword) {
		this.keysword = keysword;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public void setUrlExtern(String urlExtern) {
		this.urlExtern = urlExtern;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public Integer getVisibility() {
		return visibility;
	}
	
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
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
		return "AnnonceForm [id=" + id + ", companyId=" + companyId + ", title=" + title + ", identify=" + identify
				+ ", type=" + type + ", startDate=" + startDate + ", endDate=" + endDate + ", description="
				+ description + ", keysword=" + keysword + ", urlExtern=" + urlExtern + ", detail=" + detail
				+ ", visibility=" + visibility + ", hasPublished=" + hasPublished + ", sectors=" + sectors
				+ ", wilayas=" + wilayas + ", updateSectors=" + updateSectors + ", updateWilayas=" + updateWilayas
				+ ", hasFile=" + hasFile + ", hasFileChanged=" + hasFileChanged + ", filename=" + filename + "]";
	}

}
