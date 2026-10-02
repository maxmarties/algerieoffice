package com.rinitec.algerieoffice.web.modal.publics.marketplace.events;

import java.io.Serializable;
import java.util.List;

public class EventsWidgetList implements Serializable {
	private static final long serialVersionUID = -8935556286349519642L;
	
	private final long countResult;
	private final List<EventWidgetMini> lines;
	
	public EventsWidgetList(final long countResult, final List<EventWidgetMini> lines) {
		this.countResult = countResult;
		this.lines = lines;
	}

	public long getCountResult() {
		return countResult;
	}

	public List<EventWidgetMini> getLines() {
		return lines;
	}
	
	public boolean isEmpty() {
		return lines.isEmpty();
	}

	@Override
	public String toString() {
		return "EventsWidgetList [countResult=" + countResult + ", lines=" + lines + "]";
	}

}
