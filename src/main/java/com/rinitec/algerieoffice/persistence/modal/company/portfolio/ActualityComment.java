package com.rinitec.algerieoffice.persistence.modal.company.portfolio;

import java.io.Serializable;
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
@Table(name = "actualities_comment")
public class ActualityComment implements Serializable {
	private static final long serialVersionUID = -573085618052962821L;
	
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
	private UUID actualityId;
	
	@Column(nullable = false, length = 1024)
	private String message;
	
	//TODO MORE VERSION
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID parentUUID;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	public ActualityComment() {
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

	public UUID getActualityId() {
		return actualityId;
	}

	public void setActualityId(UUID actualityId) {
		this.actualityId = actualityId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
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

	@Override
	public String toString() {
		return "ActualityComment [id=" + id + ", userId=" + userId + ", actualityId=" + actualityId + ", message="
				+ message + ", parentUUID=" + parentUUID + ", postedDate=" + postedDate + "]";
	}

}
