package com.rinitec.algerieoffice.web.modal.publics.marketplace.news;

import java.io.Serializable;
import java.util.List;

public class NewsWidgetList implements Serializable {
	private static final long serialVersionUID = 3581071639548730985L;
	
	private final long countResult;
	private final List<NewsWidgetMini> lines;
	
	public NewsWidgetList(final long countResult, final List<NewsWidgetMini> lines) {
		this.countResult = countResult;
		this.lines = lines;
	}
	
	public long getCountResult() {
		return countResult;
	}
	
	public List<NewsWidgetMini> getLines() {
		return lines;
	}
	
	public boolean isEmpty() {
		return lines.isEmpty();
	}

	@Override
	public String toString() {
		return "NewsWidgetList [countResult=" + countResult + ", lines=" + lines + "]";
	}

}
