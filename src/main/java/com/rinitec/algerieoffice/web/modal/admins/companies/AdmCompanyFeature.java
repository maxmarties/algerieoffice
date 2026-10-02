package com.rinitec.algerieoffice.web.modal.admins.companies;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmCompanyFeature implements Serializable {
	private static final long serialVersionUID = -6343011864101576651L;
	
	private final Long id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String companyURL;
	private final int wilaya;
	private final int completed;
	private final int users;
	private final int premium;
	private final Integer note;
	private final boolean published;
	
	public AdmCompanyFeature(final Company company, final String url, final Integer completed, final Long users, final Integer pass, final Double note, 
			final String forOroder1, final Integer forOrder2, final String forOrder3) {
		this.id = company.getId();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.wilaya = ((CompanyAddress) company.getAddresses().toArray()[0]).getWilaya();
		this.completed = completed;
		this.users = users.intValue();
		this.premium = pass == null ? 0 : pass;
		this.note = note != null ? note.intValue() : null;
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

	public String getActivity() {
		return activity;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public int getWilaya() {
		return wilaya;
	}

	public int getCompleted() {
		return completed;
	}

	public int getUsers() {
		return users;
	}

	public int getPremium() {
		return premium;
	}

	public Integer getNote() {
		return note;
	}

	public boolean isPublished() {
		return published;
	}

	@Override
	public String toString() {
		return "AdmCompanyFeature [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", activity="
				+ activity + ", companyURL=" + companyURL + ", wilaya=" + wilaya + ", completed=" + completed
				+ ", users=" + users + ", premium=" + premium + ", note=" + note + ", published=" + published + "]";
	}

}
