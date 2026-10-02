package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Collaborator;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;

public class CollaboratorLine implements Serializable {
	private static final long serialVersionUID = 5487969214119268256L;
	
	private final String id;
	private final String function;
	private final String postedDate;
	private final String approuvedBy;
	private final boolean approuved;
	private final UserAccountMini userMini;
	
	public CollaboratorLine(final User user, final String pseudo, final Boolean isCollaborator, final Boolean isEnabled, 
			final Long identities, final Collaborator collaborator, final String autor, final Long blacked) {
		this.id = collaborator.getId().toString();
		this.function = collaborator.getFunction();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(collaborator.getPostedDate());
		this.approuvedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.approuved = collaborator.isApprouved();
		this.userMini = new UserAccountMini(user, pseudo, isCollaborator, isEnabled, identities, blacked);
	}

	public String getId() {
		return id;
	}

	public String getFunction() {
		return function;
	}

	public String getPostedDate() {
		return postedDate;
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
		return "CollaboratorLine [id=" + id + ", function=" + function + ", postedDate=" + postedDate + ", approuvedBy="
				+ approuvedBy + ", approuved=" + approuved + ", userMini=" + userMini + "]";
	}

}
