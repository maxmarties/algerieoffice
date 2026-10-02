package com.rinitec.algerieoffice.web.form.admins.premium;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.web.validator.ValidCalendar;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class PremiumForm implements Serializable {
	private static final long serialVersionUID = 3497420474243468811L;
	
	@NotNull
	private String premiumId;
	
	@NotNull
	private Long companyId;
	
	@ValidChose
	private Integer pass;
	
	@ValidCalendar
	@NotNull
	private String createDate;
	
	@ValidCalendar
	@NotNull
	private String expiryDate;
	
	private boolean enabled;
	
	public PremiumForm() {
	}
	
	public PremiumForm(final Long companyId, final String orderId) {
		this.companyId = companyId;
		this.premiumId = orderId;
	}
	
	public PremiumForm(final Premium premium) {
		this.premiumId = premium.getId().toString();
		this.companyId = premium.getCompanyId();
		this.pass = premium.getPass();
		this.createDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(premium.getCreateDate());
		this.expiryDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(premium.getExpiryDate());
		this.enabled = premium.getEnabled();
	}
	
	public String getPremiumId() {
		return premiumId;
	}
	
	public void setPremiumId(String premiumId) {
		this.premiumId = premiumId;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "PremiumForm [premiumId=" + premiumId + ", companyId=" + companyId + ", pass=" + pass + ", createDate="
				+ createDate + ", expiryDate=" + expiryDate + ", enabled=" + enabled + "]";
	}

}
