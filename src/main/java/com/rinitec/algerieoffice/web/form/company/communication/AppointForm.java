package com.rinitec.algerieoffice.web.form.company.communication;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.web.validator.ValidCalendar;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class AppointForm implements Serializable {
	private static final long serialVersionUID = -7019602171578930186L;
	
	@NotNull
	private String id;
	
	@ValidCalendar
	@NotNull
	private String appointDate;
	
	@ValidChose
	@NotNull
	private Integer clock;
	
	public AppointForm() {
	}
	
	public AppointForm(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppointDate() {
		return appointDate;
	}

	public void setAppointDate(String appointDate) {
		this.appointDate = appointDate;
	}

	public Integer getClock() {
		return clock;
	}

	public void setClock(Integer clock) {
		this.clock = clock;
	}

	@Override
	public String toString() {
		return "AppointForm [id=" + id + ", appointDate=" + appointDate + ", clock=" + clock + "]";
	}

}
