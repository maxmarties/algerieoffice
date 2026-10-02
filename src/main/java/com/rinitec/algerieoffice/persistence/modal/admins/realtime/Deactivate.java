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
@Table(name = "deactivates")
public class Deactivate implements Serializable {
	private static final long serialVersionUID = 5992077843573089336L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long accountId;
	
	@Max(4)
	@Column(nullable = false)
	private Integer reason;
	
	@Column(nullable = true, length = 256)
	private String observation;
	
	@Column(nullable = false)
	private Boolean hasCompany;
	
	@Column(nullable = false)
	private DateTime deactivateDate;
	
	private boolean consulted;
	
	public Deactivate() {
		this.consulted = false;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getReason() {
		return reason;
	}

	public void setReason(Integer reason) {
		this.reason = reason;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Boolean getHasCompany() {
		return hasCompany;
	}

	public void setHasCompany(Boolean hasCompany) {
		this.hasCompany = hasCompany;
	}

	public DateTime getDeactivateDate() {
		return deactivateDate;
	}

	public void setDeactivateDate(DateTime deactivateDate) {
		this.deactivateDate = deactivateDate;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public void setConsulted(boolean consulted) {
		this.consulted = consulted;
	}

	@Override
	public String toString() {
		return "Deactivate [id=" + id + ", accountId=" + accountId + ", reason=" + reason + ", observation="
				+ observation + ", hasCompany=" + hasCompany + ", deactivateDate=" + deactivateDate + ", consulted="
				+ consulted + "]";
	}
	
}
