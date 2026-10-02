package com.rinitec.algerieoffice.persistence.modal.users.favorite;

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
@Table(name = "favorite_companies")
public class FavoriteCompany implements Serializable {
	private static final long serialVersionUID = -6482152531791911212L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "favorite_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Max(4)
	@Column(nullable = false)
	private Integer type;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean alert;
	
	public FavoriteCompany() {
		this.alert = true;
	}
	
	public FavoriteCompany(final Long userId, final Long companyId) {
		this.userId = userId;
		this.companyId = companyId;
		this.alert = true;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	@Override
	public String toString() {
		return "FavoriteCompany [id=" + id + ", userId=" + userId + ", companyId=" + companyId + ", type=" + type
				+ ", postedDate=" + postedDate + ", alert=" + alert + "]";
	}
	
}
