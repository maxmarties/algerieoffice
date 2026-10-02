package com.rinitec.algerieoffice.web.modal.company.portfolio;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ActualityLine implements Serializable {
	private static final long serialVersionUID = 971363849150932367L;
	
	private final String id;
	private final String title;
	private final String actualityURL;
	private final String actuDate;
	private final String urlExtern;
	private final String autor;
	private final String modifiedDate;
	private final boolean hasPublished;
	private final Long likeCount;
	private final Long commentCount;
	
	public ActualityLine(final Actuality actuality, final String autor, final Long likeCount, final Long commentCount) {
		this.id = actuality.getId().toString();
		this.title = actuality.getTitle();
		this.actualityURL = ConstraintesURL.getActualiteFavoriteURL(actuality.getId().toString());
		this.actuDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(actuality.getActuDate());
		this.urlExtern = !StringUtils.isEmpty(actuality.getUrlExtern()) ? actuality.getUrlExtern() : null;
		this.autor = autor;
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(actuality.getModifiedDate());
		this.hasPublished = actuality.getHasPublished();
		this.likeCount = likeCount;
		this.commentCount = commentCount;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public String getActualityURL() {
		return actualityURL;
	}

	public String getActuDate() {
		return actuDate;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public String getAutor() {
		return autor;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public boolean isHasPublished() {
		return hasPublished;
	}
	
	public Long getLikeCount() {
		return likeCount;
	}
	
	public Long getCommentCount() {
		return commentCount;
	}

	@Override
	public String toString() {
		return "ActualityLine [id=" + id + ", title=" + title + ", actualityURL=" + actualityURL + ", actuDate="
				+ actuDate + ", urlExtern=" + urlExtern + ", autor=" + autor + ", modifiedDate=" + modifiedDate
				+ ", hasPublished=" + hasPublished + ", likeCount=" + likeCount + ", commentCount=" + commentCount + "]";
	}

}
