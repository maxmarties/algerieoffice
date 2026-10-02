package com.rinitec.algerieoffice.web.modal.publics.marketplace;

import java.io.Serializable;

public class DocumentSimultudeMini implements Serializable {
	private static final long serialVersionUID = 6082220241251428287L;
	
	private final String title;
	private final String identifyURL;
	
	public DocumentSimultudeMini(final String title, final String identifyURL) {
		this.title = title;
		this.identifyURL = identifyURL;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	@Override
	public String toString() {
		return "DocumentSimultudeMini [title=" + title + ", identifyURL=" + identifyURL + "]";
	}

}
