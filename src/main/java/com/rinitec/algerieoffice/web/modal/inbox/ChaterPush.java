package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Chater;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ChaterPush implements Serializable {
	private static final long serialVersionUID = -188542376740800142L;
	
	private Long userId;
	private String username;
	private String message;
	private String time;
	private String tradename;
	private String avatarURL;
	private String companyURL;
	private Integer premium;
	
	public ChaterPush() {
	}
	
	public ChaterPush(final Chater chater, final String username, final Boolean hasAvatar, final String tradename, final String url, final Integer premium) {
		this.userId = chater.getUserId();
		this.username = username;
		this.message = chater.getMessage();
		this.time = DateTimeFormat.forPattern("HH:mm").print(chater.getChaterDate());
		this.tradename = tradename;
		this.avatarURL = hasAvatar ? ConstraintesURL.URL_AVATARS + "?postedId=" + chater.getUserId() + "&type=" + AvatarType.account + "&width=38&height=38"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.companyURL = url;
		this.premium = premium == null ? 0 : premium;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
	}

	public Integer getPremium() {
		return premium;
	}

	public void setPremium(Integer premium) {
		this.premium = premium;
	}
	
	public String parseReply() {
		return "@".concat(username.replaceAll(" ", "_"));
	}

	@Override
	public String toString() {
		return "ChaterPush [userId=" + userId + ", username=" + username + ", message=" + message + ", time=" + time
				+ ", tradename=" + tradename + ", avatarURL=" + avatarURL + ", companyURL=" + companyURL + ", premium="
				+ premium + "]";
	}

}
