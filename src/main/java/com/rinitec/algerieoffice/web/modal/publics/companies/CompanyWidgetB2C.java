package com.rinitec.algerieoffice.web.modal.publics.companies;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.manage.WidgetB2C;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CompanyWidgetB2C implements Serializable {
	private static final long serialVersionUID = 84280696290887689L;
	
	private final Long id;
	private final String tradename;
	private final String companyURL;
	private final String urlCover;
	private final String urlAvatar;
	private final String activity;
	private final String address;
	private final String phone;
	private final String mail;
	private final String lang;
	private final Integer note;
	private final Long evaluation;
	private final Long liked;
	private final Integer favorite;
	private final Integer premium;
	
	public CompanyWidgetB2C(final WidgetB2C widgetB2C, final Company company, final String url, final Double note, final Long evaluation, final Long liked, 
			final Integer favorite, final Integer premium, final DateTime forOrder1, final DateTime forOrder2, final Long forOrder3, final String forOrder4) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.id = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlCover = widgetB2C.getHasCover() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.widget 
				: "/static/vectors/widget/m_widget".concat(String.valueOf(widgetB2C.getCategory())).concat("-min.jpg");
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company
				: "/static/picts/avatars/company-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.address = companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal());
		this.phone = company.getPhone();
		this.mail = company.getCompanymail();
		this.lang = company.getLang();
		this.note = note != null ? note.intValue() : null;
		this.evaluation = evaluation;
		this.liked = liked;
		this.favorite = favorite;
		this.premium = premium;
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

	public String getUrlCover() {
		return urlCover;
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

	public Long getLiked() {
		return liked;
	}

	public Integer getFavorite() {
		return favorite;
	}

	public Integer getPremium() {
		return premium;
	}
	
	public int averageLiked() {
		return (int) ((100 * liked) / evaluation);
	}

	@Override
	public String toString() {
		return "CompanyWidgetB2C [id=" + id + ", tradename=" + tradename + ", companyURL=" + companyURL + ", urlCover="
				+ urlCover + ", urlAvatar=" + urlAvatar + ", activity=" + activity + ", address=" + address + ", phone="
				+ phone + ", mail=" + mail + ", lang=" + lang + ", note=" + note + ", evaluation=" + evaluation
				+ ", liked=" + liked + ", favorite=" + favorite + ", premium=" + premium + "]";
	}

}
