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

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyCredit;

@Entity
@Table(name = "credit_truck")
public class CreditTruck implements Serializable {
	private static final long serialVersionUID = -7693352359012484351L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 250)
	private String indtruck;
	
	@ManyToOne
	@JoinColumn(name = "companycredit_id", referencedColumnName = "company_id", nullable = false)
	private CompanyCredit companycredit;
	
	public CreditTruck() {
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getIndtruck() {
		return indtruck;
	}

	public void setIndtruck(String indtruck) {
		this.indtruck = indtruck;
	}

	public CompanyCredit getCompanycredit() {
		return companycredit;
	}

	public void setCompanycredit(CompanyCredit companycredit) {
		this.companycredit = companycredit;
	}

	@Override
	public String toString() {
		return "CreditTruck [id=" + id + ", indtruck=" + indtruck + ", companycredit=" + companycredit + "]";
	}
	
}
