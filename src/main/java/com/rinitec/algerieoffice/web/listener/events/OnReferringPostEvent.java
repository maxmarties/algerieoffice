package com.rinitec.algerieoffice.web.listener.events;

import java.util.List;
import java.util.UUID;

public class OnReferringPostEvent {

	private final List<UUID> lines;
	private final boolean token;
	private final boolean filter;
	private final boolean tag;
	
	public OnReferringPostEvent(final List<UUID> lines, final boolean token, final boolean filter, final boolean tag) {
		this.lines = lines;
		this.token = token;
		this.filter = filter;
		this.tag = tag;
	}

	public List<UUID> getLines() {
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
		return "OnReferringPostEvent [lines=" + lines + ", token=" + token + ", filter=" + filter + ", tag=" + tag + "]";
	}
	
}
