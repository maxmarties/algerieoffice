package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.Timeline;

public class ExplorerWidgetTimeline implements Serializable {
	private static final long serialVersionUID = -8137430339633257537L;
	
	private final String history;
	private final List<DateTime> linesDate = new ArrayList<DateTime>();
	private final List<String> titles = new ArrayList<String>();
	private final List<String> descriptions = new ArrayList<String>();
	
	public ExplorerWidgetTimeline(final String history, final List<Timeline> timesLine) {
		this.history = history;
		for (final Timeline timeline : timesLine) {
			this.linesDate.add(timeline.getLineDate());
			this.titles.add(timeline.getTitle());
			this.descriptions.add(timeline.getDescription());
		}
	}

	public String getHistory() {
		return history;
	}

	public List<DateTime> getLinesDate() {
		return linesDate;
	}

	public List<String> getTitles() {
		return titles;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetTimeline [history=" + history + ", linesDate=" + linesDate + ", titles=" + titles
				+ ", descriptions=" + descriptions + "]";
	}

}
