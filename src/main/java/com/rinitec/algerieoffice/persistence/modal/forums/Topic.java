package com.rinitec.algerieoffice.persistence.modal.forums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "topics")
public class Topic implements Serializable {
	private static final long serialVersionUID = -5445722419978746668L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "topic_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Max(10)
	@Column(nullable = false)
	private Integer category;
	
	@Column(nullable = false, length = 150)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private byte[] detail;
	
	@Column(nullable = false, length = 2)
	private String language;
	
	@Column(nullable = false)
	private DateTime createdDate;
	
	@Column(nullable = true)
	private DateTime modifiedDate;
	
	@Column(nullable = false)
	private Integer viewCount;
	
	@Column(nullable = false)
	private Boolean hasQuiz;
	
	public Topic() {
		this.viewCount = 0;
		this.hasQuiz = false;
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

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public byte[] getDetail() {
		return detail;
	}

	public void setDetail(byte[] detail) {
		this.detail = detail;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	
	public Boolean getHasQuiz() {
		return hasQuiz;
	}
	
	public void setHasQuiz(Boolean hasQuiz) {
		this.hasQuiz = hasQuiz;
	}

	@Override
	public String toString() {
		return "Topic [id=" + id + ", userId=" + userId + ", category=" + category + ", title=" + title + ", detail="
				+ Arrays.toString(detail) + ", language=" + language + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", viewCount=" + viewCount + ", hasQuiz=" + hasQuiz + "]";
	}

}
