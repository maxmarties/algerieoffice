package com.rinitec.algerieoffice.web.modal.admins.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmNoticeLine implements Serializable {
	private static final long serialVersionUID = 7159057156838629280L;
	
	private final String id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String username;
	private final String title;
	private final String message;
	private final String postedDate;
	private final boolean autorised;
	private final boolean approuved;
	
	public AdmNoticeLine(final Notice notice, final Company company, final String username) {
		this.id = notice.getId().toString();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.username = username;
		this.title = notice.getTitle();
		this.message = notice.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(notice.getPostedDate());
		this.autorised = notice.getAutorised();
		this.approuved = notice.isApprouved();
	}

	public String getId() {
		return id;
	}

	public String getTradename() {
		return tradename;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getActivity() {
		return activity;
	}

	public String getUsername() {
		return username;
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
	
	public boolean isAutorised() {
		return autorised;
	}

	public boolean isApprouved() {
		return approuved;
	}

	@Override
	public String toString() {
		return "AdmNoticeLine [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", activity="
				+ activity + ", username=" + username + ", title=" + title + ", message=" + message + ", postedDate="
				+ postedDate + ", autorised=" + autorised + ", approuved=" + approuved + "]";
	}

}
