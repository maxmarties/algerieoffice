package com.rinitec.algerieoffice.web.modal.publics.marketplace;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;

public class DocumentInbox implements Serializable {
	private static final long serialVersionUID = 7747203077895154513L;
	
	private final String id;
	private final String title;
	private final String description;
	private final String keysword;
	private final String detail;
	private final String urlExtern;
	private final String modifiedDate;
	
	public DocumentInbox(final Post post, final byte[] detail, final String urlExtern) {
		this.id = post.getId().toString();
		this.title = post.getTitle();
		this.description = post.getDescription();
		this.keysword = post.getKeysword();
		this.detail = new String(detail);
		this.urlExtern = urlExtern;
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(post.getModifiedDate());
	}
	
	public DocumentInbox(final Annonce annonce, final byte[] detail, final String urlExtern) {
		this.id = annonce.getId().toString();
		this.title = annonce.getTitle();
		this.description = annonce.getDescription();
		this.keysword = annonce.getKeysword();
		this.detail = new String(detail);
		this.urlExtern = urlExtern;
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getModifiedDate());
	}
	
	public DocumentInbox(final Employe employe, final byte[] detail, final String urlExtern) {
		this.id = employe.getId().toString();
		this.title = employe.getTitle();
		this.description = employe.getDescription();
		this.keysword = employe.getKeysword();
		this.detail = new String(detail);
		this.urlExtern = urlExtern;
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getModifiedDate());
	}
	
	public DocumentInbox(final Event event, final byte[] detail, final String description) {
		this.id = event.getId().toString();
		this.title = event.getTitle();
		this.description = description;
		this.keysword = event.getKeysword();
		this.detail = new String(detail);
		this.urlExtern = event.getUrlExtern();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(event.getModifiedDate());
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getKeysword() {
		return keysword;
	}

	public String getDetail() {
		return detail;
	}

	public String getUrlExtern() {
		return urlExtern;
	}
	
	public String getModifiedDate() {
		return modifiedDate;
	}
	
	public String[] buildKeysword() {
		return keysword.split(",");
	}
	
	public String parsKey(final String keyword) {
		return keyword.replaceAll(" ", "\\+");
	}

	@Override
	public String toString() {
		return "DocumentInbox [id=" + id + ", title=" + title + ", description=" + description + ", keysword="
				+ keysword + ", detail=" + detail + ", urlExtern=" + urlExtern + ", modifiedDate=" + modifiedDate + "]";
	}

}
