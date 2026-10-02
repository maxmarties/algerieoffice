package com.rinitec.algerieoffice.persistence.modal.company.manage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "settings")
public class Settings implements Serializable {
	private static final long serialVersionUID = -1348981030145851399L;
	
	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 5)
	private String params;
	
	private boolean service;
	private boolean posthome;
	private boolean indexed;
	
	@Column(nullable = true, length = 256)
	private String banner;
	
	public Settings() {
	}
	
	public Settings(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public boolean isService() {
		return service;
	}

	public void setService(boolean service) {
		this.service = service;
	}

	public boolean isPosthome() {
		return posthome;
	}

	public void setPosthome(boolean posthome) {
		this.posthome = posthome;
	}

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@Override
	public String toString() {
		return "Settings [companyId=" + companyId + ", params=" + params + ", service=" + service + ", posthome="
				+ posthome + ", indexed=" + indexed + ", banner=" + banner + "]";
	}

}
