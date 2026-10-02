package com.rinitec.algerieoffice.web.modal.company.newsletter;

import java.io.Serializable;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Actuality;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class NewsletterItem implements Serializable {
	private static final long serialVersionUID = -2079647509150691063L;
	
	private final String title;
	private final String description;
	private final String identifyURL;
	private final String photoURL;
	
	public NewsletterItem(final Actuality actuality) {
		this.title = actuality.getTitle();
		this.description = actuality.getDescription();
		this.identifyURL = ConstraintesURL.getMarketplaceNewMapsiteURL(actuality.getId().toString());
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(actuality.getPhotoUUID().toString());
	}
	
	public NewsletterItem(final Post post, final UUID photoUUID, final String url) {
		this.title = post.getTitle();
		this.description = post.getDescription();
		this.identifyURL = ConstraintesURL.getMarketplacePostMapsiteURL(url, post.getIdentify());
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(photoUUID.toString());
	}
	
	public NewsletterItem(final Event event, final String description, final UUID photoUUID, final String url) {
		this.title = event.getTitle();
		this.description = description;
		this.identifyURL = ConstraintesURL.getMarketplaceEventMapsiteURL(url, event.getIdentify());
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(photoUUID.toString());
	}
	
	public NewsletterItem(final Annonce annonce, final String url) {
		this.title = annonce.getTitle();
		this.description = annonce.getDescription();
		this.identifyURL = ConstraintesURL.getMarketplaceAnnonceMapsiteURL(url, annonce.getIdentify());
		this.photoURL = null;
	}
	
	public NewsletterItem(final Employe employe, final String url) {
		this.title = employe.getTitle();
		this.description = employe.getDescription();
		this.identifyURL = ConstraintesURL.getMarketplaceEmployeMapsiteURL(url, employe.getIdentify());
		this.photoURL = null;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	@Override
	public String toString() {
		return "NewsletterItem [title=" + title + ", description=" + description + ", identifyURL=" + identifyURL
				+ ", photoURL=" + photoURL + "]";
	}

}
