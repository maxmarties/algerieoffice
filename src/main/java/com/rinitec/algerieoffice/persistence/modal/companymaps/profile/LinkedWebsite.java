package com.rinitec.algerieoffice.persistence.modal.companymaps.profile;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.Type;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;

@Entity
@Table(name = "linked_website")
public class LinkedWebsite implements Serializable {
	private static final long serialVersionUID = 2567098180773815818L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String name;
	
	@Column(nullable = false, unique = true, length = 250)
	private String url;
	
	@Max(5)
	@Column(nullable = true)
	private Integer type;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@ManyToOne
	@JoinColumn(name = "companylinked_id", referencedColumnName = "company_id", nullable = false)
	private CompanyLinked companylinked;
	
	public LinkedWebsite() {
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public void setPhotoUUID(UUID photoUUID) {
		this.photoUUID = photoUUID;
	}

	public CompanyLinked getCompanylinked() {
		return companylinked;
	}

	public void setCompanylinked(CompanyLinked companylinked) {
		this.companylinked = companylinked;
	}

	@Override
	public String toString() {
		return "LinkedWebsite [id=" + id + ", name=" + name + ", url=" + url + ", type=" + type + ", photoUUID="
				+ photoUUID + ", companylinked=" + companylinked + "]";
	}
	
}
