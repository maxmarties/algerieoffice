package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;

public class AppointLine implements Serializable {
	private static final long serialVersionUID = 6221361318171213950L;
	
	private final String id;
	private final String motif;
	private final String postedDate;
	private final String appointDate;
	private final String approuvedBy;
	private final boolean approuved;
	private final UserAccountMini userMini;
	
	public AppointLine(final User user, final String pseudo, final Boolean isCollaborator, final Boolean isEnabled, 
			final Long identities, final Appointment appointment, final String autor, final Long blacked) {
		this.id = appointment.getId().toString();
		this.motif = appointment.getMotif();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(appointment.getPostedDate());
		this.appointDate = appointment.getAppointDate() != null ? DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(appointment.getAppointDate()) : "-";
		this.approuvedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.approuved = appointment.isApprouved();
		this.userMini = new UserAccountMini(user, pseudo, isCollaborator, isEnabled, identities, blacked);
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

	public String getApprouvedBy() {
		return approuvedBy;
	}

	public boolean isApprouved() {
		return approuved;
	}

	public UserAccountMini getUserMini() {
		return userMini;
	}

	@Override
	public String toString() {
		return "AppointLine [id=" + id + ", motif=" + motif + ", postedDate=" + postedDate + ", appointDate="
				+ appointDate + ", approuvedBy=" + approuvedBy + ", approuved=" + approuved + ", userMini=" + userMini + "]";
	}

}
