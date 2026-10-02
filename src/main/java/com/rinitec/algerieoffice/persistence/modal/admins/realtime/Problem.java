package com.rinitec.algerieoffice.persistence.modal.admins.realtime;

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
@Table(name = "problems")
public class Problem implements Serializable {
	private static final long serialVersionUID = -2169118934648530554L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Max(5)
	@Column(nullable = false)
	private Integer type;
	
	@Column(nullable = false, length = 1024)
	private String message;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID fileUUID;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	public Problem() {
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UUID getFileUUID() {
		return fileUUID;
	}

	public void setFileUUID(UUID fileUUID) {
		this.fileUUID = fileUUID;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	@Override
	public String toString() {
		return "Problem [id=" + id + ", userId=" + userId + ", type=" + type + ", message=" + message + ", fileUUID="
				+ fileUUID + ", postedDate=" + postedDate + "]";
	}

}
