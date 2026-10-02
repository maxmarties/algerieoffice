package com.rinitec.algerieoffice.web.modal.mapsite;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class EventMapsite extends DocumentMapsite {
	private static final long serialVersionUID = -5017506296520419948L;

	public EventMapsite(final String identify, final DateTime modifiedDate, final String url) {
		super(ConstraintesURL.getMarketplaceEventURL(identify, url), modifiedDate);
	}

}
