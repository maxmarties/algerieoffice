package com.rinitec.algerieoffice.web.modal.admins.feedback;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.profiles.Rate;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmRateLine implements Serializable {
	private static final long serialVersionUID = -6760620837218696418L;
	
	private final String id;
	private final Long memberId;
	private final String membername;
	private final String memberURL;
	private final String urlAvatar;
	private final String username;
	private final String identifyURL;
	private final int type;
	private final String reason;
	private final String postedDate;
	private final boolean approuved;
	private final boolean locked;
	private final boolean hasPro;

	public AdmRateLine(final Rate rate, final User member, final String username) {
		this.id = rate.getId().toString();
		this.memberId = member.getId();
		this.membername = member.getDisplayName();
		this.memberURL = ConstraintesURL.URL_PROFILES.concat("?id=").concat(member.getId().toString());
		this.urlAvatar = member.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + member.getId() + "&type=" + AvatarType.account + "&width=48&height=48"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = username;
		this.identifyURL = ConstraintesURL.URL_PROFILES.concat("?id=").concat(rate.getUserId().toString());
		this.type = rate.getType();
		this.reason = rate.getReason();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(rate.getPostedDate());
		this.approuved = rate.isApprouved();
		this.locked = member.isLocked();
		this.hasPro = member.getCompanyId() != null;
	}

	public String getId() {
		return id;
	}
	
	public Long getMemberId() {
		return memberId;
	}

	public String getMembername() {
		return membername;
	}

	public String getMemberURL() {
		return memberURL;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public int getType() {
		return type;
	}

	public String getReason() {
		return reason;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public boolean isApprouved() {
		return approuved;
	}
	
	public boolean isLocked() {
		return locked;
	}
	
	public boolean isHasPro() {
		return hasPro;
	}

	@Override
	public String toString() {
		return "AdmRateLine [id=" + id + ", memberId=" + memberId + ", membername=" + membername + ", memberURL="
				+ memberURL + ", urlAvatar=" + urlAvatar + ", username=" + username + ", identifyURL=" + identifyURL
				+ ", type=" + type + ", reason=" + reason + ", postedDate=" + postedDate + ", approuved=" + approuved
				+ ", locked=" + locked + ", hasPro=" + hasPro + "]";
	}

}
