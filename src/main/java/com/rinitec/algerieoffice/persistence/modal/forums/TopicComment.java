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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "topics_comment")
public class TopicComment implements Serializable {
	private static final long serialVersionUID = 7387480250084222892L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "comment_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID topicId;
	
	@Lob
	@Column(nullable = false)
	private byte[] message;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID parentUUID;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	public TopicComment() {
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

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public UUID getParentUUID() {
		return parentUUID;
	}

	public void setParentUUID(UUID parentUUID) {
		this.parentUUID = parentUUID;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}
	
	public boolean isChildren() {
		return parentUUID != null;
	}

	@Override
	public String toString() {
		return "TopicComment [id=" + id + ", userId=" + userId + ", topicId=" + topicId + ", message="
				+ Arrays.toString(message) + ", parentUUID=" + parentUUID + ", postedDate=" + postedDate + "]";
	}

}
