package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;
import java.util.UUID;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogWidgetMini implements Serializable {
	private static final long serialVersionUID = 8536148641107737363L;
	
	private final String photoURL;
	private final String title;
	private final String identifyURL;
	private final String description;
	private final String categoryURL;
	private final String language;
	private final String modifiedDate;
	private final int category;
	private final int viewCount;
	private final Long likeCount;
	
	public BlogWidgetMini(final Blog blog, final UUID photoUUID, final Long likeCount) {
		this.photoURL = ConstraintesURL.URL_AOBNN + "?aobnId=".concat(photoUUID.toString());
		this.title = blog.getTitle();
		this.identifyURL = ConstraintesURL.getBrowserBlogURL(blog.getIdentify());
		this.description = blog.getDescription();
		this.categoryURL = ConstraintesURL.getBrowserBlogFamilyURL(blog.getCategory());
		this.language = blog.getLanguage();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(blog.getModifiedDate());
		this.category = blog.getCategory();
		this.viewCount = blog.getViewCount();
		this.likeCount = likeCount;
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

	public String getCategoryURL() {
		return categoryURL;
	}

	public String getLanguage() {
		return language;
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

	@Override
	public String toString() {
		return "BlogWidgetMini [photoURL=" + photoURL + ", title=" + title + ", identifyURL=" + identifyURL
				+ ", description=" + description + ", categoryURL=" + categoryURL + ", language=" + language
				+ ", modifiedDate=" + modifiedDate + ", category=" + category + ", viewCount=" + viewCount
				+ ", likeCount=" + likeCount + "]";
	}

}
