package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogSimultudeMini implements Serializable {
	private static final long serialVersionUID = 4569169997314040158L;

	private final UUID id;
	private final String photoURL;
	private final String title;
	private final String identifyURL;
	private final String description;
	private final int viewCount;
	private final Long likeCount;

	public BlogSimultudeMini(final Blog blog, final UUID photoUUID, final Long likeCount) {
		this.id = blog.getId();
		this.photoURL = ConstraintesURL.URL_AOBNN + "?aobnId=".concat(photoUUID.toString()).concat("&width=440&height=238");
		this.title = blog.getTitle();
		this.identifyURL = ConstraintesURL.getBrowserBlogURL(blog.getIdentify());
		this.description = blog.getDescription();
		this.viewCount = blog.getViewCount();
		this.likeCount = likeCount;
	}

	public UUID getId() {
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

	public int getViewCount() {
		return viewCount;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	@Override
	public String toString() {
		return "BlogSimultudeMini [id=" + id + ", photoURL=" + photoURL + ", title=" + title + ", identifyURL="
				+ identifyURL + ", description=" + description + ", viewCount=" + viewCount + ", likeCount=" + likeCount + "]";
	}
	
}
