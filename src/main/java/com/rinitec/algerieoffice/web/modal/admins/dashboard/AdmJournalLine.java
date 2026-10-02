package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.journal.JournalAdmin;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesJournal;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmJournalLine implements Serializable {
	private static final long serialVersionUID = 7141135749021538470L;
	
	private final String id;
	private final String icon;
	private final String urlAvatar;
	private final String username;
	private final String rolename;
	private final String postedDate;
	private final String action;
	private final String element;
	
	public AdmJournalLine(final JournalAdmin journalAdmin, final User user) {
		this.id = journalAdmin.getId().toString();
		this.icon = ConstraintesJournal.ADMIN_ICON_JOURNAL[Integer.valueOf(journalAdmin.getAction().split("\\.")[0])];
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32" 
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.rolename = ParseUtil.getRoleMessage(user.getRoles());
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(journalAdmin.getPostedDate());
		this.action = journalAdmin.getAction();
		this.element = !StringUtils.isEmpty(journalAdmin.getElement()) ? journalAdmin.getElement() : "--";
	}

	public String getId() {
		return id;
	}

	public String getIcon() {
		return icon;
	}

	public String getUrlAvatar() {
		return urlAvatar;
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
		return "AdmJournalLine [id=" + id + ", icon=" + icon + ", urlAvatar=" + urlAvatar + ", username=" + username
				+ ", rolename=" + rolename + ", postedDate=" + postedDate + ", action=" + action + ", element="
				+ element + "]";
	}

}
