package com.rinitec.algerieoffice.web.modal.mapsite;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class EmployeMapsite extends DocumentMapsite {
	private static final long serialVersionUID = -2177219364787796634L;

	public EmployeMapsite(final String identify, final DateTime modifiedDate, final String url) {
		super(ConstraintesURL.getMarketplaceEmployeURL(identify, url), modifiedDate);
	}

}
