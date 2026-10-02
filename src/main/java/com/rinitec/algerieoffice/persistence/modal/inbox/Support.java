package com.rinitec.algerieoffice.persistence.modal.inbox;

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
@Table(name = "supports")
public class Support implements Serializable {
	private static final long serialVersionUID = 5802804583837571062L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "support_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = true)
	private Long adminId;
	
	@Column(nullable = true, length = 1024)
	private String message;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID screenUUID;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	@Column(nullable = false)
	private Boolean consulted;
	
	public Support() {
		this.consulted = false;
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

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UUID getScreenUUID() {
		return screenUUID;
	}

	public void setScreenUUID(UUID screenUUID) {
		this.screenUUID = screenUUID;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	public Boolean getConsulted() {
		return consulted;
	}

	public void setConsulted(Boolean consulted) {
		this.consulted = consulted;
	}

	@Override
	public String toString() {
		return "Support [id=" + id + ", userId=" + userId + ", adminId=" + adminId + ", message=" + message
				+ ", screenUUID=" + screenUUID + ", postedDate=" + postedDate + ", consulted=" + consulted + "]";
	}
	
}
