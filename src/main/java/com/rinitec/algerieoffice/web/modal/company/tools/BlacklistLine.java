package com.rinitec.algerieoffice.web.modal.company.tools;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistCompany;
import com.rinitec.algerieoffice.persistence.modal.blacklist.BlacklistMember;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;

public class BlacklistLine implements Serializable {
	private static final long serialVersionUID = 8130202758094486520L;
	
	private final String id;
	private final String reason;
	private final String lockedDate;
	private final String lockedBy;
	private final UserAccountMini userMini;
	
	public BlacklistLine(final User user, final String pseudo, final Boolean isCollaborator, final Boolean isEnabled, 
			final Long identities, final BlacklistCompany blicklist, final String autor) {
		this.id = blicklist.getId().toString();
		this.reason = blicklist.getReason();
		this.lockedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(blicklist.getLockedDate());
		this.lockedBy = autor;
		this.userMini = new UserAccountMini(user, pseudo, isCollaborator, isEnabled, identities, 1L);
	}
	
	public BlacklistLine(final User user, final String pseudo, final Boolean isCollaborator, final Boolean isEnabled, 
			final Long identities, final BlacklistMember blicklist) {
		this.id = blicklist.getId().toString();
		this.reason = blicklist.getReason();
		this.lockedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(blicklist.getLockedDate());
		this.userMini = new UserAccountMini(user, pseudo, isCollaborator, isEnabled, identities, 1L);
		this.lockedBy = null;
	}

	public String getId() {
		return id;
	}

	public String getReason() {
		return reason;
	}

	public String getLockedDate() {
		return lockedDate;
	}

	public String getLockedBy() {
		return lockedBy;
	}

	public UserAccountMini getUserMini() {
		return userMini;
	}

	@Override
	public String toString() {
		return "BlacklistLine [id=" + id + ", reason=" + reason + ", lockedDate=" + lockedDate + ", lockedBy="
				+ lockedBy + ", userMini=" + userMini + "]";
	}

}
