package com.rinitec.algerieoffice.web.modal.user.dashboard;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalUser;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;

public class DashboardHistory implements Serializable {
	private static final long serialVersionUID = -441950242182327225L;
	
	private final String icon;
	private final String action;
	private final DateTime postedDate;
	
	public DashboardHistory(final JournalUser journalUser) {
		this.icon = ConstraintesJournal.USER_ICON_JOURNAL[Integer.valueOf(journalUser.getAction().split("\\.")[0])];
		this.action = journalUser.getAction();
		this.postedDate = journalUser.getPostedDate();
	}

	public String getIcon() {
		return icon;
	}

	public String getAction() {
		return action;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	@Override
	public String toString() {
		return "DashboardHistory [icon=" + icon + ", action=" + action + ", postedDate=" + postedDate + "]";
	}

}
