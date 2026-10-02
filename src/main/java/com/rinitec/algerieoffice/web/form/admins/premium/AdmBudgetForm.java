package com.rinitec.algerieoffice.web.form.admins.premium;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Budget;

public class AdmBudgetForm implements Serializable {
	private static final long serialVersionUID = -2065455702483743392L;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	private Integer emails;
	
	@NotNull
	private Integer sendings;
	
	public AdmBudgetForm() {
	}
	
	public AdmBudgetForm(final Long companyId) {
		this.companyId = companyId;
		this.emails = this.sendings = 0;
	}
	
	public AdmBudgetForm(final Budget budget) {
		this.companyId = budget.getCompanyId();
		this.emails = budget.getEmails().intValue();
		this.sendings = budget.getSendings();
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getEmails() {
		return emails;
	}

	public void setEmails(Integer emails) {
		this.emails = emails;
	}

	public Integer getSendings() {
		return sendings;
	}

	public void setSendings(Integer sendings) {
		this.sendings = sendings;
	}

	@Override
	public String toString() {
		return "AdmBudgetForm [companyId=" + companyId + ", emails=" + emails + ", sendings=" + sendings + "]";
	}

}
