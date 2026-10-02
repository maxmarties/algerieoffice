package com.rinitec.algerieoffice.persistence.modal.medias;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.FileType;

@Entity
@Table(name = "filereaders")
public class Filereader implements Serializable {
	private static final long serialVersionUID = -5866650314905630836L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "file_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 30)
	private String filename;
	
	@Column(nullable = false, length = 30)
	private String contentType;
	
	@Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private FileType fileType;
	
	@Column(nullable = false)
	private DateTime uploadDate;
	
	public Filereader() {
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

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	
	public DateTime getUploadDate() {
		return uploadDate;
	}
	
	public void setUploadDate(DateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Override
	public String toString() {
		return "Filereader [id=" + id + ", companyId=" + companyId + ", filename=" + filename + ", contentType="
				+ contentType + ", fileType=" + fileType + ", uploadDate=" + uploadDate + "]";
	}
	
}
