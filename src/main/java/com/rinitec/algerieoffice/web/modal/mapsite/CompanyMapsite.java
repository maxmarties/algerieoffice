package com.rinitec.algerieoffice.web.modal.mapsite;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CompanyMapsite extends DocumentMapsite {
	private static final long serialVersionUID = 6260214564918512321L;
	
	public CompanyMapsite(final String url, final DateTime modifiedDate) {
		super(ConstraintesURL.URL_COMPANIES.concat("/").concat(url), modifiedDate);
	}

}
