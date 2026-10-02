package com.rinitec.algerieoffice.web.modal.user.dashboard;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalUser;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;

public class HistoryLine implements Serializable {
	private static final long serialVersionUID = -4797944975781489777L;
	
	private final String id;
	private final String icon;
	private final String action;
	private final String element;
	private final String postedDate;
	
	public HistoryLine(final JournalUser journalUser) {
		this.id = journalUser.getId().toString();
		this.icon = ConstraintesJournal.USER_ICON_JOURNAL[Integer.valueOf(journalUser.getAction().split("\\.")[0])];
		this.action = journalUser.getAction();
		this.element = !StringUtils.isEmpty(journalUser.getElement()) ? journalUser.getElement() : "--";
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(journalUser.getPostedDate());
	}

	public String getId() {
		return id;
	}

	public String getIcon() {
		return icon;
	}

	public String getAction() {
		return action;
	}

	public String getElement() {
		return element;
	}

	public String getPostedDate() {
		return postedDate;
	}

	@Override
	public String toString() {
		return "HistoryLine [id=" + id + ", icon=" + icon + ", action=" + action + ", element=" + element
				+ ", postedDate=" + postedDate + "]";
	}

}
