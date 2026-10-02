package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetActuality implements Serializable {
	private static final long serialVersionUID = 5063994644913072607L;
	
	private final String id;
	private final String photoURL;
	private final DateTime actuDate;
	private final String title;
	private final String description;
	private final String urlExtern;
	private final String mapnewsURL;
	private final Long likeCount;
	private final Long commentCount;
	private final boolean liked;
	
	public ExplorerWidgetActuality(final Actuality actuality, final Long likeCount, final Long commentCount, final Long userId) {
		this.id = actuality.getId().toString();
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(actuality.getPhotoUUID().toString());
		this.actuDate = actuality.getActuDate();
		this.title = actuality.getTitle();
		this.description = actuality.getDescription();
		this.urlExtern = actuality.getUrlExtern();
		this.mapnewsURL = ConstraintesURL.getMarketplaceNewMapsiteURL(actuality.getId().toString());
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.liked = userId != null;
	}

	public String getId() {
		return id;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public DateTime getActuDate() {
		return actuDate;
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
		return "ExplorerWidgetActuality [id=" + id + ", photoURL=" + photoURL + ", actuDate=" + actuDate + ", title="
				+ title + ", description=" + description + ", urlExtern=" + urlExtern + ", mapnewsURL=" + mapnewsURL
				+ ", likeCount=" + likeCount + ", commentCount=" + commentCount + ", liked=" + liked + "]";
	}

}
