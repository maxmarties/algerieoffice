package com.rinitec.algerieoffice.web.modal.user.account;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class PrivateProfile implements Serializable {
	private static final long serialVersionUID = -7621727213319880522L;
	
	private final String urlAvatar;
	private final String username;
	private final String pseudoURL;
	private final String email;
	private final String address;
	private final String function;
	private final String biography;
	private final String phone;
	private final String website;
	private final DateTime createDate;
	private final Integer sexe;
	private final Integer wilaya;
	private final boolean hasPro;
	private final boolean hasPhone;
	private final String favoriteAccount;
	private final String favoriteCompany;
	private final boolean[] verifieds = new boolean[5];
	private final int[] activities = new int[3];
	private final boolean hasCompleted;
	
	public PrivateProfile(final User user, final Account account, final Profile profile, final boolean idFacebook, final boolean idGoogle, 
			final boolean idLinkedin, final long favoriteAccount, final long favoriteCompany, final int reactivity, final int popularity) {
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account
				: "/static/picts/avatars/".concat(profile != null ? profile.getSexe() ? "agent-male" : "agent-female" : "account").concat("-min.jpg");
		this.username = user.getDisplayName();
		this.pseudoURL = user.isEnabled() ? ConstraintesURL.URL_PROFILES.concat("/").concat(account.getPseudo()) : null;
		this.email = user.getEmail();
		this.createDate = account.getCreateDate();
		this.hasPro = user.getCompanyId() != null;
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
			this.phone = profile.getPhone();
			this.website = profile.getWebsite();
			this.sexe = profile.getSexe() ? 1 : 2;
			this.wilaya = profile.getWilaya();
			this.hasPhone = profile.getHasPhone();
			this.verifieds[1] = profile.isEnabled();
		} else {
			this.address = this.biography = this.phone = this.website = null;
			this.sexe = this.wilaya = null;
			this.function = "--";
			this.hasPhone = this.verifieds[1] = false;
		}
		this.activities[0] = this.parseCompleted(user, account, profile);
		this.activities[1] = reactivity;
		this.activities[2] = popularity;
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

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getPseudoURL() {
		return pseudoURL;
	}

	public String getEmail() {
		return email;
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

	public Integer getSexe() {
		return sexe;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public boolean isHasPro() {
		return hasPro;
	}

	public boolean isHasPhone() {
		return hasPhone;
	}

	public String getFavoriteAccount() {
		return favoriteAccount;
	}

	public String getFavoriteCompany() {
		return favoriteCompany;
	}
	
	public boolean[] getVerifieds() {
		return verifieds;
	}
	
	public int[] getActivities() {
		return activities;
	}
	
	public boolean isHasCompleted() {
		return hasCompleted;
	}
	
	public String getMapsiteProfileURL() {
		return pseudoURL != null ? ConstraintesURL.URL_APPLICATION.concat(pseudoURL) : "";
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
		return !StringUtils.isEmpty(website) || hasPhone;
	}
	
	public String parsePhone() {
		return !StringUtils.isEmpty(phone) ? "+213".concat(" (0) ").concat(ParseUtil.getFormattedCapital(phone)) : "--";
	}

	@Override
	public String toString() {
		return "PrivateProfile [urlAvatar=" + urlAvatar + ", username=" + username + ", pseudoURL=" + pseudoURL
				+ ", email=" + email + ", address=" + address + ", function=" + function + ", biography=" + biography
				+ ", phone=" + phone + ", website=" + website + ", createDate=" + createDate + ", sexe=" + sexe
				+ ", wilaya=" + wilaya + ", hasPro=" + hasPro + ", hasPhone=" + hasPhone + ", favoriteAccount="
				+ favoriteAccount + ", favoriteCompany=" + favoriteCompany + ", verifieds=" + verifieds
				+ ", activities=" + activities + ", hasCompleted=" + hasCompleted + "]";
	}

}
