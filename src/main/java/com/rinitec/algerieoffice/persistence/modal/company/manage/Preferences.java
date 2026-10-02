package com.rinitec.algerieoffice.persistence.modal.company.manage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "preferences")
public class Preferences implements Serializable {
	private static final long serialVersionUID = -8144546473350875350L;

	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long messengerId;
	
	@Column(nullable = false, length = 7)
	private String communications;
	
	@Column(nullable = false, length = 6)
	private String perspects;
	
	@Column(nullable = false, length = 5)
	private String mails;
	
	private boolean hasMessenger;
	
	public Preferences() {
	}
	
	public Preferences(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getMessengerId() {
		return messengerId;
	}

	public void setMessengerId(Long messengerId) {
		this.messengerId = messengerId;
	}

	public String getCommunications() {
		return communications;
	}

	public void setCommunications(String communications) {
		this.communications = communications;
	}

	public String getPerspects() {
		return perspects;
	}

	public void setPerspects(String perspects) {
		this.perspects = perspects;
	}

	public String getMails() {
		return mails;
	}

	public void setMails(String mails) {
		this.mails = mails;
	}

	public boolean isHasMessenger() {
		return hasMessenger;
	}

	public void setHasMessenger(boolean hasMessenger) {
		this.hasMessenger = hasMessenger;
	}

	@Override
	public String toString() {
		return "Preferences [companyId=" + companyId + ", messengerId=" + messengerId + ", communications="
				+ communications + ", perspects=" + perspects + ", mails=" + mails + ", hasMessenger=" + hasMessenger + "]";
	}
	
}
