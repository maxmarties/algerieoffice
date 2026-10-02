package com.rinitec.algerieoffice.web.form.company.tools;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class BlacklistForm implements Serializable {
	private static final long serialVersionUID = 2091967972424069864L;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	private Long userId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String reason;
	
	private String username;
	
	public BlacklistForm() {
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "BlacklistForm [companyId=" + companyId + ", userId=" + userId + ", reason=" + reason + ", username="
				+ username + "]";
	}

}
