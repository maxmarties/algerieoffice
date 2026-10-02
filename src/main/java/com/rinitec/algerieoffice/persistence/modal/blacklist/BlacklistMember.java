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
@Table(name = "blacklist_members")
public class BlacklistMember implements Serializable {
	private static final long serialVersionUID = 1705219608305036715L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "blacklist_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Long memberId;
	
	@Column(nullable = true, length = 150)
	private String reason;
	
	@Column(nullable = false)
	private DateTime lockedDate;
	
	public BlacklistMember() {
	}
	
	public BlacklistMember(final Long userId, final Long memberId) {
		this.userId = userId;
		this.memberId = memberId;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	@Override
	public String toString() {
		return "BlacklistMember [id=" + id + ", userId=" + userId + ", memberId=" + memberId + ", reason=" + reason
				+ ", lockedDate=" + lockedDate + "]";
	}
	
}
