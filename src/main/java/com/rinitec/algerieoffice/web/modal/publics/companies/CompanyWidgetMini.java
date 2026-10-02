package com.rinitec.algerieoffice.web.modal.publics.companies;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CompanyWidgetMini implements Serializable {
	private static final long serialVersionUID = 7185396121937268763L;
	
	private final Long id;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String activity;
	private final String address;
	private final String postal;
	private final int wilaya;
	private final String phone;
	private final String mail;
	private final String lang;
	private final Integer note;
	private final Long evaluation;
	private final Integer favorite;
	private final Integer premium;
	
	public CompanyWidgetMini(final Company company, final String url, final Double note, final Long evaluation, final Integer favorite, final Integer premium) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company
				: "/static/picts/avatars/company-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.address = companyAddress.getAddress();
		this.postal = companyAddress.getPostal();
		this.wilaya = companyAddress.getWilaya();
		this.phone = company.getPhone();
		this.mail = company.getCompanymail();
		this.lang = company.getLang();
		this.note = note != null ? note.intValue() : null;
		this.evaluation = evaluation;
		this.favorite = favorite;
		this.premium = premium;
	}
	
	public CompanyWidgetMini(final Company company, final String url, final Double note, final Long evaluation, final Integer favorite, final Integer premium, 
			final String forOrder1, final Integer forOrder2, final DateTime forOrder3, final DateTime forOrder4, final Long forOrder5) {
		this(company, url, note, evaluation, favorite, premium);
	}
	
	public CompanyWidgetMini(final Company company, final String url, final Double note, final Long evaluation, final Integer favorite, final Integer premium, 
			final String forOrder1, final DateTime forOrder2, final DateTime forOrder3, final Long forOrder4) {
		this(company, url, note, evaluation, favorite, premium);
	}
	
	public CompanyWidgetMini(final Company company, final String url, final Integer favorite, final Integer premium, 
			final DateTime forOrder1, final DateTime forOrder2, final Long forOrder3, final String forOrder4) {
		this(company, url, null, null, favorite, premium);
	}
	
	public CompanyWidgetMini(final Company company, final String url, final Integer favorite, final Integer premium, 
			final Long forOrder1, final Integer forOrder2, final DateTime forOrder3, final String forOrde4) {
		this(company, url, null, null, favorite, premium);
	}
	
	public CompanyWidgetMini(final Company company, final String url, final Integer favorite, final Integer premium, 
			final Long forOrder1, final DateTime forOrder2, final String forOrder3) {
		this(company, url, null, null, favorite, premium);
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

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getActivity() {
		return activity;
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

	public String getLang() {
		return lang;
	}
	
	public Integer getNote() {
		return note;
	}
	
	public Long getEvaluation() {
		return evaluation;
	}
	
	public Integer getFavorite() {
		return favorite;
	}
	
	public Integer getPremium() {
		return premium;
	}

	@Override
	public String toString() {
		return "CompanyWidgetMini [id=" + id + ", tradename=" + tradename + ", companyURL=" + companyURL
				+ ", urlAvatar=" + urlAvatar + ", activity=" + activity + ", address=" + address + ", postal=" + postal
				+ ", wilaya=" + wilaya + ", phone=" + phone + ", mail=" + mail + ", lang=" + lang + ", note=" + note
				+ ", evaluation=" + evaluation + ", favorite=" + favorite + ", premium=" + premium + "]";
	}

}
