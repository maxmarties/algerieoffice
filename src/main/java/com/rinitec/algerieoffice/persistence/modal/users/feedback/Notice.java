package com.rinitec.algerieoffice.persistence.modal.users.feedback;

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
@Table(name = "notices")
public class Notice implements Serializable {
	private static final long serialVersionUID = -3732473389819076377L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "notice_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false, length = 60)
	private String title;
	
	@Column(nullable = false, length = 250)
	private String message;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	@Column(nullable = false)
	private Boolean autorised;
	
	private boolean approuved;
	
	@Column(nullable = true)
	private Long approuvedBy;
	
	public Notice() {
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	public Boolean getAutorised() {
		return autorised;
	}

	public void setAutorised(Boolean autorised) {
		this.autorised = autorised;
	}

	public boolean isApprouved() {
		return approuved;
	}

	public void setApprouved(boolean approuved) {
		this.approuved = approuved;
	}
	
	public Long getApprouvedBy() {
		return approuvedBy;
	}
	
	public void setApprouvedBy(Long approuvedBy) {
		this.approuvedBy = approuvedBy;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", title=" + title
				+ ", message=" + message + ", postedDate=" + postedDate + ", autorised=" + autorised + ", approuved="
				+ approuved + ", approuvedBy=" + approuvedBy + "]";
	}
	
}
