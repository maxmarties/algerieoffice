package com.rinitec.algerieoffice.web.listener.events;

import com.rinitec.algerieoffice.enums.DocumentType;

public class OnAccessDocumentEvent {

	private final String documentId;
	private final DocumentType type;
	private final boolean hasClick;
	
	public OnAccessDocumentEvent(final String documentId, final DocumentType type, final boolean hasClick) {
		this.documentId = documentId;
		this.type = type;
		this.hasClick = hasClick;
	}

	public String getDocumentId() {
		return documentId;
	}

	public DocumentType getType() {
		return type;
	}

	public boolean isHasClick() {
		return hasClick;
	}

	@Override
	public String toString() {
		return "OnAccessDocumentEvent [documentId=" + documentId + ", type=" + type + ", hasClick=" + hasClick + "]";
	}
	
}
