package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogExplorerMini implements Serializable {
	private static final long serialVersionUID = -6854584025300322546L;
	
	private final String photoURL;
	private final String title;
	private final String identifyURL;
	private final String categoryURL;
	private final int category;
	private final int viewCount;
	private final Long likeCount;
	
	public BlogExplorerMini(final Blog blog, final UUID photoUUID, final Long likeCount) {
		this.photoURL = ConstraintesURL.URL_AOBNN + "?aobnId=".concat(photoUUID.toString()).concat("&width=480&height=260");
		this.title = blog.getTitle();
		this.identifyURL = ConstraintesURL.getBrowserBlogURL(blog.getIdentify());
		this.categoryURL = ConstraintesURL.getBrowserBlogFamilyURL(blog.getCategory());
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

	public String getCategoryURL() {
		return categoryURL;
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
		return "BlogExplorerMini [photoURL=" + photoURL + ", title=" + title + ", identifyURL=" + identifyURL
				+ ", categoryURL=" + categoryURL + ", category=" + category + ", viewCount=" + viewCount
				+ ", likeCount=" + likeCount + "]";
	}

}
