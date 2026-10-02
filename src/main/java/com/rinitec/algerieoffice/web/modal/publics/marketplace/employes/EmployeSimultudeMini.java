package com.rinitec.algerieoffice.web.modal.publics.marketplace.employes;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentSimultudeMini;

public class EmployeSimultudeMini extends DocumentSimultudeMini {
	private static final long serialVersionUID = 5207233926574437506L;
	
	public EmployeSimultudeMini(final String title, final String identify, final String url, final DateTime forOrder) {
		super(title, ConstraintesURL.getMarketplaceEmployeURL(identify, url));
	}
	
}
