package com.rinitec.algerieoffice.web.modal.user.globe;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteAccount;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class FavoriteAccountLine implements Serializable {
	private static final long serialVersionUID = 4318502402308514525L;
	
	private final String id;
	private final String username;
	private final String pseudoURL;
	private final String urlAvatar;
	private final String companyname;
	private final String postal;
	private final String postedDate;
	private final String phone;
	private final boolean pro;
	private final boolean alert;
	private final int verified;
	
	public FavoriteAccountLine(final FavoriteAccount favoriteAccount, final User user, final String pseudo, 
			final Profile profile, final String tradename, final Long countIdentities) {
		this.id = favoriteAccount.getId().toString();
		this.username = user.getDisplayName();
		this.pseudoURL = ConstraintesURL.URL_PROFILES.concat("/").concat(pseudo);
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=40&height=40" 
						: profile == null ? "/static/picts/avatars/account_mini-min.jpg" 
								: "/static/picts/avatars/".concat(profile.getSexe() ? "agent-male-min.jpg" : "agent-female-min.jpg");
		this.companyname = !StringUtils.isEmpty(tradename) ? tradename : "-";
		if(profile != null) {
			this.postal = profile.getPostal();
			this.phone = profile.getHasPhone() ? profile.getPhone() : null;
		} else {
			this.postal = this.phone = null;
		}
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(favoriteAccount.getPostedDate());
		this.pro = user.getCompanyId() != null;
		this.alert = favoriteAccount.isAlert();
		this.verified = (int) ((user.isEnabled() ? 1 : 0) + (profile != null && profile.isEnabled() ? 1 : 0) 
				+ (countIdentities != null ? countIdentities : 0));
	}
	
	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPseudoURL() {
		return pseudoURL;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getCompanyname() {
		return companyname;
	}

	public String getPostal() {
		return postal;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public String getPhone() {
		return phone;
	}

	public boolean isPro() {
		return pro;
	}

	public boolean isAlert() {
		return alert;
	}

	public int getVerified() {
		return verified;
	}

	public String getFormattedPhone() {
		return "+213".concat(" (0) ").concat(ParseUtil.getFormattedCapital(phone));
	}

	@Override
	public String toString() {
		return "FavoriteAccountLine [id=" + id + ", username=" + username + ", pseudoURL=" + pseudoURL + ", urlAvatar="
				+ urlAvatar + ", companyname=" + companyname + ", postal=" + postal + ", postedDate=" + postedDate
				+ ", phone=" + phone + ", pro=" + pro + ", alert=" + alert + ", verified=" + verified + "]";
	}

}
