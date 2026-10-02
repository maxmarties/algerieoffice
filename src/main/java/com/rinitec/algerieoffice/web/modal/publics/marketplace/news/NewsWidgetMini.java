package com.rinitec.algerieoffice.web.modal.publics.marketplace.news;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class NewsWidgetMini implements Serializable {
	private static final long serialVersionUID = -5096248205691315423L;
	
	private final String uuid;
	private final String photoURL;
	private final String title;
	private final String description;
	private final String urlExtern;
	private final DateTime actuDate;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String address;
	private final String language;
	private final String mapnewsURL;
	private final Long likeCount;
	private final Long commentCount;
	private final boolean liked;
	
	public NewsWidgetMini(final Actuality actuality, final Company company, final String url, final Long likeCount, final Long commentCount, 
			final Long userId, final DateTime forOrder) {
		final CompanyAddress companyAddress = (CompanyAddress) company.getAddresses().toArray()[0];
		this.uuid = actuality.getId().toString();
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(actuality.getPhotoUUID().toString());
		this.title = actuality.getTitle();
		this.description = actuality.getDescription();
		this.urlExtern = actuality.getUrlExtern();
		this.actuDate = actuality.getActuDate();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.address = companyAddress.getAddress().concat(", ").concat(companyAddress.getPostal());
		this.language = company.getLang();
		this.mapnewsURL = ConstraintesURL.getMarketplaceNewMapsiteURL(actuality.getId().toString());
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.liked = userId != null;
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

	public String getTradename() {
		return tradename;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}
	
	public String getAddress() {
		return address;
	}

	public String getLanguage() {
		return language;
	}
	
	public String getMapnewsURL() {
		return mapnewsURL;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public boolean isLiked() {
		return liked;
	}

	@Override
	public String toString() {
		return "NewsWidgetMini [uuid=" + uuid + ", photoURL=" + photoURL + ", title=" + title + ", description="
				+ description + ", urlExtern=" + urlExtern + ", actuDate=" + actuDate + ", tradename=" + tradename
				+ ", companyURL=" + companyURL + ", urlAvatar=" + urlAvatar + ", address=" + address + ", language="
				+ language + ", mapnewsURL=" + mapnewsURL + ", likeCount=" + likeCount + ", commentCount="
				+ commentCount + ", liked=" + liked + "]";
	}

}
