package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;

public class EvaluationLine implements Serializable {
	private static final long serialVersionUID = -5786792825190024851L;
	
	private final String id;
	private final String postedDate;
	private final boolean liked;
	private final int note;
	private final UserAccountMini userMini;
	
	public EvaluationLine(final User user, final String pseudo, final Boolean isCollaborator, final Boolean isEnabled, 
			final Long identities, final Evaluation evaluation, final Long blacked) {
		this.id = evaluation.getId().toString();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(evaluation.getPostedDate());
		this.liked = evaluation.getLiked();
		this.note = evaluation.getNote();
		this.userMini = new UserAccountMini(user, pseudo, isCollaborator, isEnabled, identities, blacked);
	}
	
	public String getId() {
		return id;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public boolean isLiked() {
		return liked;
	}

	public int getNote() {
		return note;
	}
	
	public UserAccountMini getUserMini() {
		return userMini;
	}

	@Override
	public String toString() {
		return "EvaluationLine [id=" + id + ", postedDate=" + postedDate + ", liked=" + liked + ", note=" + note
				+ ", userMini=" + userMini + "]";
	}

}
