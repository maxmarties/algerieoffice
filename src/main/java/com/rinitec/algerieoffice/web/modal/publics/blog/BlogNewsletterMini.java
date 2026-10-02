package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogNewsletterMini implements Serializable {
	private static final long serialVersionUID = 6884145205980586999L;
	
	private final String title;
	private final String identifyURL;
	private final String description;
	private final String category;
	
	public BlogNewsletterMini(final Blog blog) {
		this.title = blog.getTitle().replaceAll("\"", "'");
		this.identifyURL = ConstraintesURL.getMapsiteBlogArticelURL(blog.getIdentify());
		this.description = blog.getDescription().replaceAll("\"", "'");
		this.category = String.valueOf(blog.getCategory());
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

	public String getCategory() {
		return category;
	}

	@Override
	public String toString() {
		return "BlogNewsletterMini [title=" + title + ", identifyURL=" + identifyURL + ", description=" + description
				+ ", category=" + category + "]";
	}

}
