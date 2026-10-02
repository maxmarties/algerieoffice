package com.rinitec.algerieoffice.web.modal.company.posts;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class PostLine implements Serializable {
	private static final long serialVersionUID = 2483721723183289379L;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final String categorie;
	private final String keysword;
	private final String modifiedDate;
	private final String autor;
	private final boolean service;
	private final boolean hasPublished;
	
	public PostLine(final Post post, final String autor, final String category) {
		this.id = post.getId().toString();
		this.title = post.getTitle();
		this.identifyURL = ConstraintesURL.getPostPreviewURL(post.getIdentify());
		this.categorie = !StringUtils.isEmpty(category) ? category : "-";
		this.keysword = !StringUtils.isEmpty(post.getKeysword()) ? post.getKeysword().replaceAll(",", ", ") : "-";
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(post.getModifiedDate());
		this.autor = autor;
		this.service = post.getService();
		this.hasPublished = post.getHasPublished();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getCategorie() {
		return categorie;
	}

	public String getKeysword() {
		return keysword;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getAutor() {
		return autor;
	}
	
	public boolean isService() {
		return service;
	}

	public boolean isHasPublished() {
		return hasPublished;
	}

	@Override
	public String toString() {
		return "PostLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", categorie=" + categorie
				+ ", keysword=" + keysword + ", modifiedDate=" + modifiedDate + ", autor=" + autor + ", service="
				+ service + ", hasPublished=" + hasPublished + "]";
	}

}
