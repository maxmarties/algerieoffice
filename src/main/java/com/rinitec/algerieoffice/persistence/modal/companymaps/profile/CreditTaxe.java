package com.rinitec.algerieoffice.persistence.modal.companymaps.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyCredit;

@Entity
@Table(name = "credit_taxe")
public class CreditTaxe implements Serializable {
	private static final long serialVersionUID = 3634090201550594953L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String taxename;
	
	@Max(100)
	@Column(nullable = false)
	private Integer taxetaux;
	
	@ManyToOne
	@JoinColumn(name = "companycredit_id", referencedColumnName = "company_id", nullable = false)
	private CompanyCredit companycredit;
	
	public CreditTaxe() {
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getTaxename() {
		return taxename;
	}

	public void setTaxename(String taxename) {
		this.taxename = taxename;
	}

	public Integer getTaxetaux() {
		return taxetaux;
	}

	public void setTaxetaux(Integer taxetaux) {
		this.taxetaux = taxetaux;
	}

	public CompanyCredit getCompanycredit() {
		return companycredit;
	}

	public void setCompanycredit(CompanyCredit companycredit) {
		this.companycredit = companycredit;
	}

	@Override
	public String toString() {
		return "CreditTaxe [id=" + id + ", taxename=" + taxename + ", taxetaux=" + taxetaux + ", companycredit="
				+ companycredit + "]";
	}
	
}
