package com.rinitec.algerieoffice.web.modal.user.dashboard;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityComment;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class DashboardComment implements Serializable {
	private static final long serialVersionUID = 5872318956192000947L;

	private final String message;
	private final String actualityURL;
	private final DateTime postedDate;
	
	public DashboardComment(final ActualityComment comment) {
		this.message = comment.getMessage();
		this.actualityURL = ConstraintesURL.getActualiteFavoriteURL(comment.getActualityId().toString()).concat("?info=comments");
		this.postedDate = comment.getPostedDate();
	}

	public String getMessage() {
		return message;
	}

	public String getActualityURL() {
		return actualityURL;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	@Override
	public String toString() {
		return "DashboardComment [message=" + message + ", actualityURL=" + actualityURL + ", postedDate=" + postedDate + "]";
	}

}
