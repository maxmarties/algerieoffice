package com.rinitec.algerieoffice.web.modal.admins.companies;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAccount;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmCompanyProfile implements Serializable {
	private static final long serialVersionUID = -8690464063831981494L;
	
	private final Long id;
	private final Long userId;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String companyURL;
	private final String username;
	private final String createdDate;
	private final String modifiedDate;
	private final Long numberOfVisits;
	private final Long numberOfSignal;
	private final int wilaya;
	private final boolean published;
	
	public AdmCompanyProfile(final Company company, final CompanyAccount companyAccount, final String url, final String username, final DateTime forOroder1, 
			final DateTime forOroder2, final String forOroder3, final Integer forOrder4, final String forOrder5) {
		this.id = company.getId();
		this.userId = companyAccount.getCreatedById();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.username = username;
		this.createdDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(companyAccount.getCreatedDate());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(companyAccount.getModifiedDate());
		this.numberOfVisits = companyAccount.getNumberOfVisits();
		this.numberOfSignal = companyAccount.getNumberOfSignal();
		this.wilaya = ((CompanyAddress) company.getAddresses().toArray()[0]).getWilaya();
		this.published = company.isPublished();
	}

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
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

	public String getUsername() {
		return username;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public Long getNumberOfVisits() {
		return numberOfVisits;
	}

	public Long getNumberOfSignal() {
		return numberOfSignal;
	}
	
	public int getWilaya() {
		return wilaya;
	}
	
	public boolean isPublished() {
		return published;
	}

	@Override
	public String toString() {
		return "AdmCompanyProfile [id=" + id + ", userId=" + userId + ", tradename=" + tradename + ", urlAvatar="
				+ urlAvatar + ", activity=" + activity + ", companyURL=" + companyURL + ", username=" + username
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", numberOfVisits="
				+ numberOfVisits + ", numberOfSignal=" + numberOfSignal + ", wilaya=" + wilaya + ", published=" + published + "]";
	}

}
