package com.rinitec.algerieoffice.web.listener.events;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.enums.TalkType;

public class OnDocumentEvent {

	private final Long companyId;
	private final DocumentType type;
	
	public OnDocumentEvent(final Long companyId, final DocumentType type) {
		this.companyId = companyId;
		this.type = type;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public DocumentType getType() {
		return type;
	}
	
	public TalkType getTalkType() {
		switch(type) {
		case post: return TalkType.post;
		case annonce: return TalkType.annonce;
		case event: return TalkType.event;
		default: return TalkType.employe;
		}
	}

	@Override
	public String toString() {
		return "OnDocumentEvent [companyId=" + companyId + ", type=" + type + "]";
	}
	
}
