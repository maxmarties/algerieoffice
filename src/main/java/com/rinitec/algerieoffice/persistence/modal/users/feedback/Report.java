package com.rinitec.algerieoffice.persistence.modal.users.feedback;

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
@Table(name = "reports")
public class Report implements Serializable {
	private static final long serialVersionUID = 342037083875608021L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "report_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long userId;
	
	@Max(5)
	@Column(nullable = false)
	private Integer type;
	
	@Column(nullable = false, length = 512)
	private String reason;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID fileUUID;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean approuved;
	
	public Report() {
		this.approuved = false;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public boolean isApprouved() {
		return approuved;
	}

	public void setApprouved(boolean approuved) {
		this.approuved = approuved;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", type=" + type + ", reason="
				+ reason + ", fileUUID=" + fileUUID + ", postedDate=" + postedDate + ", approuved=" + approuved + "]";
	}
	
}
