package com.rinitec.algerieoffice.web.modal.explorer.inboxs;

import java.io.Serializable;

import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetSlider;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetThink;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetTimeline;

public class ExplorerInboxtHome implements Serializable {
	private static final long serialVersionUID = 5462294637427019166L;
	
	private final ExplorerWidgetSlider slider;
	private final ExplorerWidgetTimeline timeline;
	private final ExplorerWidgetThink think;
	
	public ExplorerInboxtHome(final ExplorerWidgetSlider slider, final ExplorerWidgetTimeline timeline, 
			final ExplorerWidgetThink think) {
		this.slider = slider;
		this.timeline = timeline;
		this.think = think;
	}

	public ExplorerWidgetSlider getSlider() {
		return slider;
	}

	public ExplorerWidgetTimeline getTimeline() {
		return timeline;
	}
	
	public ExplorerWidgetThink getThink() {
		return think;
	}

	@Override
	public String toString() {
		return "ExplorerInboxtHome [slider=" + slider + ", timeline=" + timeline + "]";
	}

}
