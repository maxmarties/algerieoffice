package com.rinitec.algerieoffice.persistence.modal.blacklist;

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
@Table(name = "blacklist_companies")
public class BlacklistCompany implements Serializable {
	private static final long serialVersionUID = -1890260948447675562L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "blacklist_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = true, length = 150)
	private String reason;
	
	@Column(nullable = false)
	private DateTime lockedDate;
	
	@Column(nullable = false)
	private Long lockedBy;
	
	public BlacklistCompany() {
	}
	
	public BlacklistCompany(final Long companyId, final Long userId) {
		this.companyId = companyId;
		this.userId = userId;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public DateTime getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(DateTime lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Long getLockedBy() {
		return lockedBy;
	}

	public void setLockedBy(Long lockedBy) {
		this.lockedBy = lockedBy;
	}

	@Override
	public String toString() {
		return "BlacklistCompany [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", reason=" + reason
				+ ", lockedDate=" + lockedDate + ", lockedBy=" + lockedBy + "]";
	}

}
