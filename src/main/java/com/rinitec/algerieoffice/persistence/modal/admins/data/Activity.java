package com.rinitec.algerieoffice.persistence.modal.admins.data;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;

@Entity
@Table(name = "activities")
public class Activity implements Serializable {
	private static final long serialVersionUID = 1819324023570212414L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 6)
	private String code;
	
	@Max(ConstraintesForm.COUNT_SECTOR_ACTIITY)
	@Column(nullable = false)
	private Integer sector;
	
	@Column(nullable = false, unique = true, length = 250)
	private String url;
	
	@ManyToMany(mappedBy = "activities")
	private Collection<Company> companies;
	
	public Activity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSector() {
		return sector;
	}

	public void setSector(Integer sector) {
		this.sector = sector;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Collection<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(Collection<Company> companies) {
		this.companies = companies;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", code=" + code + ", sector=" + sector + ", url=" + url + "]";
	}
	
}
