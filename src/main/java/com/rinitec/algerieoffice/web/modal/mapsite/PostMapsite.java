package com.rinitec.algerieoffice.web.modal.mapsite;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class PostMapsite extends DocumentMapsite {
	private static final long serialVersionUID = 2701502511452744051L;
	
	public PostMapsite(final String identify, final DateTime modifiedDate, final String url) {
		super(ConstraintesURL.getMarketplacePostURL(identify, url), modifiedDate);
	}

}
