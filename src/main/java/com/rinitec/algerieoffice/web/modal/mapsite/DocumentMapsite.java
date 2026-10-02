package com.rinitec.algerieoffice.web.modal.mapsite;

import java.io.Serializable;

import org.joda.time.DateTime;

public class DocumentMapsite implements Serializable {
	private static final long serialVersionUID = 5700494881612255575L;
	
	private final String documentURL;
	private final DateTime modifiedDate;
	
	public DocumentMapsite(final String documentURL, final DateTime modifiedDate) {
		this.documentURL = documentURL;
		this.modifiedDate = modifiedDate;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	@Override
	public String toString() {
		return "DocumentMapsite [documentURL=" + documentURL + ", modifiedDate=" + modifiedDate + "]";
	}

}
