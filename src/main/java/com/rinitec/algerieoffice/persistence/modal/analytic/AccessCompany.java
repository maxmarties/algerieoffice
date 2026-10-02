package com.rinitec.algerieoffice.persistence.modal.analytic;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AccessType;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;

@Entity
@Table(name = "accesses_company")
public class AccessCompany implements Serializable {
	private static final long serialVersionUID = 3378774082977375920L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "access_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = true)
	private Long userId;
	
	@Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private AccessType accessType;
	
	@Column(nullable = false)
	private DateTime accessDate;
	
	@Column(nullable = false, length = 256)
	private String device;
	
	@Column(nullable = false)
	private Boolean fromWeb;
	
	@Max(ConstraintesForm.COUNT_WILAYA)
	@Column(nullable = true)
	private Integer location;
	
	public AccessCompany() {
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

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	public DateTime getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(DateTime accessDate) {
		this.accessDate = accessDate;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public Boolean getFromWeb() {
		return fromWeb;
	}

	public void setFromWeb(Boolean fromWeb) {
		this.fromWeb = fromWeb;
	}
	
	public Integer getLocation() {
		return location;
	}
	
	public void setLocation(Integer location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "AccessCompany [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", accessType="
				+ accessType + ", accessDate=" + accessDate + ", device=" + device + ", fromWeb=" + fromWeb
				+ ", location=" + location + "]";
	}
	
}
