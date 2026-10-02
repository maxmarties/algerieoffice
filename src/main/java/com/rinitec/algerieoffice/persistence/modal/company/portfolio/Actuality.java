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
@Table(name = "actualities")
public class Actuality implements Serializable {
	private static final long serialVersionUID = 394335183531617180L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "actu_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private DateTime actuDate;
	
	@Column(nullable = false, length = 150)
	private String title;
	
	@Column(nullable = false, length = 1024)
	private String description;
	
	@Column(nullable = true, unique = true, length = 250)
	private String urlExtern;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@Column(nullable = false)
	private DateTime modifiedDate;
	
	@Column(nullable = false)
	private DateTime sharedDate;
	
	@Column(nullable = false)
	private Long autorId;
	
	@Column(nullable = false)
	private Boolean hasPublished;
	
	public Actuality() {
	}
	
	public Actuality(final Long companyId) {
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

	public DateTime getActuDate() {
		return actuDate;
	}

	public void setActuDate(DateTime actuDate) {
		this.actuDate = actuDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public void setUrlExtern(String urlExtern) {
		this.urlExtern = urlExtern;
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
	
	public DateTime getSharedDate() {
		return sharedDate;
	}
	
	public void setSharedDate(DateTime sharedDate) {
		this.sharedDate = sharedDate;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}

	@Override
	public String toString() {
		return "Actuality [id=" + id + ", companyId=" + companyId + ", actuDate=" + actuDate + ", title=" + title
				+ ", description=" + description + ", urlExtern=" + urlExtern + ", photoUUID=" + photoUUID
				+ ", modifiedDate=" + modifiedDate + ", sharedDate=" + sharedDate + ", autorId=" + autorId
				+ ", hasPublished=" + hasPublished + "]";
	}

}
