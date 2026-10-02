package com.rinitec.algerieoffice.web.modal;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;

public class CurrentVisitor implements Serializable {
	private static final long serialVersionUID = -6245000641517732616L;
	
	private final boolean hasLeader;
	private final Integer favorite;
	private final Boolean liked;
	private final Integer note;
	
	public CurrentVisitor(final boolean hasLeader, final Integer favorite, final Evaluation evaluation) {
		this.hasLeader = hasLeader;
		this.favorite = favorite;
		if(evaluation != null) {
			this.liked = evaluation.getLiked();
			this.note = evaluation.getNote();
		} else {
			this.liked = null;
			this.note = null;
		}
	}

	public boolean isHasLeader() {
		return hasLeader;
	}

	public Integer getFavorite() {
		return favorite;
	}
	
	public Boolean getLiked() {
		return liked;
	}
	
	public Integer getNote() {
		return note;
	}

	@Override
	public String toString() {
		return "CurrentVisitor [hasLeader=" + hasLeader + ", favorite=" + favorite + ", liked=" + liked + ", note=" + note + "]";
	}

}
