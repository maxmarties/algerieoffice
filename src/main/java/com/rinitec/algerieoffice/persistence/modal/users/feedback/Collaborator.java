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
@Table(name = "collaborators")
public class Collaborator implements Serializable {
	private static final long serialVersionUID = -6582371073451795542L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "collaborator_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false, unique = true)
	private Long userId;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 60)
	private String function;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean approuved;
	
	@Column(nullable = true)
	private Long approuvedBy;
	
	public Collaborator() {
		this.approuved = false;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
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

	public Long getApprouvedBy() {
		return approuvedBy;
	}

	public void setApprouvedBy(Long approuvedBy) {
		this.approuvedBy = approuvedBy;
	}

	@Override
	public String toString() {
		return "Collaborator [id=" + id + ", userId=" + userId + ", companyId=" + companyId + ", function=" + function
				+ ", postedDate=" + postedDate + ", approuved=" + approuved + ", approuvedBy=" + approuvedBy + "]";
	}

}
