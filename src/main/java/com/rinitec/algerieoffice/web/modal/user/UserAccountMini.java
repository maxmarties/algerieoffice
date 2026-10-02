package com.rinitec.algerieoffice.web.modal.user;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class UserAccountMini implements Serializable {
	private static final long serialVersionUID = -8121333340529174954L;
	
	private final Long id;
	private final String username;
	private final String pseudoURL;
	private final String urlAvatar;
	private final boolean hasPro;
	private final boolean hasLocked;
	private final int verified;
	
	public UserAccountMini(final User user, final String pseudo, final Boolean isCollaborator, final Boolean isEnabled, final Long identities) {
		this.id = user.getId();
		this.username = user.getDisplayName();
		this.pseudoURL = ConstraintesURL.URL_PROFILES.concat("/").concat(pseudo);
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=48&height=48"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.hasPro = user.getCompanyId() != null || (isCollaborator != null && isCollaborator);
		this.verified = (int) ((user.isEnabled() ? 1 : 0) + (isEnabled != null && isEnabled ? 1 : 0) + identities);
		this.hasLocked = false;
	}

	public UserAccountMini(final User user, final String pseudo, final Boolean isCollaborator, final Boolean isEnabled, 
			final Long identities, final Long blackedList) {
		this.id = user.getId();
		this.username = user.getDisplayName();
		this.pseudoURL = ConstraintesURL.URL_PROFILES.concat("/").concat(pseudo);
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=48&height=48"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.hasPro = user.getCompanyId() != null || (isCollaborator != null && isCollaborator);
		this.verified = (int) ((user.isEnabled() ? 1 : 0) + (isEnabled != null && isEnabled ? 1 : 0) + identities);
		this.hasLocked = blackedList != null;
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

	public boolean isHasPro() {
		return hasPro;
	}
	
	public boolean isHasLocked() {
		return hasLocked;
	}

	public int getVerified() {
		return verified;
	}

	@Override
	public String toString() {
		return "UserAccountMini [id=" + id + ", username=" + username + ", pseudoURL=" + pseudoURL + ", urlAvatar="
				+ urlAvatar + ", hasPro=" + hasPro + ", hasLocked=" + hasLocked + ", verified=" + verified + "]";
	}

}
