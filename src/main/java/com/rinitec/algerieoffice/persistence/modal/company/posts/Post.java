package com.rinitec.algerieoffice.persistence.modal.company.posts;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
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
@Table(name = "posts")
public class Post implements Serializable {
	private static final long serialVersionUID = -885434873157805393L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "post_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean service;
	
	@Column(nullable = false, length = 150)
	private String title;
	
	@Column(nullable = false, length = 150)
	private String identify;
	
	@Column(nullable = false, length = 512)
	private String description;
	
	@Column(nullable = true, length = 512)
	private String keysword;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID categoryId;
	
	@Column(nullable = false)
	private DateTime createdDate;
	
	@Column(nullable = false)
	private DateTime modifiedDate;
	
	@Column(nullable = false)
	private Long autorId;
	
	@Column(nullable = false)
	private Boolean hasTrashed;
	
	@Column(nullable = false)
	private Boolean hasPublished;
	
	public Post() {
		this.hasTrashed = false;
		this.createdDate = new DateTime(Date.from(Instant.now()));
	}
	
	public Post(final Long companyId) {
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
	
	public Boolean getService() {
		return service;
	}
	
	public void setService(Boolean service) {
		this.service = service;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeysword() {
		return keysword;
	}

	public void setKeysword(String keysword) {
		this.keysword = keysword;
	}

	public UUID getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
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

	public Boolean getHasTrashed() {
		return hasTrashed;
	}

	public void setHasTrashed(Boolean hasTrashed) {
		this.hasTrashed = hasTrashed;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}
	
	public boolean isActive() {
		return hasPublished && !hasTrashed;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", companyId=" + companyId + ", service=" + service + ", title=" + title
				+ ", identify=" + identify + ", description=" + description + ", keysword=" + keysword + ", categoryId="
				+ categoryId + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", autorId="
				+ autorId + ", hasTrashed=" + hasTrashed + ", hasPublished=" + hasPublished + "]";
	}

}
