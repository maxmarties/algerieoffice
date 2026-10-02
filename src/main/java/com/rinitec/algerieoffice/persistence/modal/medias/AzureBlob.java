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

@Entity
@Table(name = "azure_blobs")
public class AzureBlob implements Serializable {
	private static final long serialVersionUID = 4315716282932248025L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "blob_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = true)
	private Long companyId;
	
	@Column(nullable = false, unique = true, length = 512)
	private String filename;
	
	@Column(nullable = false, unique = true, length = 256)
	private String srcname;
	
	public AzureBlob() {
	}
	
	public AzureBlob(final Long companyId) {
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getSrcname() {
		return srcname;
	}
	
	public void setSrcname(String srcname) {
		this.srcname = srcname;
	}

	@Override
	public String toString() {
		return "AzureBlob [id=" + id + ", companyId=" + companyId + ", filename=" + filename + ", srcname=" + srcname + "]";
	}

}
