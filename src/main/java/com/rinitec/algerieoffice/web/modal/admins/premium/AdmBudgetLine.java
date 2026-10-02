package com.rinitec.algerieoffice.web.modal.admins.premium;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Budget;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmBudgetLine implements Serializable {
	private static final long serialVersionUID = 3109175223585583582L;
	
	private final Long id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String companyURL;
	private final long emails;
	private final int sendings;
	
	public AdmBudgetLine(final Budget budget, final Company company, final String url) {
		this.id = budget.getCompanyId();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.emails = budget.getEmails();
		this.sendings = budget.getSendings();
	}

	public Long getId() {
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

	public String getCompanyURL() {
		return companyURL;
	}

	public long getEmails() {
		return emails;
	}

	public int getSendings() {
		return sendings;
	}
	
	public String getFormattedBudget(int index) {
		switch(index) {
		case 1: return ParseUtil.getFormattedCount(emails);
		default: return ParseUtil.getFormattedOrder(sendings);
		}
	}

	@Override
	public String toString() {
		return "AdmBudgetLine [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", activity="
				+ activity + ", companyURL=" + companyURL + ", emails=" + emails + ", sendings=" + sendings + "]";
	}

}
