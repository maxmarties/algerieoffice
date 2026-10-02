package com.rinitec.algerieoffice.persistence.modal.company.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "companies_location")
public class CompanyLocation implements Serializable {
	private static final long serialVersionUID = 1598530955546521323L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean hasEmpded;
	
	@Column(nullable = true, length = 1024)
	private String empded;
	
	@Column(nullable = true, unique = true, length = 250)
	private String urlmap;
	
	public CompanyLocation() {
		this.hasEmpded = false;
	}
	
	public CompanyLocation(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getHasEmpded() {
		return hasEmpded;
	}

	public void setHasEmpded(Boolean hasEmpded) {
		this.hasEmpded = hasEmpded;
	}

	public String getEmpded() {
		return empded;
	}

	public void setEmpded(String empded) {
		this.empded = empded;
	}

	public String getUrlmap() {
		return urlmap;
	}

	public void setUrlmap(String urlmap) {
		this.urlmap = urlmap;
	}

	@Override
	public String toString() {
		return "CompanyLocation [companyId=" + companyId + ", hasEmpded=" + hasEmpded + ", empded=" + empded + ", urlmap=" + urlmap + "]";
	}
	
}
