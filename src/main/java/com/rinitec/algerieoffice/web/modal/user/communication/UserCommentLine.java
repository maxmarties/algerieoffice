package com.rinitec.algerieoffice.web.modal.user.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityComment;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.company.CompanyAccountMini;

public class UserCommentLine implements Serializable {
	private static final long serialVersionUID = -7716616064666527137L;
	
	private final String id;
	private final String title;
	private final String actualityURL;
	private final String message;
	private final String postedDate;
	private final Long likeCount;
	private final CompanyAccountMini companyMini;
	
	public UserCommentLine(final ActualityComment comment, final Company company, final String url, final String title, final Long likeCount) {
		this.id = comment.getId().toString();
		this.title = title;
		this.actualityURL = ConstraintesURL.getActualiteFavoriteURL(comment.getActualityId().toString());
		this.message = comment.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(comment.getPostedDate());
		this.likeCount = likeCount;
		this.companyMini = new CompanyAccountMini(company, url);
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getActualityURL() {
		return actualityURL;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public CompanyAccountMini getCompanyMini() {
		return companyMini;
	}

	@Override
	public String toString() {
		return "UserCommentLine [id=" + id + ", title=" + title + ", actualityURL=" + actualityURL + ", message="
				+ message + ", postedDate=" + postedDate + ", likeCount=" + likeCount + ", companyMini=" + companyMini + "]";
	}

}
