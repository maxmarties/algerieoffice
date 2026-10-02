package com.rinitec.algerieoffice.web.modal.admins.companies;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmCompanyLine implements Serializable {
	private static final long serialVersionUID = -8309220875754450220L;
	
	private final Long id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String companyURL;
	private final String language;
	private final String phone;
	private final String mail;
	private final String buildDate;
	private final String postal;
	private final int wilaya;
	private final boolean enabled;
	private final boolean locked;
	private final boolean active;
	
	public AdmCompanyLine(final Company company, final String url, final String forOroder1, final Integer forOrder2, final String forOrder3) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = company.getId();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.language = company.getLang();
		this.phone = ParseUtil.getFormattedPhone(company.getPhone());
		this.mail = company.getCompanymail();
		this.buildDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(company.getBuildDate());
		this.postal = companyAddress.getPostal();
		this.wilaya = companyAddress.getWilaya();
		this.enabled = company.isEnabled();
		this.locked = company.isLocked();
		this.active = company.isActive();
	}

	public Long getId() {
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

	public String getLanguage() {
		return language;
	}

	public String getPhone() {
		return phone;
	}

	public String getMail() {
		return mail;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public String getPostal() {
		return postal;
	}

	public int getWilaya() {
		return wilaya;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isLocked() {
		return locked;
	}

	public boolean isActive() {
		return active;
	}
	
	public boolean hasPublished() {
		return enabled && !locked && active;
	}

	@Override
	public String toString() {
		return "AdmCompanyLine [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", activity="
				+ activity + ", companyURL=" + companyURL + ", language=" + language + ", phone=" + phone + ", mail="
				+ mail + ", buildDate=" + buildDate + ", postal=" + postal + ", wilaya=" + wilaya + ", enabled="
				+ enabled + ", locked=" + locked + ", active=" + active + "]";
	}

}
