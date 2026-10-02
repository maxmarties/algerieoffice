package com.rinitec.algerieoffice.web.modal.publics.companies;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CompanyLogoMini implements Serializable {
	private static final long serialVersionUID = -5683911911197426993L;
	
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	
	public CompanyLogoMini(final Long id, final String tradename, final String url) {
		this.tradename = tradename;
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = ConstraintesURL.URL_AVATARS + "?postedId=" + id + "&type=" + AvatarType.company;
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

	@Override
	public String toString() {
		return "CompanyLogoMini [tradename=" + tradename + ", companyURL=" + companyURL + ", urlAvatar=" + urlAvatar + "]";
	}

}
