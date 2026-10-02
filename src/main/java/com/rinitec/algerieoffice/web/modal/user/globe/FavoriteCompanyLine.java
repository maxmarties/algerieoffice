package com.rinitec.algerieoffice.web.modal.user.globe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteCompany;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class FavoriteCompanyLine implements Serializable {
	private static final long serialVersionUID = 1399097835634756745L;
	
	private final String id;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String address;
	private final Integer wilaya;
	private final String activity;
	private final Integer activitySize;
	private final Integer briefcase;
	private final Integer type;
	private final String postedDate;
	private final String phone;
	private final boolean alert;
	
	public FavoriteCompanyLine(final FavoriteCompany favoriteCompany, final Company company, final String url, final Integer briefcase) {
		final List<Activity> activities = new ArrayList<Activity>(company.getActivities());
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = favoriteCompany.getId().toString();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.address = companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal());
		this.wilaya = companyAddress.getWilaya();
		this.activity = activities.get(0).getCode();
		this.activitySize = activities.size() - 1;
		this.briefcase = briefcase;
		this.type = favoriteCompany.getType();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(favoriteCompany.getPostedDate());
		this.phone = company.getPhone();
		this.alert = favoriteCompany.isAlert();
	}

	public String getId() {
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

	public Integer getActivitySize() {
		return activitySize;
	}

	public Integer getBriefcase() {
		return briefcase;
	}

	public Integer getType() {
		return type;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public String getPhone() {
		return phone;
	}

	public boolean isAlert() {
		return alert;
	}
	
	public String getFormattedPhone() {
		return ParseUtil.getFormattedPhone(phone);
	}

	@Override
	public String toString() {
		return "FavoriteCompanyLine [id=" + id + ", tradename=" + tradename + ", companyURL=" + companyURL
				+ ", urlAvatar=" + urlAvatar + ", address=" + address + ", wilaya=" + wilaya + ", activity=" + activity
				+ ", activitySize=" + activitySize + ", briefcase=" + briefcase + ", type=" + type + ", postedDate="
				+ postedDate + ", phone=" + phone + ", alert=" + alert + "]";
	}
	
}
