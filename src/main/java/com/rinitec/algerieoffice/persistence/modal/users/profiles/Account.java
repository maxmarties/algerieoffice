package com.rinitec.algerieoffice.persistence.modal.users.profiles;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.joda.time.DateTime;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {
	private static final long serialVersionUID = -1068662334121757456L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long userId;
	
	@Column(nullable = false, unique = true, length = 250)
	private String pseudo;
	
	@Column(nullable = false)
	private DateTime createDate;
	
	@Column(nullable = true)
	private DateTime activateDate;
	
	@Column(nullable = true)
    private DateTime lastLoginDate;
	
	@Column(nullable = false)
    private Long numberOfVisits;
	
	@Column(nullable = false, length = 60)
	private String ip;
	
	@Column(nullable = false)
	private Boolean hasAccepte;
	
	@Max(100)
	@Column(nullable = false)
	private Integer completed;
	
	public Account() {
		this.numberOfVisits = 0L;
		this.hasAccepte = false;
		this.completed = 0;
	}

	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public DateTime getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(DateTime activateDate) {
		this.activateDate = activateDate;
	}

	public DateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(DateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Long getNumberOfVisits() {
		return numberOfVisits;
	}

	public void setNumberOfVisits(Long numberOfVisits) {
		this.numberOfVisits = numberOfVisits;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Boolean getHasAccepte() {
		return hasAccepte;
	}

	public void setHasAccepte(Boolean hasAccepte) {
		this.hasAccepte = hasAccepte;
	}
	
	public Integer getCompleted() {
		return completed;
	}
	
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "Account [userId=" + userId + ", pseudo=" + pseudo + ", createDate=" + createDate + ", activateDate="
				+ activateDate + ", lastLoginDate=" + lastLoginDate + ", numberOfVisits=" + numberOfVisits + ", ip="
				+ ip + ", hasAccepte=" + hasAccepte + ", completed=" + completed + "]";
	}
	
}
