package com.rinitec.algerieoffice.web.modal.explorer.pages;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetSticky;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetTimeline;

public class ExplorerPageTimeline extends ExplorerPage {
	private static final long serialVersionUID = -4540963651122333781L;
	
	private final ExplorerWidgetTimeline timeline;
	private final ExplorerWidgetSticky sticky;
	
	public ExplorerPageTimeline(final ExplorerMeta meta, final ExplorerWidgetTimeline timeline, final ExplorerWidgetSticky sticky) {
		super(meta);
		this.timeline = timeline;
		this.sticky = sticky;
	}
	
	public ExplorerWidgetTimeline getTimeline() {
		return timeline;
	}
	
	public ExplorerWidgetSticky getSticky() {
		return sticky;
	}
	
	@Override
	public String getInboxId() {
		return null;
	}

	@Override
	public String toString() {
		return "ExplorerPageTimeline [timeline=" + timeline + ", sticky=" + sticky + "]";
	}
	
}
