package com.rinitec.algerieoffice.web.modal.admins.companies;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.manage.WidgetB2C;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmCompanyCustomer implements Serializable {
	private static final long serialVersionUID = -8478713403365664103L;
	
	private final Long id;
	private final String tradename;
	private final String urlAvatar;
	private final String code;
	private final String companyURL;
	private final int wilaya;
	private final int category;
	private final int activity;
	private final boolean enabled;
	private final boolean filtred;
	private final boolean published;
	
	public AdmCompanyCustomer(final WidgetB2C widgetB2C, final Company company, final String url) {
		this.id = widgetB2C.getCompanyId();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.code = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.wilaya = ((CompanyAddress) company.getAddresses().toArray()[0]).getWilaya();
		this.category = widgetB2C.getCategory();
		this.activity = widgetB2C.getActivity();
		this.enabled = widgetB2C.isEnabled();
		this.filtred = widgetB2C.isFiltred();
		this.published = company.isPublished();
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

	public String getCode() {
		return code;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public int getWilaya() {
		return wilaya;
	}

	public int getCategory() {
		return category;
	}

	public int getActivity() {
		return activity;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isFiltred() {
		return filtred;
	}

	public boolean isPublished() {
		return published;
	}

	@Override
	public String toString() {
		return "AdmCompanyCustomer [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", code="
				+ code + ", companyURL=" + companyURL + ", wilaya=" + wilaya + ", category=" + category + ", activity="
				+ activity + ", enabled=" + enabled + ", filtred=" + filtred + ", published=" + published + "]";
	}

}
