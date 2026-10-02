package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.web.modal.user.UserAccountMini;

public class NoticeLine implements Serializable {
	private static final long serialVersionUID = 7695688579010105003L;
	
	private final String id;
	private final String title;
	private final String message;
	private final String postedDate;
	private final String approuvedBy;
	private final boolean autorized;
	private final boolean approuved;
	private final UserAccountMini userMini;
	
	public NoticeLine(final User user, final String pseudo, final Boolean isCollaborator, final Boolean isEnabled, 
			final Long identities, final Notice notice, final String autor, final Long blacked) {
		this.id = notice.getId().toString();
		this.title = notice.getTitle();
		this.message = notice.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(notice.getPostedDate());
		this.approuvedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.autorized = notice.getAutorised();
		this.approuved = notice.isApprouved();
		this.userMini = new UserAccountMini(user, pseudo, isCollaborator, isEnabled, identities, blacked);
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public String getApprouvedBy() {
		return approuvedBy;
	}

	public boolean isAutorized() {
		return autorized;
	}

	public boolean isApprouved() {
		return approuved;
	}

	public UserAccountMini getUserMini() {
		return userMini;
	}

	@Override
	public String toString() {
		return "NoticeLine [id=" + id + ", title=" + title + ", message=" + message + ", postedDate=" + postedDate
				+ ", approuvedBy=" + approuvedBy + ", autorized=" + autorized + ", approuved=" + approuved
				+ ", userMini=" + userMini + "]";
	}

}
