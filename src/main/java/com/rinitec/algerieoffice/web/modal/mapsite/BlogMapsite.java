package com.rinitec.algerieoffice.web.modal.mapsite;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogMapsite extends DocumentMapsite {
	private static final long serialVersionUID = -3451453447604681560L;

	public BlogMapsite(final String identify, final DateTime modifiedDate) {
		super(ConstraintesURL.getBrowserBlogURL(identify), modifiedDate);
	}

}
