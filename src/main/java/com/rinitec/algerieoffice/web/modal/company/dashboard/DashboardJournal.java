package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalCompany;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;

public class DashboardJournal implements Serializable {
	private static final long serialVersionUID = -3264492882398666065L;
	
	private final Long userId;
	private final String icon;
	private final String username;
	private final DateTime postedDate;
	private final String action;
	private final String element;
	
	public DashboardJournal(final JournalCompany journalCompany, final Long userId, final String username) {
		this.userId = userId;
		this.icon = ConstraintesJournal.COMPANY_ICON_JOURNAL[Integer.valueOf(journalCompany.getAction().split("\\.")[0])];
		this.username = username;
		this.postedDate = journalCompany.getPostedDate();
		this.action = journalCompany.getAction();
		this.element = !StringUtils.isEmpty(journalCompany.getElement()) ? journalCompany.getElement() : null;
	}

	public Long getUserId() {
		return userId;
	}

	public String getIcon() {
		return icon;
	}

	public String getUsername() {
		return username;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public String getAction() {
		return action;
	}
	
	public String getElement() {
		return element;
	}

	@Override
	public String toString() {
		return "DashboardJournal [userId=" + userId + ", icon=" + icon + ", username=" + username + ", postedDate="
				+ postedDate + ", action=" + action + ", element=" + element + "]";
	}

}
