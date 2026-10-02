package com.rinitec.algerieoffice.persistence.modal.company.marketplace;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "employes_detail")
public class EmployeDetail implements Serializable {
	private static final long serialVersionUID = 4458513462319422831L;
	
	@Id
	@Column(name = "annonce_id", columnDefinition = "uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Lob
	@Column(nullable = false)
	private byte[] detail;
	
	@Column(nullable = true, unique = true, length = 250)
	private String urlExtern;
	
	@Max(11)
	@Column(nullable = false)
	private Integer domaine;
	
	@Max(3)
	@Column(nullable = false)
	private Integer discoverType;
	
	@Column(nullable = true, length = 12)
	private String discoverValue;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private Employe employe;
	
	public EmployeDetail() {
	}
	
	public EmployeDetail(final Employe employe) {
		this.employe = employe;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public byte[] getDetail() {
		return detail;
	}

	public void setDetail(byte[] detail) {
		this.detail = detail;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public void setUrlExtern(String urlExtern) {
		this.urlExtern = urlExtern;
	}

	public Integer getDomaine() {
		return domaine;
	}

	public void setDomaine(Integer domaine) {
		this.domaine = domaine;
	}

	public Integer getDiscoverType() {
		return discoverType;
	}

	public void setDiscoverType(Integer discoverType) {
		this.discoverType = discoverType;
	}

	public String getDiscoverValue() {
		return discoverValue;
	}

	public void setDiscoverValue(String discoverValue) {
		this.discoverValue = discoverValue;
	}

	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	@Override
	public String toString() {
		return "EmployeDetail [id=" + id + ", detail=" + Arrays.toString(detail) + ", urlExtern=" + urlExtern
				+ ", domaine=" + domaine + ", discoverType=" + discoverType + ", discoverValue=" + discoverValue
				+ ", employe=" + employe + "]";
	}

}
