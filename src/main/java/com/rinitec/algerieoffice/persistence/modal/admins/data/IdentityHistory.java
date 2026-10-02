package com.rinitec.algerieoffice.persistence.modal.admins.data;

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
@Table(name = "identities_history")
public class IdentityHistory implements Serializable {
	private static final long serialVersionUID = -5218692598185720782L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "identity_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long validateBy;
	
	@Column(nullable = false)
	private Boolean response;
	
	@Column(nullable = false)
	private DateTime historyDate;
	
	@Column(nullable = true, length = 256)
	private String message;
	
	public IdentityHistory() {
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

	public Long getValidateBy() {
		return validateBy;
	}

	public void setValidateBy(Long validateBy) {
		this.validateBy = validateBy;
	}

	public Boolean getResponse() {
		return response;
	}

	public void setResponse(Boolean response) {
		this.response = response;
	}

	public DateTime getHistoryDate() {
		return historyDate;
	}

	public void setHistoryDate(DateTime historyDate) {
		this.historyDate = historyDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "IdentityHistory [id=" + id + ", companyId=" + companyId + ", validateBy=" + validateBy + ", response="
				+ response + ", historyDate=" + historyDate + ", message=" + message + "]";
	}

}
