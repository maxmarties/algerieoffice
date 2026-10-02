package com.rinitec.algerieoffice.persistence.modal.company;

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

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

@Entity
@Table(name = "companies_address")
public class CompanyAddress implements Serializable {
	private static final long serialVersionUID = -3981478062125881727L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 150)
	private String address;
	
	@Column(nullable = false, length = 5)
	private String postal;
	
	@Max(ConstraintesForm.COUNT_WILAYA)
	@Column(nullable = false)
	private Integer wilaya;
	
	@ManyToOne
	@JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
	private Company company;
	
	public CompanyAddress() {
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "CompanyAddress [id=" + id + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya + "]";
	}
	
}
