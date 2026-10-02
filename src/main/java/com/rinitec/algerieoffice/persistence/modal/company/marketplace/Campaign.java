package com.rinitec.algerieoffice.persistence.modal.company.marketplace;

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

import com.rinitec.algerieoffice.enums.DocumentType;

@Entity
@Table(name = "campaigns")
public class Campaign implements Serializable {
	private static final long serialVersionUID = 8818368785520096822L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "campaign_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID documentId;
	
	@Column(nullable = false, length = 8)
    @Enumerated(EnumType.STRING)
	private DocumentType type;
	
	@Column(nullable = false)
	private Long autorId;
	
	private boolean enabled;
	private boolean published;
	
	@Column(nullable = false)
	private Integer viewCount;
	
	@Column(nullable = false)
	private Integer clickCount;
	
	@Column(nullable = false)
	private Integer creditCount;
	
	public Campaign() {
		this.enabled = this.published = true;
		this.viewCount = this.clickCount = this.creditCount = 0;
	}
	
	public Campaign(final Long companyId) {
		this();
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

	public UUID getDocumentId() {
		return documentId;
	}

	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}

	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isPublished() {
		return published;
	}
	
	public void setPublished(boolean published) {
		this.published = published;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}
	
	public int parseCreditCount() {
		return creditCount - clickCount;
	}

	@Override
	public String toString() {
		return "Campagne [id=" + id + ", companyId=" + companyId + ", documentId=" + documentId + ", type=" + type
				+ ", autorId=" + autorId + ", enabled=" + enabled + ", published=" + published + ", viewCount="
				+ viewCount + ", clickCount=" + clickCount + ", creditCount=" + creditCount + "]";
	}
	
}
