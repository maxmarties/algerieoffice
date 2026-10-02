package com.rinitec.algerieoffice.persistence.modal.company.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "companies_identity")
public class CompanyIdentity implements Serializable {
	private static final long serialVersionUID = -2040568838965937570L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Boolean consulted;
	
	@Column(nullable = false)
	private DateTime requestedDate;
	
	public CompanyIdentity() {
		this.consulted = false;
	}
	
	public CompanyIdentity(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getConsulted() {
		return consulted;
	}

	public void setConsulted(Boolean consulted) {
		this.consulted = consulted;
	}

	public DateTime getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(DateTime requestedDate) {
		this.requestedDate = requestedDate;
	}

	@Override
	public String toString() {
		return "CompanyIdentity [companyId=" + companyId + ", userId=" + userId + ", consulted=" + consulted
				+ ", requestedDate=" + requestedDate + "]";
	}
	
}
