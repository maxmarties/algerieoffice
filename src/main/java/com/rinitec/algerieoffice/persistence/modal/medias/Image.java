package com.rinitec.algerieoffice.persistence.modal.medias;

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
@Table(name = "images")
public class Image implements Serializable {
	private static final long serialVersionUID = -2875447726429537978L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "image_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 256)
	private String filename;
	
	@Column(nullable = false, length = 30)
	private String contentType;
	
	@Column(nullable = false)
	private DateTime uploadDate;
	
	public Image() {
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public DateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(DateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", companyId=" + companyId + ", filename=" + filename + ", contentType="
				+ contentType + ", uploadDate=" + uploadDate + "]";
	}
	
}
