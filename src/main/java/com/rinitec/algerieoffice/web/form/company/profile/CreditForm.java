package com.rinitec.algerieoffice.web.form.company.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class CreditForm implements Serializable {
	private static final long serialVersionUID = 6824182516565232306L;

	@NotNull
	private Long id;
	
	private boolean cheque;
	private boolean versement;
	private boolean espece;
	private boolean carte;
	private boolean paypal;
	
	private List<Long> identsTaxe;
	private List<String> taxesname;
	private List<Integer> taxestaux;
	private List<Long> updatedTaxe;
	private List<Long> trashedTaxe;
	
	private List<Long> identsTruck;
	private List<String> indstruck;
	private List<Long> updatedTruck;
	private List<Long> trashedTruck;
	
	private boolean updateTaxe = false;
	private boolean updateTruck = false;
	
	public CreditForm() {
		this.identsTaxe = new ArrayList<Long>();
		this.taxesname = new ArrayList<String>();
		this.taxestaux = new ArrayList<Integer>();
		this.identsTruck = new ArrayList<Long>();
		this.indstruck = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isCheque() {
		return cheque;
	}

	public void setCheque(boolean cheque) {
		this.cheque = cheque;
	}

	public boolean isVersement() {
		return versement;
	}

	public void setVersement(boolean versement) {
		this.versement = versement;
	}

	public boolean isEspece() {
		return espece;
	}

	public void setEspece(boolean espece) {
		this.espece = espece;
	}

	public boolean isCarte() {
		return carte;
	}

	public void setCarte(boolean carte) {
		this.carte = carte;
	}

	public boolean isPaypal() {
		return paypal;
	}

	public void setPaypal(boolean paypal) {
		this.paypal = paypal;
	}

	public List<Long> getIdentsTaxe() {
		return identsTaxe;
	}
	
	public void setIdentsTaxe(List<Long> identsTaxe) {
		this.identsTaxe = identsTaxe;
	}

	public List<String> getTaxesname() {
		return taxesname;
	}

	public void setTaxesname(List<String> taxesname) {
		this.taxesname = taxesname;
	}

	public List<Integer> getTaxestaux() {
		return taxestaux;
	}

	public void setTaxestaux(List<Integer> taxestaux) {
		this.taxestaux = taxestaux;
	}
	
	public List<Long> getUpdatedTaxe() {
		return updatedTaxe;
	}
	
	public void setUpdatedTaxe(List<Long> updatedTaxe) {
		this.updatedTaxe = updatedTaxe;
	}
	
	public List<Long> getTrashedTaxe() {
		return trashedTaxe;
	}
	
	public void setTrashedTaxe(List<Long> trashedTaxe) {
		this.trashedTaxe = trashedTaxe;
	}

	public List<Long> getIdentsTruck() {
		return identsTruck;
	}
	
	public void setIdentsTruck(List<Long> identsTruck) {
		this.identsTruck = identsTruck;
	}

	public List<String> getIndstruck() {
		return indstruck;
	}

	public void setIndstruck(List<String> indstruck) {
		this.indstruck = indstruck;
	}
	
	public List<Long> getUpdatedTruck() {
		return updatedTruck;
	}
	
	public void setUpdatedTruck(List<Long> updatedTruck) {
		this.updatedTruck = updatedTruck;
	}
	
	public List<Long> getTrashedTruck() {
		return trashedTruck;
	}
	
	public void setTrashedTruck(List<Long> trashedTruck) {
		this.trashedTruck = trashedTruck;
	}

	public boolean isUpdateTaxe() {
		return updateTaxe;
	}

	public void setUpdateTaxe(boolean updateTaxe) {
		this.updateTaxe = updateTaxe;
	}

	public boolean isUpdateTruck() {
		return updateTruck;
	}

	public void setUpdateTruck(boolean updateTruck) {
		this.updateTruck = updateTruck;
	}
	
	public boolean hasCredit() {
		return cheque || versement || espece || carte || paypal;
	}
	
	public Boolean buildCredit(final String credit) {
		switch(credit) {
		case "cheque": return cheque;
		case "versement": return versement;
		case "espece": return espece;
		case "carte": return carte;
		case "paypal": return paypal;
		}
		return null;
	}

	@Override
	public String toString() {
		return "CreditForm [id=" + id + ", cheque=" + cheque + ", versement=" + versement + ", espece=" + espece
				+ ", carte=" + carte + ", paypal=" + paypal + ", identsTaxe=" + identsTaxe + ", taxesname=" + taxesname
				+ ", taxestaux=" + taxestaux + ", updatedTaxe=" + updatedTaxe + ", trashedTaxe=" + trashedTaxe
				+ ", identsTruck=" + identsTruck + ", indstruck=" + indstruck + ", updatedTruck=" + updatedTruck
				+ ", trashedTruck=" + trashedTruck + ", updateTaxe=" + updateTaxe + ", updateTruck=" + updateTruck + "]";
	}
	
}
