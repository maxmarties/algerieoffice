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

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;

@Entity
@Table(name = "briefcases")
public class Briefcase implements Serializable {
	private static final long serialVersionUID = 258612006892001647L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String label;
	
	@Column(nullable = false, length = 60)
	private String info;
	
	@ManyToOne
	@JoinColumn(name = "companybriefcase_id", referencedColumnName = "company_id", nullable = false)
	private CompanyBriefcase companybriefcase;
	
	public Briefcase() {
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public CompanyBriefcase getCompanybriefcase() {
		return companybriefcase;
	}

	public void setCompanybriefcase(CompanyBriefcase companybriefcase) {
		this.companybriefcase = companybriefcase;
	}

	@Override
	public String toString() {
		return "Briefcase [id=" + id + ", label=" + label + ", info=" + info + ", companybriefcase=" + companybriefcase + "]";
	}
	
}
