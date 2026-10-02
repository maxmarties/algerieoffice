package com.rinitec.algerieoffice.persistence.modal.company.manage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stickies")
public class Sticky implements Serializable {
	private static final long serialVersionUID = -1349590420041720844L;
	
	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 60)
	private String title;
	
	@Column(nullable = true, length = 124)
	private String description;
	
	@Column(nullable = false, length = 30)
	private String label;
	
	@Column(nullable = true, unique = true, length = 250)
	private String urlExtern;
	
	public Sticky() {
	}
	
	public Sticky(final Long companyId) {
		this.companyId = companyId;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public void setUrlExtern(String urlExtern) {
		this.urlExtern = urlExtern;
	}

	@Override
	public String toString() {
		return "Sticky [companyId=" + companyId + ", title=" + title + ", description=" + description + ", label="
				+ label + ", urlExtern=" + urlExtern + "]";
	}

}
