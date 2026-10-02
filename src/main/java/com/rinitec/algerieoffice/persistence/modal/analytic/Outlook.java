package com.rinitec.algerieoffice.persistence.modal.analytic;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "outlooks")
public class Outlook implements Serializable {
	private static final long serialVersionUID = -7137414289488886136L;
	
	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long getPhone;
	
	@Column(nullable = false)
	private Long appPhone;
	
	@Column(nullable = false)
	private Long sendMail;
	
	public Outlook() {
		this.getPhone = this.appPhone = this.sendMail = 0L;
	}
	
	public Outlook(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getGetPhone() {
		return getPhone;
	}

	public void setGetPhone(Long getPhone) {
		this.getPhone = getPhone;
	}

	public Long getAppPhone() {
		return appPhone;
	}

	public void setAppPhone(Long appPhone) {
		this.appPhone = appPhone;
	}

	public Long getSendMail() {
		return sendMail;
	}

	public void setSendMail(Long sendMail) {
		this.sendMail = sendMail;
	}

	@Override
	public String toString() {
		return "Outlook [companyId=" + companyId + ", getPhone=" + getPhone + ", appPhone=" + appPhone + ", sendMail="
				+ sendMail + "]";
	}

}
