package com.rinitec.algerieoffice.persistence.modal.admins.premium;

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
@Table(name = "premiums")
public class Premium implements Serializable {
	private static final long serialVersionUID = 7544004813342468591L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Max(4)
	@Column(nullable = false)
	private Integer pass;
	
	@Column(nullable = false)
	private DateTime createDate;
	
	@Column(nullable = false)
	private DateTime expiryDate;
	
	@Column(nullable = false)
	private Boolean enabled;
	
	public Premium() {
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

	public Integer getPass() {
		return pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public DateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(DateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Premium [id=" + id + ", companyId=" + companyId + ", pass=" + pass + ", createDate=" + createDate
				+ ", expiryDate=" + expiryDate + ", enabled=" + enabled + "]";
	}
	
}
