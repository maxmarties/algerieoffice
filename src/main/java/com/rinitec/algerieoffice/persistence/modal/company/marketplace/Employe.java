package com.rinitec.algerieoffice.persistence.modal.company.marketplace;

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
@Table(name = "employes")
public class Employe implements Serializable {
	private static final long serialVersionUID = 8332629802466793421L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "employe_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 150)
	private String title;
	
	@Column(nullable = false, length = 150)
	private String identify;
	
	@Max(5)
	@Column(nullable = false)
	private Integer contract;
	
	@Column(nullable = false)
	private DateTime expiredDate;
	
	@Column(nullable = false, length = 512)
	private String description;
	
	@Column(nullable = true, length = 512)
	private String keysword;
	
	@Column(nullable = false)
	private Long autorId;
	
	@Column(nullable = false)
	private DateTime modifiedDate;
	
	@Column(nullable = false)
	private Integer clickCount;
	
	@Column(nullable = false)
	private Integer workCount;
	
	@Column(nullable = false)
	private Boolean hasTrashed;
	
	@Column(nullable = false)
	private Boolean hasPublished;
	
	public Employe() {
		this.hasTrashed = false;
		this.clickCount = this.workCount = 0;
	}
	
	public Employe(final Long companyId) {
		this();
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

	public Integer getContract() {
		return contract;
	}

	public void setContract(Integer contract) {
		this.contract = contract;
	}

	public DateTime getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(DateTime expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeysword() {
		return keysword;
	}

	public void setKeysword(String keysword) {
		this.keysword = keysword;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}

	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(DateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getWorkCount() {
		return workCount;
	}

	public void setWorkCount(Integer workCount) {
		this.workCount = workCount;
	}

	public Boolean getHasTrashed() {
		return hasTrashed;
	}

	public void setHasTrashed(Boolean hasTrashed) {
		this.hasTrashed = hasTrashed;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}
	
	public boolean isActive() {
		return hasPublished && !hasTrashed;
	}

	@Override
	public String toString() {
		return "Employe [id=" + id + ", companyId=" + companyId + ", title=" + title + ", identify=" + identify
				+ ", contract=" + contract + ", expiredDate=" + expiredDate + ", description=" + description
				+ ", keysword=" + keysword + ", autorId=" + autorId + ", modifiedDate=" + modifiedDate + ", clickCount="
				+ clickCount + ", workCount=" + workCount + ", hasTrashed=" + hasTrashed + ", hasPublished="
				+ hasPublished + "]";
	}

}
