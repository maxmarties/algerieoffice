package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.BlogDetail;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogInbox implements Serializable {
	private static final long serialVersionUID = -6493808252728475420L;
	
	private final String id;
	private final String title;
	private final String description;
	private final String detail;
	private final String photoURL;
	private final String keysword;
	private final String modifiedDate;
	private final String language;
	private final int category;
	private final int viewCount;
	private final String autorname;
	private final String autorURL;
	private final String autorAvatar;
	private final String categoryURL;
	private final Long likeCount;
	
	public BlogInbox(final Blog blog, final BlogDetail blogDetail, final Autor autor, final Long likeCount) {
		this.id = blog.getId().toString();
		this.title = blog.getTitle();
		this.description = blog.getDescription();
		this.detail = new String(blogDetail.getDetail());
		this.photoURL = ConstraintesURL.URL_AOBNN + "?aobnId=".concat(blogDetail.getPhotoUUID().toString());
		this.keysword = blogDetail.getKeysword();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(blog.getModifiedDate());
		this.language = blog.getLanguage();
		this.category = blog.getCategory();
		this.viewCount = blog.getViewCount() + 1;
		this.autorname = autor.getAutorname();
		this.autorURL = ConstraintesURL.getBrowserBlogAutorURL(autor.getIdentify());
		this.autorAvatar = autor.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + autor.getId() + "&type=" + AvatarType.autor + "&width=38&height=38"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.categoryURL = ConstraintesURL.getBrowserBlogFamilyURL(blog.getCategory());
		this.likeCount = likeCount;
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

	public String getDetail() {
		return detail;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getKeysword() {
		return keysword;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getLanguage() {
		return language;
	}

	public int getCategory() {
		return category;
	}

	public int getViewCount() {
		return viewCount;
	}

	public String getAutorname() {
		return autorname;
	}

	public String getAutorURL() {
		return autorURL;
	}

	public String getAutorAvatar() {
		return autorAvatar;
	}
	
	public String getCategoryURL() {
		return categoryURL;
	}
	
	public Long getLikeCount() {
		return likeCount;
	}
	
	public String[] buildKeysword() {
		return keysword.split(",");
	}
	
	public String parsKey(final String keyword) {
		return keyword.replaceAll(" ", "\\+");
	}

	@Override
	public String toString() {
		return "BlogInbox [id=" + id + ", title=" + title + ", description=" + description + ", detail=" + detail
				+ ", photoURL=" + photoURL + ", keysword=" + keysword + ", modifiedDate=" + modifiedDate + ", language="
				+ language + ", category=" + category + ", viewCount=" + viewCount + ", autorname=" + autorname
				+ ", autorURL=" + autorURL + ", autorAvatar=" + autorAvatar + ", categoryURL=" + categoryURL
				+ ", likeCount=" + likeCount + "]";
	}

}
