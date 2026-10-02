package com.rinitec.algerieoffice.persistence.modal.users.favorite;

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
@Table(name = "favorite_documents")
public class FavoriteDocument implements Serializable {
	private static final long serialVersionUID = 3772029847876399351L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "favorite_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID documentId;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	@Column(nullable = false, length = 8)
    @Enumerated(EnumType.STRING)
	private DocumentType type;
	
	public FavoriteDocument() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UUID getDocumentId() {
		return documentId;
	}

	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FavoriteDocument [id=" + id + ", userId=" + userId + ", documentId=" + documentId + ", postedDate="
				+ postedDate + ", type=" + type + "]";
	}
	
}
