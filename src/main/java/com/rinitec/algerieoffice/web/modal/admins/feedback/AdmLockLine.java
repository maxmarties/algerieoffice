package com.rinitec.algerieoffice.web.modal.admins.feedback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmLockLine implements Serializable {
	private static final long serialVersionUID = -5678042288736420040L;
	
	private final Long id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String address;
	private final Integer wilaya;
	private final String buildDate;
	private final String createdDate;
	
	public AdmLockLine(final Company company, final DateTime createdDate) {
		final List<Activity> activities = new ArrayList<Activity>(company.getActivities());
		final List<CompanyAddress> companiesAddress = new ArrayList<CompanyAddress>(company.getAddresses());
		this.id = company.getId();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = activities.get(0).getCode();
		this.address = companiesAddress.get(0).getAddress().concat(", ").concat(companiesAddress.get(0).getPostal());
		this.wilaya = companiesAddress.get(0).getWilaya();
		this.buildDate = company.getBuildDate() != null ? DateTimeFormat.forPattern("dd/MM/yyyy").print(company.getBuildDate()) : "--";
		this.createdDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(createdDate);
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

	public String getAddress() {
		return address;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	@Override
	public String toString() {
		return "AdmLockLine [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", activity="
				+ activity + ", address=" + address + ", wilaya=" + wilaya + ", buildDate=" + buildDate
				+ ", createdDate=" + createdDate + "]";
	}

}
