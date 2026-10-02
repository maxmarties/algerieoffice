package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestPartner;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class PartnerGuestLine implements Serializable {
	private static final long serialVersionUID = 545421940475264064L;
	
	private final String id;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String address;
	private final Integer wilaya;
	private final String activity;
	private final Integer briefcase;
	private final String guestDate;
	private final String approuvedBy;
	private final boolean approuved;
	
	public PartnerGuestLine(final GuestPartner guestPartner, final Company company, final String url, final Integer type, final String autor) {
		final List<Activity> activities = new ArrayList<Activity>(company.getActivities());
		final List<CompanyAddress> companiesAddress = new ArrayList<CompanyAddress>(company.getAddresses());
		this.id = guestPartner.getId().toString();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.address = companiesAddress.get(0).getAddress().concat(", ").concat(companiesAddress.get(0).getPostal());
		this.wilaya = companiesAddress.get(0).getWilaya();
		this.activity = activities.get(0).getCode();
		this.briefcase = type;
		this.guestDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestPartner.getGuestDate());
		this.approuvedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.approuved = guestPartner.isApprouved();
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

	public Integer getBriefcase() {
		return briefcase;
	}

	public String getGuestDate() {
		return guestDate;
	}

	public String getApprouvedBy() {
		return approuvedBy;
	}

	public boolean isApprouved() {
		return approuved;
	}

	@Override
	public String toString() {
		return "PartnerGuestLine [id=" + id + ", tradename=" + tradename + ", companyURL=" + companyURL + ", urlAvatar="
				+ urlAvatar + ", address=" + address + ", wilaya=" + wilaya + ", activity=" + activity + ", briefcase="
				+ briefcase + ", guestDate=" + guestDate + ", approuvedBy=" + approuvedBy + ", approuved=" + approuved + "]";
	}

}
