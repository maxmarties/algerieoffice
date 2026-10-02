package com.rinitec.algerieoffice.persistence.modal.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "companies_account")
public class CompanyAccount implements Serializable {
	private static final long serialVersionUID = -6182804579044043584L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false, unique = true)
	private Long createdById;
	
	@Column(nullable = false)
	private DateTime createdDate;
	
	@Column(nullable = false)
	private DateTime modifiedDate;
	
	@Column(nullable = false)
    private Long numberOfVisits;
	
	@Column(nullable = false)
    private Long numberOfSignal;
	
	public CompanyAccount() {
		this.numberOfVisits = this.numberOfSignal = 0L;
	}

	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public DateTime getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(DateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getNumberOfVisits() {
		return numberOfVisits;
	}

	public void setNumberOfVisits(Long numberOfVisits) {
		this.numberOfVisits = numberOfVisits;
	}

	public Long getNumberOfSignal() {
		return numberOfSignal;
	}

	public void setNumberOfSignal(Long numberOfSignal) {
		this.numberOfSignal = numberOfSignal;
	}

	@Override
	public String toString() {
		return "CompanyAccount [companyId=" + companyId + ", createdById=" + createdById + ", createdDate="
				+ createdDate + ", modifiedDate=" + modifiedDate + ", numberOfVisits=" + numberOfVisits
				+ ", numberOfSignal=" + numberOfSignal + "]";
	}
	
}
