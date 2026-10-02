package com.rinitec.algerieoffice.persistence.modal.admins.blog;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "blogs")
public class Blog implements Serializable {
	private static final long serialVersionUID = 3426638865037914256L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "blog_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false, length = 150)
	private String title;
	
	@Column(nullable = false, unique = true, length = 150)
	private String identify;
	
	@Column(nullable = false, length = 250)
	private String description;
	
	@Max(10)
	@Column(nullable = false)
	private Integer category;
	
	@Column(nullable = false)
	private Long autorId;
	
	@Column(nullable = false, length = 2)
	private String language;
	
	@Column(nullable = false)
	private DateTime modifiedDate;
	
	@Column(nullable = false)
	private Boolean hasPublished;
	
	@Column(nullable = false)
	private Integer viewCount;
	
	public Blog() {
		this.viewCount = 0;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(DateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", identify=" + identify + ", description=" + description
				+ ", category=" + category + ", autorId=" + autorId + ", language=" + language + ", modifiedDate="
				+ modifiedDate + ", hasPublished=" + hasPublished + ", viewCount=" + viewCount + "]";
	}
	
}
