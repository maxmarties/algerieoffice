package com.rinitec.algerieoffice.web.modal.explorer.pages;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetSticky;

public class ExplorerPageElements extends ExplorerPage {
	private static final long serialVersionUID = 2392840668191881828L;
	
	private final ExplorerWidgetSticky sticky;
	
	public ExplorerPageElements(final ExplorerMeta meta, final ExplorerWidgetSticky sticky) {
		super(meta);
		this.sticky = sticky;
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
		return "ExplorerPageElements [sticky=" + sticky + "]";
	}
	
}
