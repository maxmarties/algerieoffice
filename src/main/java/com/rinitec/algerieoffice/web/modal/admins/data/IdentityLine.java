package com.rinitec.algerieoffice.web.modal.admins.data;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class IdentityLine implements Serializable {
	private static final long serialVersionUID = 7804727953415464341L;
	
	private final Long id;
	private final String iconUrl;
	private final String tradename;
	private final String requestedDate;
	private final String createdDate;
	private final String fileUrl;
	private final boolean hasConsulted;
	
	public IdentityLine(final Long id, final boolean hasAvatar, final String tradename, final DateTime requestedDate, 
			final DateTime createdDate, final boolean hasConsulted) {
		this.id = id;
		this.iconUrl = hasAvatar ? ConstraintesURL.URL_AVATARS + "?postedId=" + id + "&type=" + AvatarType.company + "&width=32&height=32"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.tradename = tradename;
		this.requestedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(requestedDate);
		this.createdDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(createdDate);
		this.fileUrl = ConstraintesURL.URL_AVATARS + "?postedId=" + id + "&type=" + AvatarType.identity;
		this.hasConsulted = hasConsulted;
	}

	public Long getId() {
		return id;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getTradename() {
		return tradename;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getFileUrl() {
		return fileUrl;
	}
	
	public boolean isHasConsulted() {
		return hasConsulted;
	}

	@Override
	public String toString() {
		return "IdentityLine [id=" + id + ", iconUrl=" + iconUrl + ", tradename=" + tradename + ", requestedDate="
				+ requestedDate + ", createdDate=" + createdDate + ", fileUrl=" + fileUrl + ", hasConsulted="
				+ hasConsulted + "]";
	}

}
