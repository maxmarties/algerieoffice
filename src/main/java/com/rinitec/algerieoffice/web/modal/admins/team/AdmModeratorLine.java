package com.rinitec.algerieoffice.web.modal.admins.team;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmModeratorLine implements Serializable {
	private static final long serialVersionUID = -1499934962080402637L;
	
	private final Long id;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String activity;
	private final Integer wilaya;
	private final boolean published;
	private final String username;
	private final String userAvatar;
	private final String role;
	private final String email;
	private final Long numberOfVisit;
	private final boolean enabled;
	private final boolean locked;
	
	public AdmModeratorLine(final Company company, final String url, final User user, final Long numberOfVisit) {
		this.id = user.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=40&height=40"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.wilaya = ((CompanyAddress) company.getAddresses().toArray()[0]).getWilaya();
		this.published = company.isEnabled() && company.isActive() && !company.isLocked();
		this.username = user.getDisplayName();
		this.userAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32" 
				: "/static/picts/avatars/account_mini-min.jpg";
		this.role = ParseUtil.getRoleMessage(user.getRoles());
		this.email = user.getEmail();
		this.numberOfVisit = numberOfVisit;
		this.enabled = user.isEnabled();
		this.locked = user.isLocked();
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

	public Integer getWilaya() {
		return wilaya;
	}

	public boolean isPublished() {
		return published;
	}

	public String getUsername() {
		return username;
	}
	
	public String getUserAvatar() {
		return userAvatar;
	}

	public String getRole() {
		return role;
	}

	public String getEmail() {
		return email;
	}

	public Long getNumberOfVisit() {
		return numberOfVisit;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isLocked() {
		return locked;
	}
	
	public boolean isUserPublished() {
		return enabled && !locked;
	}

	@Override
	public String toString() {
		return "AdmModeratorLine [id=" + id + ", tradename=" + tradename + ", companyURL=" + companyURL + ", urlAvatar="
				+ urlAvatar + ", activity=" + activity + ", wilaya=" + wilaya + ", published=" + published
				+ ", username=" + username + ", userAvatar=" + userAvatar + ", role=" + role + ", email=" + email
				+ ", numberOfVisit=" + numberOfVisit + ", enabled=" + enabled + ", locked=" + locked + "]";
	}

}
