package com.rinitec.algerieoffice.web.modal.company.team;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.team.Guest;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class GuestLine implements Serializable {
	private static final long serialVersionUID = 7083493571993392825L;
	
	private final Long id;
	private final String username;
	private final String pseudoURL;
	private final String urlAvatar;
	private final String email;
	private final String guestDate;
	private final String guestBy;
	private final int role;
	
	public GuestLine(final User user, final String pseudo, final String autor, final Guest guest) {
		this.id = guest.getId();
		this.username = user.getDisplayName();
		this.pseudoURL = ConstraintesURL.URL_PROFILES.concat("/").concat(pseudo);
		this.urlAvatar = user.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=40&height=40"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.email = user.getEmail();
		this.guestDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guest.getGuestDate());
		this.guestBy = autor;
		this.role = guest.getRole();
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPseudoURL() {
		return pseudoURL;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getEmail() {
		return email;
	}

	public String getGuestDate() {
		return guestDate;
	}

	public String getGuestBy() {
		return guestBy;
	}

	public int getRole() {
		return role;
	}

	@Override
	public String toString() {
		return "GuestLine [id=" + id + ", username=" + username + ", pseudoURL=" + pseudoURL + ", urlAvatar="
				+ urlAvatar + ", email=" + email + ", guestDate=" + guestDate + ", guestBy=" + guestBy + ", role="
				+ role + "]";
	}

}
