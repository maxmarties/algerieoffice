package com.rinitec.algerieoffice.web.modal.publics.members;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class MemberProfile implements Serializable {
	private static final long serialVersionUID = -3435517699845718650L;
	
	private final Long id;
	private final String urlAvatar;
	private final String username;
	private final String address;
	private final String function;
	private final String biography;
	private final String phone;
	private final String website;
	private final DateTime createDate;
	private final DateTime loginDate;
	private final Integer sexe;
	private final Integer wilaya;
	private final String favoriteAccount;
	private final String favoriteCompany;
	private final boolean hasOnline;
	private final boolean[] verifieds = new boolean[5];
	private final int[] activities = new int[3];
	private final boolean hasCompleted;
	private final String tradename;
	private final String companyURL;
	
	public MemberProfile(final User user, final Account account, final Profile profile, final boolean idFacebook, final boolean idGoogle, 
			final boolean idLinkedin, final long favoriteAccount, final long favoriteCompany, final int reactivity, final int popularity, 
			final String tradename, final String url, final boolean hasOnline) {
		this.id = user.getId();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account
				: "/static/picts/avatars/".concat(profile != null ? profile.getSexe() ? "agent-male" : "agent-female" : "account").concat("-min.jpg");
		this.username = user.getDisplayName();
		this.createDate = account.getCreateDate();
		this.loginDate = account.getLastLoginDate();
		this.verifieds[0] = user.isEnabled();
		this.verifieds[2] = idFacebook;
		this.verifieds[3] = idGoogle;
		this.verifieds[4] = idLinkedin;
		this.favoriteAccount = ParseUtil.getFormattedCount(favoriteAccount);
		this.favoriteCompany = ParseUtil.getFormattedCount(favoriteCompany);
		if(profile != null) {
			this.address = profile.getAddress().concat(", ").concat(profile.getPostal());
			this.function = profile.getFunction();
			this.biography = profile.getBiography();
			this.phone = profile.getHasPhone() ? profile.getPhone() : null;
			this.website = profile.getWebsite();
			this.sexe = profile.getSexe() ? 1 : 2;
			this.wilaya = profile.getWilaya();
			this.verifieds[1] = profile.isEnabled();
		} else {
			this.address = this.biography = this.phone = this.website = null;
			this.sexe = this.wilaya = null;
			this.function = "--";
			this.verifieds[1] = false;
		}
		this.activities[0] = this.parseCompleted(user, account, profile);
		this.activities[1] = reactivity;
		this.activities[2] = popularity;
		this.hasOnline = hasOnline;
		this.tradename = tradename;
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.hasCompleted = account.getCompleted() > 75;
	}
	
	private final int parseCompleted(final User user, final Account account, final Profile profile) {
		int completed = 0;
		if(user.getHasAvatar()) completed += 2;
		if(user.isEnabled()) completed++;
		if(user.getCompanyId() != null) completed += 3;
		if(account.getHasAccepte()) completed++;
		if(profile != null) {
			completed++;
			if(!StringUtils.isEmpty(profile.getFunction())) completed++;
			if(!StringUtils.isEmpty(profile.getBiography())) completed++;
			if(profile.getHasPhone()) completed++;
			if(profile.isEnabled()) completed += 2;
		}
		for(int i = 2; i < 5; i++) {
			if(this.verifieds[i]) completed += 3;
		}
		return (completed * 100) / 22;
	}

	public Long getId() {
		return id;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getAddress() {
		return address;
	}

	public String getFunction() {
		return function;
	}

	public String getBiography() {
		return biography;
	}

	public String getPhone() {
		return phone;
	}

	public String getWebsite() {
		return website;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public DateTime getLoginDate() {
		return loginDate;
	}

	public Integer getSexe() {
		return sexe;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public String getFavoriteAccount() {
		return favoriteAccount;
	}

	public String getFavoriteCompany() {
		return favoriteCompany;
	}

	public boolean isHasOnline() {
		return hasOnline;
	}

	public boolean[] getVerifieds() {
		return verifieds;
	}

	public int[] getActivities() {
		return activities;
	}

	public String getTradename() {
		return tradename;
	}

	public String getCompanyURL() {
		return companyURL;
	}
	
	public boolean isHasCompleted() {
		return hasCompleted;
	}
	
	public String getCountFavorite(final int index) {
		return index == 1 ? favoriteCompany : favoriteAccount;
	}
	
	public int countVerified() {
		int countVerified = 0;
		for(int i = 0; i < 5; i++) {
			if(verifieds[i]) countVerified++;
		}
		return countVerified;
	}
	
	public int averageActivities() {
		int activity = 0;
		for(int i = 0; i < 3; i++) {
			activity += activities[i];
		}
		return (activity * 100) / 300;
	}
	
	public boolean hasPresentContact() {
		return !StringUtils.isEmpty(website) || !StringUtils.isEmpty(phone);
	}
	
	public String parsePhone() {
		return !StringUtils.isEmpty(phone) ? "+213".concat(" (0) ").concat(ParseUtil.getFormattedCapital(phone)) : "--";
	}

	@Override
	public String toString() {
		return "MemberProfile [id=" + id + ", urlAvatar=" + urlAvatar + ", username=" + username + ", address="
				+ address + ", function=" + function + ", biography=" + biography + ", phone=" + phone + ", website="
				+ website + ", createDate=" + createDate + ", loginDate=" + loginDate + ", sexe=" + sexe + ", wilaya="
				+ wilaya + ", favoriteAccount=" + favoriteAccount + ", favoriteCompany=" + favoriteCompany
				+ ", hasOnline=" + hasOnline + ", verifieds=" + verifieds + ", activities=" + activities
				+ ", hasCompleted=" + hasCompleted + ", tradename=" + tradename + ", companyURL=" + companyURL + "]";
	}

}
