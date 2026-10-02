package com.rinitec.algerieoffice.persistence.modal.company.portfolio;

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
@Table(name = "works")
public class Work implements Serializable {
	private static final long serialVersionUID = -8943728216790869974L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "work_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private DateTime workDate;
	
	@Column(nullable = false, length = 150)
	private String title;
	
	@Column(nullable = false, length = 150)
	private String identify;
	
	@Column(nullable = false, length = 512)
	private String expertise;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID partnerUUID;
	
	@Column(nullable = false)
	private DateTime modifiedDate;
	
	@Column(nullable = false)
	private Long autorId;
	
	@Column(nullable = false)
	private Boolean hasPublished;
	
	public Work() {
	}
	
	public Work(final Long companyId) {
		this.companyId = companyId;
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

	public DateTime getWorkDate() {
		return workDate;
	}

	public void setWorkDate(DateTime workDate) {
		this.workDate = workDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public UUID getPartnerUUID() {
		return partnerUUID;
	}
	
	public void setPartnerUUID(UUID partnerUUID) {
		this.partnerUUID = partnerUUID;
	}

	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(DateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}

	@Override
	public String toString() {
		return "Work [id=" + id + ", companyId=" + companyId + ", workDate=" + workDate + ", title=" + title
				+ ", identify=" + identify + ", expertise=" + expertise + ", partnerUUID=" + partnerUUID
				+ ", modifiedDate=" + modifiedDate + ", autorId=" + autorId + ", hasPublished=" + hasPublished + "]";
	}

}
