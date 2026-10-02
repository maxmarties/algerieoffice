package com.rinitec.algerieoffice.web.modal;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CurrentCompany implements Serializable {
	private static final long serialVersionUID = 3193930628039024549L;

	private final Long companyId;
	private final boolean enabled;
	private final boolean published;
	private final String tradename;
	private final String url;
	private final String iconurl;
	private final String companymail;
	private final DateTime createdDate;
	private final Integer premium;
	
	public CurrentCompany(final Company company, final String url, final DateTime createdDate, final Integer premium) {
		this.companyId = company.getId();
		this.enabled = company.isEnabled() && !company.isLocked();
		this.published = company.isEnabled() && company.isActive() && !company.isLocked();
		this.tradename = company.getTradename();
		this.url = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.iconurl = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=44&height=44"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.companymail = company.getCompanymail();
		this.createdDate = createdDate;
		this.premium = premium == null ? 0 : premium;
	}
	
	public Long getCompanyId() {
		return companyId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isPublished() {
		return published;
	}

	public String getTradename() {
		return tradename;
	}

	public String getUrl() {
		return url;
	}

	public String getIconurl() {
		return iconurl;
	}
	
	public String getCompanymail() {
		return companymail;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public Integer getPremium() {
		return premium;
	}
	
	public boolean hasPremium() {
		return premium != 0;
	}
	
	public boolean hasIgnoreWelcome() {
		return createdDate.isBefore(ParseUtil.getFourthineFromNow());
	}

	@Override
	public String toString() {
		return "CurrentCompany [companyId=" + companyId + ", enabled=" + enabled + ", published=" + published
				+ ", tradename=" + tradename + ", url=" + url + ", iconurl=" + iconurl + ", companymail=" + companymail
				+ ", createdDate=" + createdDate + ", premium=" + premium + "]";
	}
	
}
