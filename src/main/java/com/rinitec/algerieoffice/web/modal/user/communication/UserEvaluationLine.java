package com.rinitec.algerieoffice.web.modal.user.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Evaluation;
import com.rinitec.algerieoffice.web.modal.company.CompanyAccountMini;

public class UserEvaluationLine implements Serializable {
	private static final long serialVersionUID = -4888963013693306703L;
	
	private final String id;
	private final boolean liked;
	private final int note;
	private final String postedDate;
	private final CompanyAccountMini companyMini;
	
	public UserEvaluationLine(final Company company, final String url, final Evaluation evaluation) {
		this.id = evaluation.getId().toString();
		this.liked = evaluation.getLiked();
		this.note = evaluation.getNote();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(evaluation.getPostedDate());
		this.companyMini = new CompanyAccountMini(company, url);
	}

	public String getId() {
		return id;
	}

	public boolean isLiked() {
		return liked;
	}

	public int getNote() {
		return note;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public CompanyAccountMini getCompanyMini() {
		return companyMini;
	}

	@Override
	public String toString() {
		return "UserEvaluationLine [id=" + id + ", liked=" + liked + ", note=" + note + ", postedDate=" + postedDate
				+ ", companyMini=" + companyMini + "]";
	}

}
