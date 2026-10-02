package com.rinitec.algerieoffice.web.modal.user.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.web.modal.company.CompanyAccountMini;

public class UserAppointLine implements Serializable {
	private static final long serialVersionUID = -7753222339807844925L;
	
	private final String id;
	private final String motif;
	private final String postedDate;
	private final String appointDate;
	private final boolean approuved;
	private final CompanyAccountMini companyMini;
	
	public UserAppointLine(final Company company, final String url, final Appointment appointment) {
		this.id = appointment.getId().toString();
		this.motif = appointment.getMotif();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(appointment.getPostedDate());
		this.appointDate = appointment.getAppointDate() != null ? DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(appointment.getAppointDate()) : null;
		this.approuved = appointment.isApprouved();
		this.companyMini = new CompanyAccountMini(company, url);
	}

	public String getId() {
		return id;
	}

	public String getMotif() {
		return motif;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public String getAppointDate() {
		return appointDate;
	}

	public boolean isApprouved() {
		return approuved;
	}

	public CompanyAccountMini getCompanyMini() {
		return companyMini;
	}

	@Override
	public String toString() {
		return "UserAppointLine [id=" + id + ", motif=" + motif + ", postedDate=" + postedDate + ", appointDate="
				+ appointDate + ", approuved=" + approuved + ", companyMini=" + companyMini + "]";
	}

}
