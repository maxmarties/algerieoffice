package com.rinitec.algerieoffice.web.modal.user.favorite;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class FavoriteDocumentLine implements Serializable {
	private static final long serialVersionUID = 3564098210863353817L;
	
	private final String id;
	private final String title;
	private final String documentURL;
	private final String companyname;
	private final String postedDate;
	private final String modifiedDate;
	
	public FavoriteDocumentLine(final FavoriteDocument favoriteDocument, final Post post, final String tradename, final String companyURL) {
		this.id = favoriteDocument.getId().toString();
		this.title = post.getTitle();
		this.documentURL = ConstraintesURL.getPostFavoriteURL(companyURL, post.getIdentify());
		this.companyname = tradename;
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(favoriteDocument.getPostedDate());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(post.getModifiedDate());
	}
	
	public FavoriteDocumentLine(final FavoriteDocument favoriteDocument, final Annonce annonce, final String tradename, final String companyURL) {
		this.id = favoriteDocument.getId().toString();
		this.title = annonce.getTitle();
		this.documentURL = ConstraintesURL.getMarketplaceFavoriteURL(companyURL, annonce.getIdentify());
		this.companyname = tradename;
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(favoriteDocument.getPostedDate());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getModifiedDate());
	}
	
	public FavoriteDocumentLine(final FavoriteDocument favoriteDocument, final Event event, final String tradename, final String companyURL) {
		this.id = favoriteDocument.getId().toString();
		this.title = event.getTitle();
		this.documentURL = ConstraintesURL.getEventFavoriteURL(companyURL, event.getIdentify());
		this.companyname = tradename;
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(favoriteDocument.getPostedDate());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(event.getModifiedDate());
	}
	
	public FavoriteDocumentLine(final FavoriteDocument favoriteDocument, final Employe employe, final String tradename, final String companyURL) {
		this.id = favoriteDocument.getId().toString();
		this.title = employe.getTitle();
		this.documentURL = ConstraintesURL.getEmployeFavoriteURL(companyURL, employe.getIdentify());
		this.companyname = tradename;
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(favoriteDocument.getPostedDate());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getModifiedDate());
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public String getCompanyname() {
		return companyname;
	}

	public String getPostedDate() {
		return postedDate;
	}
	
	public String getModifiedDate() {
		return modifiedDate;
	}

	@Override
	public String toString() {
		return "FavoriteDocumentLine [id=" + id + ", title=" + title + ", documentURL=" + documentURL + ", companyname="
				+ companyname + ", postedDate=" + postedDate + ", modifiedDate=" + modifiedDate + "]";
	}

}
