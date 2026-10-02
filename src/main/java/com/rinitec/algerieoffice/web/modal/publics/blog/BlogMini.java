package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogMini implements Serializable {
	private static final long serialVersionUID = -1785844087346534369L;
	
	private final String title;
	private final String identifyURL;
	
	public BlogMini(final String title, final String identify) {
		this.title = title;
		this.identifyURL = ConstraintesURL.getBrowserBlogURL(identify);
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	@Override
	public String toString() {
		return "BlogMini [title=" + title + ", identifyURL=" + identifyURL + "]";
	}

}
