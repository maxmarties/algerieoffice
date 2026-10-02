package com.rinitec.algerieoffice.web.modal.admins.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmGuestLine implements Serializable {
	private static final long serialVersionUID = -8657995256168943927L;
	
	private final String id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String username;
	private final String message;
	private final String postedDate;
	private final boolean consulted;
	private final int type;
	
	public AdmGuestLine(final GuestDocument guestDocument, final Company company) {
		this.id = guestDocument.getId().toString();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.username = guestDocument.getUsername();
		this.message = guestDocument.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestDocument.getPostedDate());
		this.consulted = guestDocument.isConsulted();
		this.type = ParseUtil.parseTypeDocument(guestDocument.getType());
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

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "AdmGuestLine [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", activity="
				+ activity + ", username=" + username + ", message=" + message + ", postedDate=" + postedDate
				+ ", consulted=" + consulted + ", type=" + type + "]";
	}

}
