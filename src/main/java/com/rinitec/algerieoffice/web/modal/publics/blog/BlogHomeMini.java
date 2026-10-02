package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogHomeMini implements Serializable {
	private static final long serialVersionUID = -9202744387041851226L;
	
	private final String title;
	private final String identifyURL;
	private final String categoryURL;
	private final String description;
	private final String language;
	private final DateTime modifiedDate;
	private final int category;
	private final int viewCount;
	private final Long likeCount;
	
	public BlogHomeMini(final Blog blog, final Long likeCount) {
		this.title = blog.getTitle();
		this.identifyURL = ConstraintesURL.getBrowserBlogURL(blog.getIdentify());
		this.categoryURL = ConstraintesURL.getBrowserBlogFamilyURL(blog.getCategory());
		this.description = blog.getDescription();
		this.modifiedDate = blog.getModifiedDate();
		this.language = blog.getLanguage();
		this.category = blog.getCategory();
		this.viewCount = blog.getViewCount();
		this.likeCount = likeCount;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getCategoryURL() {
		return categoryURL;
	}

	public String getDescription() {
		return description;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public DateTime getModifiedDate() {
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

	@Override
	public String toString() {
		return "BlogHomeMini [title=" + title + ", identifyURL=" + identifyURL + ", categoryURL=" + categoryURL
				+ ", description=" + description + ", language=" + language + ", modifiedDate=" + modifiedDate
				+ ", category=" + category + ", viewCount=" + viewCount + ", likeCount=" + likeCount + "]";
	}

}
