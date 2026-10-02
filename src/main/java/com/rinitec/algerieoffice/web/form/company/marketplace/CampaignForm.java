package com.rinitec.algerieoffice.web.form.company.marketplace;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.web.validator.ValidChose;

public class CampaignForm implements Serializable {
	private static final long serialVersionUID = -5160783400400296459L;
	
	private String id;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	private String documentId;
	
	private String documentTitle;
	
	@ValidChose
	private Integer type;
	
	@ValidChose
	private Integer sector;
	
	@ValidChose
	private Integer wilaya;
	
	public CampaignForm() {
	}
	
	public CampaignForm(final Long companyId) {
		this.companyId = companyId;
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

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
	public String getDocumentTitle() {
		return documentTitle;
	}
	
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSector() {
		return sector;
	}

	public void setSector(Integer sector) {
		this.sector = sector;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	@Override
	public String toString() {
		return "CampaignForm [id=" + id + ", companyId=" + companyId + ", documentId=" + documentId + ", documentTitle="
				+ documentTitle + ", type=" + type + ", sector=" + sector + ", wilaya=" + wilaya + "]";
	}

}
