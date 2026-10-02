package com.rinitec.algerieoffice.web.modal.publics.members;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class MemberWidgetMini implements Serializable {
	private static final long serialVersionUID = -5403714131896293147L;
	
	private final Long id;
	private final String urlAvatar;
	private final String username;
	private final String pseudoURL;
	private final String function;
	private final String address;
	private final String postal;
	private final Integer wilaya;
	private final boolean hasPro;
	private final boolean hasFavorite;
	private final int verified;
	private final String companyname;
	private final String companyURL;
	
	private String email;
	private boolean online;
	
	public MemberWidgetMini(final User user, final Profile profile, final String pseudo, final Long userId, final Long identities, final String tradename, 
			final String url, final Integer premium, final Long published) {
		this.id = user.getId();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=48&height=48"
				: "/static/picts/avatars/".concat(profile != null ? profile.getSexe() ? "agent-male" : "agent-female" : "account").concat("_mini-min.jpg");
		this.username = user.getDisplayName();
		this.pseudoURL = ConstraintesURL.URL_PROFILES.concat("/").concat(pseudo);
		this.email = user.getEmail();
		this.hasFavorite = userId != null;
		if(profile != null) {
			this.function = profile.getFunction();
			this.address = profile.getAddress();
			this.postal = profile.getPostal();
			this.wilaya = profile.getWilaya();
			
		} else {
			this.function = "--";
			this.address = this.postal = null;
			this.wilaya = null;
		}
		this.verified = (int) ((user.isEnabled() ? 1 : 0) + (profile != null && profile.isEnabled() ? 1 : 0) + identities);
		this.companyname = tradename;
		this.companyURL = !StringUtils.isEmpty(url) && published != null ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.hasPro = premium != null && premium != 0;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
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

	public String getPseudoURL() {
		return pseudoURL;
	}

	public String getFunction() {
		return function;
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

	public boolean isHasPro() {
		return hasPro;
	}

	public boolean isHasFavorite() {
		return hasFavorite;
	}

	public int getVerified() {
		return verified;
	}
	
	public String getCompanyname() {
		return companyname;
	}
	
	public String getCompanyURL() {
		return companyURL;
	}
	
	public void updateOnline(final boolean hasOnline) {
		this.online = hasOnline;
		this.email = null;
	}

	@Override
	public String toString() {
		return "MemberWidgetMini [id=" + id + ", urlAvatar=" + urlAvatar + ", username=" + username + ", pseudoURL="
				+ pseudoURL + ", function=" + function + ", address=" + address + ", postal=" + postal + ", wilaya="
				+ wilaya + ", hasPro=" + hasPro + ", hasFavorite=" + hasFavorite + ", verified=" + verified
				+ ", companyname=" + companyname + ", companyURL=" + companyURL + ", email=" + email + ", online="
				+ online + "]";
	}

}
