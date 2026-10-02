package com.rinitec.algerieoffice.web.modal.publics;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CompanyAutorMini implements Serializable {
	private static final long serialVersionUID = -403897117227643188L;
	
	private final Long id;
	private final String tradename;
	private final String companyURL;
	private final String address;
	private final String postal;
	private final int wilaya;
	private final String phone;
	private final String mail;
	private final String language;
	
	public CompanyAutorMini(final Company company, final String url) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.address = companyAddress.getAddress();
		this.postal = companyAddress.getPostal();
		this.wilaya = companyAddress.getWilaya();
		this.phone = company.getPhone();
		this.mail = company.getCompanymail();
		this.language = company.getLang();
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

	public String getAddress() {
		return address;
	}

	public String getPostal() {
		return postal;
	}

	public int getWilaya() {
		return wilaya;
	}

	public String getPhone() {
		return phone;
	}

	public String getMail() {
		return mail;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public String getFormattedPhone() {
		return "+213".concat(" (0) ").concat(ParseUtil.getFormattedCapital(phone));
	}

	@Override
	public String toString() {
		return "CompanyAutorMini [id=" + id + ", tradename=" + tradename + ", companyURL=" + companyURL + ", address="
				+ address + ", postal=" + postal + ", wilaya=" + wilaya + ", phone=" + phone + ", mail=" + mail
				+ ", language=" + language + "]";
	}

}
