package com.rinitec.algerieoffice.persistence.modal.users.alerts;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "alert_setting")
public class AlertSetting implements Serializable {
	private static final long serialVersionUID = 2108380434962580603L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long userId;
	
	@Column(nullable = false, length = 5)
	private String communications;
	
	@Column(nullable = false, length = 5)
	private String notifications;
	
	@Column(nullable = false)
	private Boolean analytic;
	
	private boolean secured;
	
	@Max(2)
	@Column(nullable = true)
	private Integer codage;
	
	public AlertSetting() {
		this.secured = false;
	}
	
	public AlertSetting(final Long userId, final Boolean analytic) {
		this.userId = userId;
		this.analytic = analytic;
	}
	
	public AlertSetting(final Long userId, final String communications, final String notifications) {
		this.userId = userId;
		this.communications = communications;
		this.notifications = notifications;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCommunications() {
		return communications;
	}

	public void setCommunications(String communications) {
		this.communications = communications;
	}

	public String getNotifications() {
		return notifications;
	}

	public void setNotifications(String notifications) {
		this.notifications = notifications;
	}

	public Boolean getAnalytic() {
		return analytic;
	}

	public void setAnalytic(Boolean analytic) {
		this.analytic = analytic;
	}

	public boolean isSecured() {
		return secured;
	}

	public void setSecured(boolean secured) {
		this.secured = secured;
	}

	public Integer getCodage() {
		return codage;
	}

	public void setCodage(Integer codage) {
		this.codage = codage;
	}

	@Override
	public String toString() {
		return "AlertSetting [userId=" + userId + ", communications=" + communications + ", notifications="
				+ notifications + ", analytic=" + analytic + ", secured=" + secured + ", codage=" + codage + "]";
	}
	
}
