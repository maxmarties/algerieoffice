package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteCompany;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class FollowedCompany implements Serializable {
	private static final long serialVersionUID = 7510461064995075122L;
	
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final Integer type;
	private final boolean alert;
	
	public FollowedCompany(final FavoriteCompany favoriteCompany, final Company company, final String url) {
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.URL_COMPANIES.concat("/").concat(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=36&height=36"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.type = favoriteCompany.getType();
		this.alert = favoriteCompany.isAlert();
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

	public Integer getType() {
		return type;
	}

	public boolean isAlert() {
		return alert;
	}

	@Override
	public String toString() {
		return "InboxCompany [tradename=" + tradename + ", companyURL=" + companyURL + ", urlAvatar=" + urlAvatar
				+ ", type=" + type + ", alert=" + alert + "]";
	}

}
