package com.rinitec.algerieoffice.web.modal.publics.marketplace.news;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ScreenInboxNews implements Serializable {
	private static final long serialVersionUID = -3530362160334942112L;
	
	private final String uuid;
	private final String photoURL;
	private final String title;
	private final String description;
	private final String urlExtern;
	private final DateTime actuDate;
	private final String modifiedDate;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String language;
	private final Long likeCount;
	private final Long commentCount;
	
	public ScreenInboxNews(final Actuality actuality, final Company company, final String url, final Long likeCount, final Long commentCount) {
		this.uuid = actuality.getId().toString();
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(actuality.getPhotoUUID().toString());
		this.title = actuality.getTitle();
		this.description = actuality.getDescription();
		this.urlExtern = actuality.getUrlExtern();
		this.actuDate = actuality.getActuDate();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(actuality.getModifiedDate());
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=38&height=38"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.language = company.getLang();
		this.likeCount = likeCount;
		this.commentCount = commentCount;
	}

	public String getUuid() {
		return uuid;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public DateTime getActuDate() {
		return actuDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
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

	public String getLanguage() {
		return language;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	@Override
	public String toString() {
		return "ScreenInboxNews [uuid=" + uuid + ", photoURL=" + photoURL + ", title=" + title + ", description="
				+ description + ", urlExtern=" + urlExtern + ", actuDate=" + actuDate + ", modifiedDate=" + modifiedDate
				+ ", tradename=" + tradename + ", companyURL=" + companyURL + ", urlAvatar=" + urlAvatar + ", language="
				+ language + ", likeCount=" + likeCount + ", commentCount=" + commentCount + "]";
	}

}
