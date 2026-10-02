package com.rinitec.algerieoffice.web.form.feedback;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class AppointmentForm implements Serializable {
	private static final long serialVersionUID = 5453651829592889202L;
	
	@NotNull
	private Long companyAppoint;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String motifAppoint;
	
	@ValidChose
	@NotNull
	private Integer degreeAppoint;
	
	private String forDateAppoint;
	private String toDateAppoint;
	
	private Integer periodAppoint;
	
	public AppointmentForm() {
		this.degreeAppoint = 1;
	}
	
	public AppointmentForm(final Long companyAppoint) {
		this.degreeAppoint = 1;
		this.companyAppoint = companyAppoint;
	}

	public Long getCompanyAppoint() {
		return companyAppoint;
	}

	public void setCompanyAppoint(Long companyAppoint) {
		this.companyAppoint = companyAppoint;
	}

	public String getMotifAppoint() {
		return motifAppoint;
	}

	public void setMotifAppoint(String motifAppoint) {
		this.motifAppoint = motifAppoint;
	}
	
	public Integer getDegreeAppoint() {
		return degreeAppoint;
	}
	
	public void setDegreeAppoint(Integer degreeAppoint) {
		this.degreeAppoint = degreeAppoint;
	}

	public String getForDateAppoint() {
		return forDateAppoint;
	}

	public void setForDateAppoint(String forDateAppoint) {
		this.forDateAppoint = forDateAppoint;
	}

	public String getToDateAppoint() {
		return toDateAppoint;
	}

	public void setToDateAppoint(String toDateAppoint) {
		this.toDateAppoint = toDateAppoint;
	}

	public Integer getPeriodAppoint() {
		return periodAppoint;
	}
	
	public void setPeriodAppoint(Integer periodAppoint) {
		this.periodAppoint = periodAppoint;
	}

	@Override
	public String toString() {
		return "AppointmentForm [companyAppoint=" + companyAppoint + ", motifAppoint=" + motifAppoint
				+ ", degreeAppoint=" + degreeAppoint + ", forDateAppoint=" + forDateAppoint + ", toDateAppoint="
				+ toDateAppoint + ", periodAppoint=" + periodAppoint + "]";
	}

}
