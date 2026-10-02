package com.rinitec.algerieoffice.web.modal.admins.companies;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmCompanyInfo implements Serializable {
	private static final long serialVersionUID = 1522454089217836989L;
	
	private final String tradename;
	private final String urlAvatar;
	private final String companyURL;
	private final String activity;
	private final String address;
	private final String postal;
	private final int wilaya;
	
	public AdmCompanyInfo(final Company company, final String url) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.URL_COMPANIES.concat("/").concat(url) : null;
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.address = companyAddress.getAddress();
		this.postal = companyAddress.getPostal();
		this.wilaya = companyAddress.getWilaya();
	}

	public String getTradename() {
		return tradename;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getCompanyURL() {
		return companyURL;
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

	@Override
	public String toString() {
		return "AdmCompanyInfo [tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", companyURL=" + companyURL
				+ ", activity=" + activity + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya + "]";
	}

}
