package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.journal.JournalCompany;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;

public class JournalLine implements Serializable {
	private static final long serialVersionUID = 4492423387030912581L;
	
	private final String id;
	private final String icon;
	private final String username;
	private final String rolename;
	private final String postedDate;
	private final String action;
	private final String element;
	
	public JournalLine(final JournalCompany journalCompany, final User user) {
		this.id = journalCompany.getId().toString();
		this.icon = ConstraintesJournal.COMPANY_ICON_JOURNAL[Integer.valueOf(journalCompany.getAction().split("\\.")[0])];
		this.username = user.getDisplayName();
		this.rolename = ParseUtil.getRoleMessage(user.getRoles());
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(journalCompany.getPostedDate());
		this.action = journalCompany.getAction();
		this.element = !StringUtils.isEmpty(journalCompany.getElement()) ? journalCompany.getElement() : "--";
	}

	public String getId() {
		return id;
	}

	public String getIcon() {
		return icon;
	}

	public String getUsername() {
		return username;
	}

	public String getRolename() {
		return rolename;
	}

	public String getPostedDate() {
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
		return "JournalLine [id=" + id + ", icon=" + icon + ", username=" + username + ", rolename=" + rolename
				+ ", postedDate=" + postedDate + ", action=" + action + ", element=" + element + "]";
	}

}
