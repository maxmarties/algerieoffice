package com.rinitec.algerieoffice.web.modal.company;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CompanyAccountMini implements Serializable {
	private static final long serialVersionUID = -3741292796826180290L;
	
	private final Long id;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String activity;
	
	public CompanyAccountMini(final Company company, final String url) {
		this.id = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=40&height=40"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
	}

	public Long getId() {
		return id;
	}

	public String getTradename() {
		return tradename;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getActivity() {
		return activity;
	}

	@Override
	public String toString() {
		return "CompanyAccountMini [id=" + id + ", tradename=" + tradename + ", companyURL=" + companyURL
				+ ", urlAvatar=" + urlAvatar + ", activity=" + activity + "]";
	}

}
