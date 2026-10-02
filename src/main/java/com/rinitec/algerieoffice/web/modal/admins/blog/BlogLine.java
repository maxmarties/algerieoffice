package com.rinitec.algerieoffice.web.modal.admins.blog;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogLine implements Serializable {
	private static final long serialVersionUID = -1972260339974412189L;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final String language;
	private final String autor;
	private final String modifiedDate;
	private final int category;
	private final int viewCount;
	private final Long likeCount;
	private final boolean published;
	
	public BlogLine(final Blog blog, final String autor, final Long likeCount) {
		this.id = blog.getId().toString();
		this.title = blog.getTitle();
		this.identifyURL = ConstraintesURL.getBrowserBlogURL(blog.getIdentify());
		this.language = blog.getLanguage();
		this.autor = autor;
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(blog.getModifiedDate());
		this.category = blog.getCategory();
		this.viewCount = blog.getViewCount();
		this.likeCount = likeCount;
		this.published = blog.getHasPublished();
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

	public String getLanguage() {
		return language;
	}

	public String getAutor() {
		return autor;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public int getCategory() {
		return category;
	}

	public int getViewCount() {
		return viewCount;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public boolean isPublished() {
		return published;
	}

	@Override
	public String toString() {
		return "BlogLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", language=" + language
				+ ", autor=" + autor + ", modifiedDate=" + modifiedDate + ", category=" + category + ", viewCount="
				+ viewCount + ", likeCount=" + likeCount + ", published=" + published + "]";
	}
	
}
