package com.rinitec.algerieoffice.web.modal.admins.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Appointment;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmAppointLine implements Serializable {
	private static final long serialVersionUID = 4655852143712189442L;
	
	private final String id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String username;
	private final String motif;
	private final String postedDate;
	private final boolean approuved;
	
	public AdmAppointLine(final Appointment appointment, final Company company, final String username) {
		this.id = appointment.getId().toString();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.username = username;
		this.motif = appointment.getMotif();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(appointment.getPostedDate());
		this.approuved = appointment.isApprouved();
	}

	public String getId() {
		return id;
	}

	public String getTradename() {
		return tradename;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getActivity() {
		return activity;
	}

	public String getUsername() {
		return username;
	}

	public String getMotif() {
		return motif;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public boolean isApprouved() {
		return approuved;
	}

	@Override
	public String toString() {
		return "AdmAppointLine [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", activity="
				+ activity + ", username=" + username + ", motif=" + motif + ", postedDate=" + postedDate
				+ ", approuved=" + approuved + "]";
	}

}
