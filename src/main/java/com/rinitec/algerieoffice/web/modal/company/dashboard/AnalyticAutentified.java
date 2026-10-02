package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AnalyticAutentified implements Serializable {
	private static final long serialVersionUID = -7617151531826840743L;
	
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String activity;
	private final String address;
	private final String postal;
	private final int wilaya;
	private final String accessDate;
	
	public AnalyticAutentified(final Company company, final String url, final DateTime accessDate) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.address = companyAddress.getAddress();
		this.postal = companyAddress.getPostal();
		this.wilaya = companyAddress.getWilaya();
		this.accessDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(accessDate);
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
	
	public String getAddress() {
		return address;
	}

	public String getPostal() {
		return postal;
	}

	public int getWilaya() {
		return wilaya;
	}

	public String getAccessDate() {
		return accessDate;
	}

	@Override
	public String toString() {
		return "AnalyticAutentified [tradename=" + tradename + ", companyURL=" + companyURL + ", urlAvatar=" + urlAvatar
				+ ", activity=" + activity + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya
				+ ", accessDate=" + accessDate + "]";
	}

}
