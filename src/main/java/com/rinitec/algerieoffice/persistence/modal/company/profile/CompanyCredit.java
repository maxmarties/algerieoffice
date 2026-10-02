package com.rinitec.algerieoffice.persistence.modal.company.profile;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.CreditTaxe;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.CreditTruck;

@Entity
@Table(name = "companies_credit")
public class CompanyCredit implements Serializable {
	private static final long serialVersionUID = -9071932132619676162L;

	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean cheque;
	
	@Column(nullable = false)
	private Boolean versement;
	
	@Column(nullable = false)
	private Boolean espece;
	
	@Column(nullable = false)
	private Boolean carte;
	
	@Column(nullable = false)
	private Boolean paypal;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "companycredit")
	private Collection<CreditTaxe> taxes;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "companycredit")
	private Collection<CreditTruck> trucks;
	
	public CompanyCredit() {
	}
	
	public CompanyCredit(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getCheque() {
		return cheque;
	}

	public void setCheque(Boolean cheque) {
		this.cheque = cheque;
	}

	public Boolean getVersement() {
		return versement;
	}

	public void setVersement(Boolean versement) {
		this.versement = versement;
	}

	public Boolean getEspece() {
		return espece;
	}

	public void setEspece(Boolean espece) {
		this.espece = espece;
	}

	public Boolean getCarte() {
		return carte;
	}

	public void setCarte(Boolean carte) {
		this.carte = carte;
	}

	public Boolean getPaypal() {
		return paypal;
	}

	public void setPaypal(Boolean paypal) {
		this.paypal = paypal;
	}

	public Collection<CreditTaxe> getTaxes() {
		return taxes;
	}

	public void setTaxes(Collection<CreditTaxe> taxes) {
		this.taxes = taxes;
	}

	public Collection<CreditTruck> getTrucks() {
		return trucks;
	}

	public void setTrucks(Collection<CreditTruck> trucks) {
		this.trucks = trucks;
	}
	
	public Boolean getTicket(int index) {
		switch(index) {
		case 0: return cheque;
		case 1: return versement;
		case 2: return espece;
		case 3: return carte;
		case 4: return paypal;
		}
		return null;
	}

	@Override
	public String toString() {
		return "CompanyCredit [companyId=" + companyId + ", cheque=" + cheque + ", versement=" + versement + ", espece="
				+ espece + ", carte=" + carte + ", paypal=" + paypal + ", taxes=" + taxes + ", trucks=" + trucks + "]";
	}
	
}
