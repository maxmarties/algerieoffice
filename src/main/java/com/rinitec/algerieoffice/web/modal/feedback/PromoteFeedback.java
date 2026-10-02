package com.rinitec.algerieoffice.web.modal.feedback;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class PromoteFeedback implements Serializable {
	private static final long serialVersionUID = -3018506009967687653L;

	private final String id;
	private final String photoURL;
	private final String title;
	private final String description;
	private final Integer label;
	private final String hrefURL;
	private final boolean hasBlank;
	
	public PromoteFeedback(final Promote promote, final String companyURL) {
		this.id = promote.getId().toString();
		this.photoURL = promote.getPhotoUUID() != null ? ConstraintesURL.URL_PHOTOS + "?photoId=".concat(promote.getPhotoUUID().toString()) : null;
		this.title = promote.getTitle();
		this.description = promote.getDescription();
		this.label = promote.getLabel();
		this.hrefURL = !StringUtils.isEmpty(promote.getUrl()) ? promote.getUrl() : ConstraintesURL.getCompanyExplorerURL(companyURL);
		this.hasBlank = !StringUtils.isEmpty(promote.getUrl());
	}
	
	public String getId() {
		return id;
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

	public Integer getLabel() {
		return label;
	}

	public String getHrefURL() {
		return hrefURL;
	}

	public boolean isHasBlank() {
		return hasBlank;
	}

	@Override
	public String toString() {
		return "PromoteFeedback [id=" + id + ", photoURL=" + photoURL + ", title=" + title + ", description="
				+ description + ", label=" + label + ", hrefURL=" + hrefURL + ", hasBlank=" + hasBlank + "]";
	}

}
