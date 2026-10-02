package com.rinitec.algerieoffice.web.modal.admins.blog;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogAnalytic;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogStatLine implements Serializable {
	private static final long serialVersionUID = 238851486575599877L;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final int category;
	private final int viewCount;
	private final int simultude;
	private final int follow;
	private final int market;
	private final Long likeCount;
	
	public BlogStatLine(final Blog blog, final BlogAnalytic blogAnalytic, final Long likeCount) {
		this.id = blog.getId().toString();
		this.title = blog.getTitle();
		this.identifyURL = ConstraintesURL.getBrowserBlogURL(blog.getIdentify());
		this.category = blog.getCategory();
		this.viewCount = blog.getViewCount();
		this.simultude = blogAnalytic.getSimultude();
		this.follow = blogAnalytic.getFollow();
		this.market = blogAnalytic.getMarket();
		this.likeCount = likeCount;
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
	
	public int getCategory() {
		return category;
	}

	public int getViewCount() {
		return viewCount;
	}

	public int getSimultude() {
		return simultude;
	}

	public int getFollow() {
		return follow;
	}

	public int getMarket() {
		return market;
	}
	
	public Long getLikeCount() {
		return likeCount;
	}

	@Override
	public String toString() {
		return "BlogStatLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", category=" + category
				+ ", viewCount=" + viewCount + ", simultude=" + simultude + ", follow=" + follow + ", market=" + market
				+ ", likeCount=" + likeCount + "]";
	}

}
