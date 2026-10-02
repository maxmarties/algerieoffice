package com.rinitec.algerieoffice.web.form.company.marketplace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidCalendar;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidUrl;

public class EmployeForm implements Serializable {
	private static final long serialVersionUID = 7370612304098026685L;
	
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
	private Integer contract;
	
	@ValidCalendar
	@NotNull
	private String expiredDate;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_NOTE, message = "{message.input.lenght}")
	private String description;
	
	private String keysword;
	private String urlExtern;
	
	@NotNull(message = "{message.input.required}")
	private String detail;
	
	@ValidChose
	private Integer domaine;
	
	@ValidChose
	private Integer discoverType;
	
	private String discoverValue;
	
	@NotNull
	private Boolean hasPublished;
	
	private List<Integer> locations;
	
	private boolean updateLocations = false;
	
	public EmployeForm() {
		this.locations = new ArrayList<Integer>();
	}
	
	public EmployeForm(final Long companyId) {
		this();
		this.companyId = companyId;
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

	public Integer getContract() {
		return contract;
	}

	public void setContract(Integer contract) {
		this.contract = contract;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
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

	public Integer getDomaine() {
		return domaine;
	}

	public void setDomaine(Integer domaine) {
		this.domaine = domaine;
	}

	public Integer getDiscoverType() {
		return discoverType;
	}

	public void setDiscoverType(Integer discoverType) {
		this.discoverType = discoverType;
	}

	public String getDiscoverValue() {
		return discoverValue;
	}

	public void setDiscoverValue(String discoverValue) {
		this.discoverValue = discoverValue;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}

	public List<Integer> getLocations() {
		return locations;
	}

	public void setLocations(List<Integer> locations) {
		this.locations = locations;
	}

	public boolean isUpdateLocations() {
		return updateLocations;
	}

	public void setUpdateLocations(boolean updateLocations) {
		this.updateLocations = updateLocations;
	}
	
	public boolean inLocations(final Integer location) {
		return locations.contains(location);
	}

	@Override
	public String toString() {
		return "EmployeForm [id=" + id + ", companyId=" + companyId + ", title=" + title + ", identify=" + identify
				+ ", contract=" + contract + ", expiredDate=" + expiredDate + ", description=" + description
				+ ", keysword=" + keysword + ", urlExtern=" + urlExtern + ", detail=" + detail + ", domaine=" + domaine
				+ ", discoverType=" + discoverType + ", discoverValue=" + discoverValue + ", hasPublished="
				+ hasPublished + ", locations=" + locations + ", updateLocations=" + updateLocations + "]";
	}

}
