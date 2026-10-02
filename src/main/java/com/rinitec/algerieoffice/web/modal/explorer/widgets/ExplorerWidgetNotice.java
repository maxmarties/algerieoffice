package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;

public class ExplorerWidgetNotice implements Serializable {
	private static final long serialVersionUID = 4379065504439889997L;
	
	private final String title;
	private final String message;
	private final DateTime postedDate;
	private final UserAccountMini userMini;
	
	public ExplorerWidgetNotice(final Notice notice, final User user, final String pseudo, final Boolean isCollaborator, final Boolean isEnabled, final Long identities) {
		this.title = notice.getTitle();
		this.message = notice.getMessage();
		this.postedDate = notice.getPostedDate();
		this.userMini = new UserAccountMini(user, pseudo, isCollaborator, isEnabled, identities, null);
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public UserAccountMini getUserMini() {
		return userMini;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetNotice [title=" + title + ", message=" + message + ", postedDate=" + postedDate
				+ ", userMini=" + userMini + "]";
	}

}
