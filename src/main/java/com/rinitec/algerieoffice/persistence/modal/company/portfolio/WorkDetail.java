package com.rinitec.algerieoffice.persistence.modal.company.portfolio;

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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "works_detail")
public class WorkDetail implements Serializable {
	private static final long serialVersionUID = 7424007376394823418L;
	
	@Id
	@Column(name = "work_id", columnDefinition = "uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false, length = 512)
	private String description;
	
	@Lob
	@Column(nullable = false)
	private byte[] detail;
	
	@Column(nullable = true, unique = true, length = 250)
	private String urlExtern;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private Work work;
	
	public WorkDetail() {
	}
	
	public WorkDetail(final Work work) {
		this.work = work;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public void setPhotoUUID(UUID photoUUID) {
		this.photoUUID = photoUUID;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	@Override
	public String toString() {
		return "WorkDetail [id=" + id + ", description=" + description + ", detail=" + Arrays.toString(detail)
				+ ", urlExtern=" + urlExtern + ", photoUUID=" + photoUUID + ", work=" + work + "]";
	}

}
