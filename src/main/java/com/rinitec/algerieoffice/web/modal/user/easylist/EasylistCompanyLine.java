package com.rinitec.algerieoffice.web.modal.user.easylist;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class EasylistCompanyLine implements Serializable {
	private static final long serialVersionUID = -3859160126179367790L;
	
	private final Long id;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String address;
	private final Integer wilaya;
	private final String activity;
	private final Integer briefcase;
	private final String email;
	private final String phone;
	private final boolean published;
	
	public EasylistCompanyLine(final Company company, final String url, final Integer briefcase, final String forOrder1, final String forOrder2, final Integer forOrder3) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.address = companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal());
		this.wilaya = companyAddress.getWilaya();
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.briefcase = briefcase;
		this.email = company.getCompanymail();
		this.phone = company.getPhone();
		this.published = company.isPublished();
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

	public String getAddress() {
		return address;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public String getActivity() {
		return activity;
	}

	public Integer getBriefcase() {
		return briefcase;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public boolean isPublished() {
		return published;
	}
	
	public String getFormattedPhone() {
		return ParseUtil.getFormattedPhone(phone);
	}

	@Override
	public String toString() {
		return "EasylistCompanyLine [id=" + id + ", tradename=" + tradename + ", companyURL=" + companyURL
				+ ", urlAvatar=" + urlAvatar + ", address=" + address + ", wilaya=" + wilaya + ", activity=" + activity
				+ ", briefcase=" + briefcase + ", email=" + email + ", phone=" + phone + ", published=" + published + "]";
	}

}
