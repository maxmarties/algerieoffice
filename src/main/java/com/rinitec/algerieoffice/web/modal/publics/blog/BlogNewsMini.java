package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;
import java.util.UUID;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Blog;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogNewsMini implements Serializable {
	private static final long serialVersionUID = -233230386269193462L;
	
	private final String photoURL;
	private final String title;
	private final String identifyURL;
	private final String modifiedDate;
	private final String autorname;
	private final String autorURL;
	
	public BlogNewsMini(final Blog blog, final UUID photoUUID, final String autorname, final String autoridentify) {
		this.photoURL = ConstraintesURL.URL_AOBNN + "?aobnId=".concat(photoUUID.toString()).concat("&width=99&height=54");
		this.title = blog.getTitle();
		this.identifyURL = ConstraintesURL.getBrowserBlogURL(blog.getIdentify());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(blog.getModifiedDate());
		this.autorname = autorname;
		this.autorURL = ConstraintesURL.getBrowserBlogAutorURL(autoridentify);
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

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getAutorname() {
		return autorname;
	}

	public String getAutorURL() {
		return autorURL;
	}

	@Override
	public String toString() {
		return "BlogNewsMini [photoURL=" + photoURL + ", title=" + title + ", identifyURL=" + identifyURL
				+ ", modifiedDate=" + modifiedDate + ", autorname=" + autorname + ", autorURL=" + autorURL + "]";
	}

}
