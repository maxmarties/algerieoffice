package com.rinitec.algerieoffice.web.modal.explorer.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class ExplorerCompanyProfile implements Serializable {
	private static final long serialVersionUID = 4224081823464397522L;
	
	private final String denomination;
	private final String tradename;
	private final String language;
	private final String address;
	private final String postal;
	private final Integer wilaya;
	private final String activity;
	private final String buildDate;
	private final String email;
	private final String phone;
	private final boolean enabled;
	
	private final List<String> activities = new ArrayList<String>();
	private final List<String> addrs = new ArrayList<String>();
	private final List<String> postals = new ArrayList<String>();
	private final List<Integer> wilayas = new ArrayList<Integer>();
	
	public ExplorerCompanyProfile(final Company company) {
		final List<Activity> activities = new ArrayList<Activity>(company.getActivities());
		final List<CompanyAddress> companiesAddress = new ArrayList<CompanyAddress>(company.getAddresses());
		this.denomination = company.getDenomination();
		this.tradename = company.getTradename();
		this.language = company.getLang();
		this.address = companiesAddress.get(0).getAddress();
		this.postal = companiesAddress.get(0).getPostal();
		this.wilaya = companiesAddress.get(0).getWilaya();
		this.activity = activities.get(0).getCode();
		this.buildDate = !StringUtils.isEmpty(company.getBuildDate()) ? DateTimeFormat.forPattern("dd/MM/yyyy").print(company.getBuildDate()) : "-";
		this.email = company.getCompanymail();
		this.phone = company.getPhone();
		this.enabled = company.isEnabled();
		for (final Activity activity : activities) {
			this.activities.add(activity.getCode());
		}
		if(companiesAddress.size() > 1) {
			for (int i = 1; i < companiesAddress.size(); i++) {
				this.addrs.add(companiesAddress.get(i).getAddress());
				this.postals.add(companiesAddress.get(i).getPostal());
				this.wilayas.add(companiesAddress.get(i).getWilaya());
			}
		}
	}

	public String getDenomination() {
		return denomination;
	}

	public String getTradename() {
		return tradename;
	}

	public String getLanguage() {
		return language;
	}

	public String getAddress() {
		return address;
	}

	public String getPostal() {
		return postal;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public String getActivity() {
		return activity;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public List<String> getActivities() {
		return activities;
	}

	public List<String> getAddrs() {
		return addrs;
	}
	
	public List<String> getPostals() {
		return postals;
	}

	public List<Integer> getWilayas() {
		return wilayas;
	}
	
	public String getFormattedPhone() {
		return "+213".concat(" (0) ").concat(ParseUtil.getFormattedCapital(phone));
	}

	@Override
	public String toString() {
		return "ExplorerCompanyProfile [denomination=" + denomination + ", tradename=" + tradename + ", language="
				+ language + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya + ", activity="
				+ activity + ", buildDate=" + buildDate + ", email=" + email + ", phone=" + phone + ", enabled="
				+ enabled + ", activities=" + activities + ", addrs=" + addrs + ", postals=" + postals + ", wilayas="
				+ wilayas + "]";
	}

}
