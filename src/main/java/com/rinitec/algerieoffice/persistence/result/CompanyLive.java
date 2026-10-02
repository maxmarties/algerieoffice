package com.rinitec.algerieoffice.persistence.result;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CompanyLive {

	private final Long comanyId;
	private final String avatarURL;
	private final String tradename;
	private final String url;
	
	public CompanyLive(final Long companyId, final String tradename, final Boolean hasAvatar, final String url) {
		this.comanyId = companyId;
		this.avatarURL = hasAvatar ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.company + "&width=40&height=40" 
				: "/static/picts/avatars/company_mini-min.jpg";
		this.tradename = tradename;
		this.url = url;
	}
	
	public Long getComanyId() {
		return comanyId;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public String getTradename() {
		return tradename;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "CompanyLive [comanyId=" + comanyId + ", avatarURL=" + avatarURL + ", tradename=" + tradename + ", url="
				+ url + "]";
	}
	
}
