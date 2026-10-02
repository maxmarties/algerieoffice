package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogMarketMini implements Serializable {
	private static final long serialVersionUID = -6665698903959050830L;
	
	private final String id;
	private final String photoURL;
	private final String title;
	private final String identifyURL;
	private final String description;
	private final String language;
	
	public BlogMarketMini(final Blog blog, final UUID photoUUID) {
		this.id = blog.getId().toString();
		this.photoURL = ConstraintesURL.URL_AOBNN + "?aobnId=".concat(photoUUID.toString());
		this.title = blog.getTitle();
		this.identifyURL = ConstraintesURL.getBrowserBlogURL(blog.getIdentify());
		this.description = blog.getDescription();
		this.language = blog.getLanguage();
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

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getDescription() {
		return description;
	}

	public String getLanguage() {
		return language;
	}

	@Override
	public String toString() {
		return "BlogMarketMini [id=" + id + ", photoURL=" + photoURL + ", title=" + title + ", identifyURL="
				+ identifyURL + ", description=" + description + ", language=" + language + "]";
	}

}
