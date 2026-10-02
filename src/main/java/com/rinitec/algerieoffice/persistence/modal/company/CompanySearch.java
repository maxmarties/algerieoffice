package com.rinitec.algerieoffice.persistence.modal.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "companies_search")
public class CompanySearch implements Serializable {
	private static final long serialVersionUID = 2688618607686928519L;
	
	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long simultude;
	
	@Column(nullable = false)
	private Long token;
	
	@Column(nullable = false)
	private Long filter;
	
	@Column(nullable = false)
	private Long tag;
	
	@Column(nullable = false)
	private Long view;
	
	@Max(100)
	@Column(nullable = false)
	private Integer completed;
	
	public CompanySearch() {
		this.simultude = this.token = this.filter = this.tag = this.view = 0L;
		this.completed = 0;
	}
	
	public CompanySearch(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getSimultude() {
		return simultude;
	}

	public void setSimultude(Long simultude) {
		this.simultude = simultude;
	}

	public Long getToken() {
		return token;
	}

	public void setToken(Long token) {
		this.token = token;
	}

	public Long getFilter() {
		return filter;
	}

	public void setFilter(Long filter) {
		this.filter = filter;
	}

	public Long getTag() {
		return tag;
	}

	public void setTag(Long tag) {
		this.tag = tag;
	}
	
	public Long getView() {
		return view;
	}
	
	public void setView(Long view) {
		this.view = view;
	}
	
	public Integer getCompleted() {
		return completed;
	}
	
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "CompanySearch [companyId=" + companyId + ", simultude=" + simultude + ", token=" + token + ", filter="
				+ filter + ", tag=" + tag + ", view=" + view + ", completed=" + completed + "]";
	}
	
}
