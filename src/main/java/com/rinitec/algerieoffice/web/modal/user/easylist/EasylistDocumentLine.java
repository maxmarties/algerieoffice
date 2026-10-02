package com.rinitec.algerieoffice.web.modal.user.easylist;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class EasylistDocumentLine implements Serializable {
	private static final long serialVersionUID = 7092612174970125700L;
	
	private final String id;
	private final String title;
	private final String documentURL;
	private final String companyname;
	private final String modifiedDate;
	private final boolean published;
	
	public EasylistDocumentLine(final Post post, final Company company, final String companyURL) {
		this.id = post.getId().toString();
		this.title = post.getTitle();
		this.documentURL = ConstraintesURL.getPostFavoriteURL(companyURL, post.getIdentify());
		this.companyname = company.getTradename();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(post.getModifiedDate());
		this.published = post.isActive() && company.isPublished();
	}
	
	public EasylistDocumentLine(final Annonce annonce, final Company company, final String companyURL) {
		this.id = annonce.getId().toString();
		this.title = annonce.getTitle();
		this.documentURL = ConstraintesURL.getMarketplaceFavoriteURL(companyURL, annonce.getIdentify());
		this.companyname = company.getTradename();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getModifiedDate());
		this.published = annonce.isActive() && company.isPublished();
	}
	
	public EasylistDocumentLine(final Event event, final Company company, final String companyURL) {
		this.id = event.getId().toString();
		this.title = event.getTitle();
		this.documentURL = ConstraintesURL.getEventFavoriteURL(companyURL, event.getIdentify());
		this.companyname = company.getTradename();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(event.getModifiedDate());
		this.published = event.getHasPublished() && company.isPublished();
	}
	
	public EasylistDocumentLine(final Employe employe, final Company company, final String companyURL) {
		this.id = employe.getId().toString();
		this.title = employe.getTitle();
		this.documentURL = ConstraintesURL.getEmployeFavoriteURL(companyURL, employe.getIdentify());
		this.companyname = company.getTradename();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getModifiedDate());
		this.published = employe.isActive() && company.isPublished();
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

	public String getModifiedDate() {
		return modifiedDate;
	}

	public boolean isPublished() {
		return published;
	}

	@Override
	public String toString() {
		return "EasylistDocumentLine [id=" + id + ", title=" + title + ", documentURL=" + documentURL + ", companyname="
				+ companyname + ", modifiedDate=" + modifiedDate + ", published=" + published + "]";
	}

}
