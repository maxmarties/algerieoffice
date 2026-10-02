package com.rinitec.algerieoffice.web.modal.admins.premium;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmPremiumLine implements Serializable {
	private static final long serialVersionUID = -7531336567431658266L;
	
	private final String id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String companyURL;
	private final int pack;
	private final String createDate;
	private final String expiryDate;
	private final boolean enabled;
	
	public AdmPremiumLine(final Premium premium, final Company company, final String url) {
		this.id = premium.getId().toString();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.pack = premium.getPass();
		this.createDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(premium.getCreateDate());
		this.expiryDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(premium.getExpiryDate());
		this.enabled = premium.getEnabled();
	}

	public String getId() {
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

	public int getPack() {
		return pack;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "AdmPremiumLine [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", activity="
				+ activity + ", companyURL=" + companyURL + ", pack=" + pack + ", createDate=" + createDate
				+ ", expiryDate=" + expiryDate + ", enabled=" + enabled + "]";
	}

}
