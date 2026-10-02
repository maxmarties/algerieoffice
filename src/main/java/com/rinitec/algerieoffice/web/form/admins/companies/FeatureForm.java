package com.rinitec.algerieoffice.web.form.admins.companies;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.web.validator.ValidCalendar;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class FeatureForm implements Serializable {
	private static final long serialVersionUID = 1195976528666913610L;
	
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
	
	public FeatureForm() {
	}
	
	public FeatureForm(final Long companyId) {
		this.companyId = companyId;
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

	@Override
	public String toString() {
		return "FeatureForm [companyId=" + companyId + ", pass=" + pass + ", createDate=" + createDate + ", expiryDate="
				+ expiryDate + "]";
	}

}
