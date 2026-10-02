package com.rinitec.algerieoffice.persistence.modal.users.favorite;

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
@Table(name = "favorite_accounts")
public class FavoriteAccount implements Serializable {
	private static final long serialVersionUID = -7274871714102944442L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "favorite_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Long accountId;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean alert;
	
	public FavoriteAccount() {
		this.alert = true;
	}
	
	public FavoriteAccount(final Long userId, final Long accountId) {
		this.userId = userId;
		this.accountId = accountId;
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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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
		return "FavoriteAccount [id=" + id + ", userId=" + userId + ", accountId=" + accountId + ", postedDate="
				+ postedDate + ", alert=" + alert + "]";
	}

}
