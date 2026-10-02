package com.rinitec.algerieoffice.web.listener.events;

import java.util.List;

public class OnReferringCompanyEvent {

	private final List<Long> lines;
	private final boolean token;
	private final boolean filter;
	private final boolean tag;
	
	public OnReferringCompanyEvent(final List<Long> lines, final boolean token, final boolean filter, final boolean tag) {
		this.lines = lines;
		this.token = token;
		this.filter = filter;
		this.tag = tag;
	}

	public List<Long> getLines() {
		return lines;
	}

	public boolean isToken() {
		return token;
	}

	public boolean isFilter() {
		return filter;
	}

	public boolean isTag() {
		return tag;
	}
	
	public boolean hasPresentReferring() {
		return token || filter || tag;
	}

	@Override
	public String toString() {
		return "OnReferringCompanyEvent [lines=" + lines + ", token=" + token + ", filter=" + filter + ", tag=" + tag + "]";
	}
	
}
