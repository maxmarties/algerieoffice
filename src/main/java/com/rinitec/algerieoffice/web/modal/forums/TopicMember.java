package com.rinitec.algerieoffice.web.modal.forums;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Account;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class TopicMember implements Serializable {
	private static final long serialVersionUID = -5910580965861585615L;
	
	private final Long userId;
	private final String urlAvatar;
	private final String username;
	private final String userURL;
	private final DateTime createDate;
	private final DateTime loginDate;
	private final String tradename;
	private final String companyURL;
	
	public TopicMember(final User user, final Account account, final String tradename, final String url) {
		this.userId = user.getId();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account
				: "/static/picts/avatars/account-min.jpg";
		this.username = user.getDisplayName();
		this.userURL = ConstraintesURL.getMemberMapsiteURL(account.getPseudo());
		this.createDate = account.getCreateDate();
		this.loginDate = account.getLastLoginDate();
		this.tradename = tradename;
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getUserURL() {
		return userURL;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public DateTime getLoginDate() {
		return loginDate;
	}

	public String getTradename() {
		return tradename;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	@Override
	public String toString() {
		return "TopicMember [userId=" + userId + ", urlAvatar=" + urlAvatar + ", username=" + username + ", userURL="
				+ userURL + ", createDate=" + createDate + ", loginDate=" + loginDate + ", tradename=" + tradename
				+ ", companyURL=" + companyURL + "]";
	}

}
