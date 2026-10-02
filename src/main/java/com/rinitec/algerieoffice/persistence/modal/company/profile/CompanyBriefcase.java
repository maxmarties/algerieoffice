package com.rinitec.algerieoffice.persistence.modal.company.profile;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.Briefcase;

@Entity
@Table(name = "companies_briefcase")
public class CompanyBriefcase implements Serializable {
	private static final long serialVersionUID = -4043945958966534036L;

	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Max(10)
	@Column(nullable = false)
	private Integer briefcase;
	
	@Column(nullable = false)
	private Boolean warehouse;
	
	@Column(nullable = true)
	private Integer capital;
	
	@Max(4)
	@Column(nullable = false)
	private Integer type;
	
	@Column(nullable = true, length = 16)
	private String nrc;
	
	@Column(nullable = true, length = 15)
	private String nif;
	
	@Column(nullable = true, length = 15)
	private String nis;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "companybriefcase")
	private Collection<Briefcase> briefcases;
	
	public CompanyBriefcase() {
	}
	
	public CompanyBriefcase(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getBriefcase() {
		return briefcase;
	}

	public void setBriefcase(Integer briefcase) {
		this.briefcase = briefcase;
	}

	public Boolean getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Boolean warehouse) {
		this.warehouse = warehouse;
	}

	public Integer getCapital() {
		return capital;
	}
	
	public void setCapital(Integer capital) {
		this.capital = capital;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNis() {
		return nis;
	}

	public void setNis(String nis) {
		this.nis = nis;
	}

	public Collection<Briefcase> getBriefcases() {
		return briefcases;
	}
	
	public void setBriefcases(Collection<Briefcase> briefcases) {
		this.briefcases = briefcases;
	}

	@Override
	public String toString() {
		return "CompanyBriefcase [companyId=" + companyId + ", briefcase=" + briefcase + ", warehouse=" + warehouse
				+ ", capital=" + capital + ", type=" + type + ", nrc=" + nrc + ", nif=" + nif + ", nis=" + nis
				+ ", briefcases=" + briefcases + "]";
	}
	
}
