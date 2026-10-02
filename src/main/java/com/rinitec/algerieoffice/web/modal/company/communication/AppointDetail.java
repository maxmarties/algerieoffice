package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;

public class AppointDetail implements Serializable {
	private static final long serialVersionUID = -6975320560519501681L;
	
	private final String motif;
	private final String postedDate;
	private final String forDate;
	private final String toDate;
	private final Integer period;
	private final Integer degree;
	private final UserAccountMini userMini;
	
	public AppointDetail(final Appointment appointment, final UserAccountMini userMini) {
		this.motif = appointment.getMotif();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(appointment.getPostedDate());
		this.forDate = appointment.getForDate() != null ? DateTimeFormat.forPattern("dd/MM/yyyy").print(appointment.getForDate()) : null;
		this.toDate = appointment.getToDate() != null ? DateTimeFormat.forPattern("dd/MM/yyyy").print(appointment.getToDate()) : null;
		this.period = appointment.getPeriod();
		this.degree = appointment.getDegree();
		this.userMini = userMini;
	}

	public String getMotif() {
		return motif;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public String getForDate() {
		return forDate;
	}

	public String getToDate() {
		return toDate;
	}

	public Integer getPeriod() {
		return period;
	}

	public Integer getDegree() {
		return degree;
	}

	public UserAccountMini getUserMini() {
		return userMini;
	}

	@Override
	public String toString() {
		return "AppointDetail [motif=" + motif + ", postedDate=" + postedDate + ", forDate=" + forDate + ", toDate="
				+ toDate + ", period=" + period + ", degree=" + degree + ", userMini=" + userMini + "]";
	}

}
