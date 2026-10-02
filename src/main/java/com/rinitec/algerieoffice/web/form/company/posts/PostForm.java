package com.rinitec.algerieoffice.web.form.company.posts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidUrl;

public class PostForm implements Serializable {
	private static final long serialVersionUID = -3381634081046551791L;

	private String id;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	private Boolean service;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String title;
	
	@ValidUrl
	@NotNull
	private String identify;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_NOTE, message = "{message.input.lenght}")
	private String description;
	
	@NotNull(message = "{message.input.required}")
	private String detail;
	
	private String category;
	private String keysword;
	private String urlExtern;
	
	@ValidChose
	private Integer priceType;
	
	private String priceValue;
	private String priceParrain;
	private String pricePrecision;
	
	@NotNull
	private Boolean labelNew;
	
	@NotNull
	private Boolean labelExclusif;
	
	@NotNull
	private Boolean hasPublished;
	
	private List<String> textsAlt;
	private List<String> photosUUID;
	private List<String> updated;
	private List<String> trashed;
	
	@NotNull
	private Integer photoPrincipal;
	
	private MultipartFile[] files;
	
	private boolean updateFile = false;
	
	public PostForm() {
		this.textsAlt = new ArrayList<String>();
		this.photosUUID = new ArrayList<String>();
	}
	
	public PostForm(final Long companyId, final boolean service) {
		this();
		this.companyId = companyId;
		this.service = service;
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
	
	public Boolean getService() {
		return service;
	}
	
	public void setService(Boolean service) {
		this.service = service;
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

	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
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

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public String getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(String priceValue) {
		this.priceValue = priceValue;
	}

	public String getPriceParrain() {
		return priceParrain;
	}

	public void setPriceParrain(String priceParrain) {
		this.priceParrain = priceParrain;
	}

	public String getPricePrecision() {
		return pricePrecision;
	}

	public void setPricePrecision(String pricePrecision) {
		this.pricePrecision = pricePrecision;
	}

	public Boolean getLabelNew() {
		return labelNew;
	}

	public void setLabelNew(Boolean labelNew) {
		this.labelNew = labelNew;
	}

	public Boolean getLabelExclusif() {
		return labelExclusif;
	}

	public void setLabelExclusif(Boolean labelExclusif) {
		this.labelExclusif = labelExclusif;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}

	public List<String> getTextsAlt() {
		return textsAlt;
	}

	public void setTextsAlt(List<String> textsAlt) {
		this.textsAlt = textsAlt;
	}

	public List<String> getPhotosUUID() {
		return photosUUID;
	}

	public void setPhotosUUID(List<String> photosUUID) {
		this.photosUUID = photosUUID;
	}

	public List<String> getUpdated() {
		return updated;
	}

	public void setUpdated(List<String> updated) {
		this.updated = updated;
	}

	public List<String> getTrashed() {
		return trashed;
	}

	public void setTrashed(List<String> trashed) {
		this.trashed = trashed;
	}

	public Integer getPhotoPrincipal() {
		return photoPrincipal;
	}

	public void setPhotoPrincipal(Integer photoPrincipal) {
		this.photoPrincipal = photoPrincipal;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public boolean isUpdateFile() {
		return updateFile;
	}

	public void setUpdateFile(boolean updateFile) {
		this.updateFile = updateFile;
	}
	
	public boolean isPresentCategoryId() {
		return !StringUtils.isEmpty(category) && !category.equals("-");
	}

	@Override
	public String toString() {
		return "PostForm [id=" + id + ", companyId=" + companyId + ", service=" + service + ", title=" + title
				+ ", identify=" + identify + ", description=" + description + ", detail=" + detail + ", category="
				+ category + ", keysword=" + keysword + ", urlExtern=" + urlExtern + ", priceType=" + priceType
				+ ", priceValue=" + priceValue + ", priceParrain=" + priceParrain + ", pricePrecision=" + pricePrecision
				+ ", labelNew=" + labelNew + ", labelExclusif=" + labelExclusif + ", hasPublished=" + hasPublished
				+ ", textsAlt=" + textsAlt + ", photosUUID=" + photosUUID + ", updated=" + updated + ", trashed="
				+ trashed + ", photoPrincipal=" + photoPrincipal + ", updateFile=" + updateFile + "]";
	}
	
}
