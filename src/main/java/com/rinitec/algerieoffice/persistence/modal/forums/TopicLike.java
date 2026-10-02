package com.rinitec.algerieoffice.persistence.modal.forums;

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
@Table(name = "topics_like")
public class TopicLike implements Serializable {
	private static final long serialVersionUID = -2724442371984863273L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "like_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID topicId;
	
	public TopicLike() {
	}
	
	public TopicLike(final Long userId, final UUID topicId) {
		this.userId = userId;
		this.topicId = topicId;
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

	public UUID getTopicId() {
		return topicId;
	}

	public void setTopicId(UUID topicId) {
		this.topicId = topicId;
	}

	@Override
	public String toString() {
		return "TopicLike [id=" + id + ", userId=" + userId + ", topicId=" + topicId + "]";
	}

}
