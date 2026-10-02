package com.rinitec.algerieoffice.web.modal.company.manage;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AppearanceView implements Serializable {
	private static final long serialVersionUID = 3800674383819611159L;
	
	private final String urlAvatar;
	private final String address;
	private final Integer wilaya;
	private final String activity;
	private final String viewDate;
	
	public AppearanceView(final Company company) {
		final List<CompanyAddress> companiesAddress = new ArrayList<CompanyAddress>(company.getAddresses());
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company
				: "/static/picts/avatars/company-min.jpg";
		this.address = companiesAddress.get(0).getAddress().concat(", ").concat(companiesAddress.get(0).getPostal());
		this.wilaya = companiesAddress.get(0).getWilaya();
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.viewDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(new DateTime(Date.from(Instant.now())));
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getAddress() {
		return address;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public String getActivity() {
		return activity;
	}

	public String getViewDate() {
		return viewDate;
	}

	@Override
	public String toString() {
		return "AppearanceView [urlAvatar=" + urlAvatar + ", address=" + address + ", wilaya=" + wilaya + ", activity="
				+ activity + ", viewDate=" + viewDate + "]";
	}

}
