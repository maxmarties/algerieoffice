package com.rinitec.algerieoffice.web.modal.publics.marketplace.events;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentSimultudeMini;

public class EventSimultudeMini extends DocumentSimultudeMini {
	private static final long serialVersionUID = 2874365758251688342L;

	public EventSimultudeMini(final String title, final String identify, final String url, final DateTime forOrder) {
		super(title, ConstraintesURL.getMarketplaceEventURL(identify, url));
	}
	
}
