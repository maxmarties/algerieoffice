package com.rinitec.algerieoffice.persistence.modal.company.profile;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.DayShedule;

@Entity
@Table(name = "companies_shedule")
public class CompanyShedule implements Serializable {
	private static final long serialVersionUID = 6441262238061134328L;

	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = true, unique = true, length = 10)
	private String mobile;
	
	@Column(nullable = true, unique = true, length = 10)
	private String fax;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "companyshedule")
	private Collection<DayShedule> days;
	
	public CompanyShedule() {
	}
	
	public CompanyShedule(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Collection<DayShedule> getDays() {
		return days;
	}

	public void setDays(Collection<DayShedule> days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "CompanyShedule [companyId=" + companyId + ", mobile=" + mobile + ", fax=" + fax + ", days=" + days + "]";
	}
	
}
