package com.rinitec.algerieoffice.persistence.modal.company.newsletter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "maintemplates")
public class Maintemplate implements Serializable {
	private static final long serialVersionUID = -474729460425189355L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean hasCover;
	
	@Column(nullable = false, length = 30)
	private String paneColor;
	
	@Column(nullable = false, length = 30)
	private String textColor;
	
	@Column(nullable = true, length = 60)
	private String title;
	
	@Max(3)
	@Column(nullable = false)
	private Integer targetIndex;
	
	@Column(nullable = true, length = 250)
	private String targetUrl;
	
	@Max(5)
	@Column(nullable = false)
	private Integer label;
	
	@Column(nullable = true, length = 250)
	private String description;
	
	@Column(nullable = false, length = 5)
	private String params;
	
	public Maintemplate() {
	}
	
	public Maintemplate(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getHasCover() {
		return hasCover;
	}

	public void setHasCover(Boolean hasCover) {
		this.hasCover = hasCover;
	}

	public String getPaneColor() {
		return paneColor;
	}

	public void setPaneColor(String paneColor) {
		this.paneColor = paneColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTargetIndex() {
		return targetIndex;
	}

	public void setTargetIndex(Integer targetIndex) {
		this.targetIndex = targetIndex;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public Integer getLabel() {
		return label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "Maintemplate [companyId=" + companyId + ", hasCover=" + hasCover + ", paneColor=" + paneColor
				+ ", textColor=" + textColor + ", title=" + title + ", targetIndex=" + targetIndex + ", targetUrl="
				+ targetUrl + ", label=" + label + ", description=" + description + ", params=" + params + "]";
	}
	
}
