package com.rinitec.algerieoffice.persistence.modal.company.newsletter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "budgets")
public class Budget implements Serializable {
	private static final long serialVersionUID = -7745507092608790651L;
	
	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long emails;
	
	@Column(nullable = false)
	private Integer sendings;
	
	public Budget() {
		this.emails = 0L;
		this.sendings = 0;
	}
	
	public Budget(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getEmails() {
		return emails;
	}

	public void setEmails(Long emails) {
		this.emails = emails;
	}

	public Integer getSendings() {
		return sendings;
	}

	public void setSendings(Integer sendings) {
		this.sendings = sendings;
	}
	
	public void incrementEmails(final int emails) {
		this.emails += emails;
	}
	
	public void incrementSendings(final int sendings) {
		this.sendings += sendings;
	}

	@Override
	public String toString() {
		return "Budget [companyId=" + companyId + ", emails=" + emails + ", sendings=" + sendings + "]";
	}

}
