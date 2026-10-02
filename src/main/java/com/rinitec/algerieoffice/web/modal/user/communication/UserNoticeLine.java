package com.rinitec.algerieoffice.web.modal.user.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Notice;
import com.rinitec.algerieoffice.web.modal.company.CompanyAccountMini;

public class UserNoticeLine implements Serializable {
	private static final long serialVersionUID = -6271955979087085731L;
	
	private final String id;
	private final String title;
	private final String message;
	private final String postedDate;
	private final boolean autorized;
	private final boolean approuved;
	private final CompanyAccountMini companyMini;
	
	public UserNoticeLine(final Company company, final String url, final Notice notice) {
		this.id = notice.getId().toString();
		this.title = notice.getTitle();
		this.message = notice.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(notice.getPostedDate());
		this.autorized = notice.getAutorised();
		this.approuved = notice.isApprouved();
		this.companyMini = new CompanyAccountMini(company, url);
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

	public boolean isAutorized() {
		return autorized;
	}

	public boolean isApprouved() {
		return approuved;
	}

	public CompanyAccountMini getCompanyMini() {
		return companyMini;
	}

	@Override
	public String toString() {
		return "UserNoticeLine [id=" + id + ", title=" + title + ", message=" + message + ", postedDate=" + postedDate
				+ ", autorized=" + autorized + ", approuved=" + approuved + ", companyMini=" + companyMini + "]";
	}
	
}
