package com.rinitec.algerieoffice.persistence.modal.companymaps;

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

import com.rinitec.algerieoffice.enums.DocumentType;

@Entity
@Table(name = "guest_documents")
public class GuestDocument implements Serializable {
	private static final long serialVersionUID = -8995897634856327130L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "guest_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 60)
	private String username;
	
	@Column(nullable = true, length = 10)
	private String phone;
	
	@Column(nullable = false, length = 100)
	private String email;
	
	@Column(nullable = false, length = 512)
	private String message;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID documentId;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID fileUUID;
	
	@Column(nullable = false, length = 8)
    @Enumerated(EnumType.STRING)
	private DocumentType type;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean consulted;
	
	@Column(nullable = true)
	private Long consultedBy;
	
	public GuestDocument() {
		this.consulted = false;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UUID getDocumentId() {
		return documentId;
	}

	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}

	public UUID getFileUUID() {
		return fileUUID;
	}

	public void setFileUUID(UUID fileUUID) {
		this.fileUUID = fileUUID;
	}

	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public void setConsulted(boolean consulted) {
		this.consulted = consulted;
	}

	public Long getConsultedBy() {
		return consultedBy;
	}

	public void setConsultedBy(Long consultedBy) {
		this.consultedBy = consultedBy;
	}

	@Override
	public String toString() {
		return "GuestDocument [id=" + id + ", companyId=" + companyId + ", username=" + username + ", phone=" + phone
				+ ", email=" + email + ", message=" + message + ", documentId=" + documentId + ", fileUUID=" + fileUUID
				+ ", type=" + type + ", postedDate=" + postedDate + ", consulted=" + consulted + ", consultedBy="
				+ consultedBy + "]";
	}

}
