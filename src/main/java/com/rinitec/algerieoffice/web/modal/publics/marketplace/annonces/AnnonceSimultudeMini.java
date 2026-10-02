package com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentSimultudeMini;

public class AnnonceSimultudeMini extends DocumentSimultudeMini {
	private static final long serialVersionUID = -457132055687292774L;
	
	public AnnonceSimultudeMini(final String title, final String identify, final String url, final DateTime forOrder) {
		super(title, ConstraintesURL.getMarketplaceAnnonceURL(identify, url));
	}

}
