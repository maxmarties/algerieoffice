package com.rinitec.algerieoffice.web.modal.company.manage;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class WidgetView implements Serializable {
	private static final long serialVersionUID = -857837909077192535L;
	
	private final String urlLogo;
	private final String address;
	private final String activity;
	private final String language;
	
	public WidgetView(final Company company) {
		final CompanyAddress companyAddress  = (CompanyAddress) company.getAddresses().toArray()[0];
		this.urlLogo = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company
				: "/static/picts/avatars/company-min.jpg";
		this.address = companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal());
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.language = company.getLang();
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public String getAddress() {
		return address;
	}
	
	public String getActivity() {
		return activity;
	}

	public String getLanguage() {
		return language;
	}

	@Override
	public String toString() {
		return "WidgetView [urlLogo=" + urlLogo + ", address=" + address + ", activity=" + activity + ", language="
				+ language + "]";
	}

}
