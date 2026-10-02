package com.rinitec.algerieoffice.persistence.modal.company.portfolio;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "partners")
public class Partner implements Serializable {
	private static final long serialVersionUID = 5480598399149157334L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "partener_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 60)
	private String name;
	
	@Column(nullable = false, length = 250)
	private String biography;
	
	@Column(nullable = false, length = 250)
	private String url;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@Column(nullable = false)
	private DateTime modifiedDate;
	
	@Column(nullable = false)
	private Long autorId;
	
	@Column(nullable = false)
	private Boolean hasPingled;
	
	public Partner() {
	}
	
	public Partner(final Long companyId) {
		this.companyId = companyId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public void setPhotoUUID(UUID photoUUID) {
		this.photoUUID = photoUUID;
	}

	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(DateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	public Boolean getHasPingled() {
		return hasPingled;
	}

	public void setHasPingled(Boolean hasPingled) {
		this.hasPingled = hasPingled;
	}

	@Override
	public String toString() {
		return "Partener [id=" + id + ", companyId=" + companyId + ", name=" + name + ", biography=" + biography
				+ ", url=" + url + ", photoUUID=" + photoUUID + ", modifiedDate=" + modifiedDate + ", autorId="
				+ autorId + ", hasPingled=" + hasPingled + "]";
	}
	
}
