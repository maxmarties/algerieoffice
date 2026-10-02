package com.rinitec.algerieoffice.web.listener.events;

import com.rinitec.algerieoffice.enums.TalkType;

public class OnTalkEvent {

	private final Long companyId;
	private final TalkType type;
	
	public OnTalkEvent(final Long companyId, final TalkType type) {
		this.companyId = companyId;
		this.type = type;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public TalkType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "OnTalkEvent [companyId=" + companyId + ", type=" + type + "]";
	}
	
}
