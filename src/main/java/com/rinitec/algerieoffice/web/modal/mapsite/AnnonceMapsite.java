package com.rinitec.algerieoffice.web.modal.mapsite;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AnnonceMapsite extends DocumentMapsite {
	private static final long serialVersionUID = -7927597038794015620L;

	public AnnonceMapsite(final String identify, final DateTime modifiedDate, final String url) {
		super(ConstraintesURL.getMarketplaceAnnonceURL(identify, url), modifiedDate);
	}
	
}
