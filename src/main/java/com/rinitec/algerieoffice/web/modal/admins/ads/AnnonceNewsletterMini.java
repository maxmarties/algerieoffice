package com.rinitec.algerieoffice.web.modal.admins.ads;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AnnonceNewsletterMini implements Serializable {
	private static final long serialVersionUID = 7886748303622507016L;
	
	private final String title;
	private final String identifyURL;
	private final String description;
	private final String type;
	
	public AnnonceNewsletterMini(final Annonce annonce, final String url) {
		this.title = annonce.getTitle().replaceAll("\"", "'");
		this.identifyURL = ConstraintesURL.getAnnonceMapsiteURL(url, annonce.getIdentify());
		this.description = annonce.getDescription().replaceAll("\"", "'");
		this.type = String.valueOf(annonce.getType());
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getDescription() {
		return description;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "AnnonceNewsletterMini [title=" + title + ", identifyURL=" + identifyURL + ", description=" + description
				+ ", type=" + type + "]";
	}
	
}
