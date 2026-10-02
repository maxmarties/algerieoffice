package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.journal.JournalCompany;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class JournalAccess implements Serializable {
	private static final long serialVersionUID = -796646223131249412L;
	
	private final String urlAvatar;
	private final String username;
	private final String time;
	private final String device;
	
	public JournalAccess(final User user, final JournalCompany journalCompany) {
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=38&height=38"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.time = DateTimeFormat.forPattern("HH:mm").print(journalCompany.getPostedDate());
		this.device = journalCompany.getElement();
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getTime() {
		return time;
	}

	public String getDevice() {
		return device;
	}

	@Override
	public String toString() {
		return "JournalAccess [urlAvatar=" + urlAvatar + ", username=" + username + ", time=" + time + ", device="
				+ device + "]";
	}
	
}
