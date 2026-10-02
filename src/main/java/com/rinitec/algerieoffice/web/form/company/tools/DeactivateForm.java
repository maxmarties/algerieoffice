package com.rinitec.algerieoffice.web.form.company.tools;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class DeactivateForm implements Serializable {
	private static final long serialVersionUID = -668989123903559953L;
	
	@NotNull
	private Long companyId;
	
	@ValidChose
	private Integer reason;
	
	private String observation;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_PASSWORD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String password;
	
	public DeactivateForm() {
	}
	
	public DeactivateForm(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getReason() {
		return reason;
	}

	public void setReason(Integer reason) {
		this.reason = reason;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "DeactivateForm [companyId=" + companyId + ", reason=" + reason + ", observation=" + observation
				+ ", password=" + password + "]";
	}

}
