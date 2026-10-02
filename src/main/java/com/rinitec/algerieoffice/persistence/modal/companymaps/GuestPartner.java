package com.rinitec.algerieoffice.persistence.modal.companymaps;

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
@Table(name = "guest_partners")
public class GuestPartner implements Serializable {
	private static final long serialVersionUID = -783268969496432285L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "guest_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long partnerId;
	
	@Column(nullable = false)
	private DateTime guestDate;
	
	@Column(nullable = false)
	private Long guestBy;
	
	@Column(nullable = true)
	private Long approuvedBy;
	
	private boolean approuved;
	
	@Column(nullable = false)
	private Boolean hasPingled;
	
	public GuestPartner() {
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

	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public DateTime getGuestDate() {
		return guestDate;
	}

	public void setGuestDate(DateTime guestDate) {
		this.guestDate = guestDate;
	}

	public Long getGuestBy() {
		return guestBy;
	}

	public void setGuestBy(Long guestBy) {
		this.guestBy = guestBy;
	}

	public Long getApprouvedBy() {
		return approuvedBy;
	}

	public void setApprouvedBy(Long approuvedBy) {
		this.approuvedBy = approuvedBy;
	}

	public boolean isApprouved() {
		return approuved;
	}

	public void setApprouved(boolean approuved) {
		this.approuved = approuved;
	}

	public Boolean getHasPingled() {
		return hasPingled;
	}

	public void setHasPingled(Boolean hasPingled) {
		this.hasPingled = hasPingled;
	}

	@Override
	public String toString() {
		return "GuestPartner [id=" + id + ", companyId=" + companyId + ", partnerId=" + partnerId + ", guestDate="
				+ guestDate + ", guestBy=" + guestBy + ", approuvedBy=" + approuvedBy + ", approuved=" + approuved
				+ ", hasPingled=" + hasPingled + "]";
	}

}
