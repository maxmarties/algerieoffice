package com.rinitec.algerieoffice.web.modal.explorer.pages;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetAbout;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetCatalog;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetSticky;

public class ExplorerPagePresentation extends ExplorerPage {
	private static final long serialVersionUID = -2658460151431330379L;
	
	private final String presentation;
	private final ExplorerWidgetSticky sticky;
	private final ExplorerWidgetAbout about;
	private final ExplorerWidgetCatalog catalog;
	
	public ExplorerPagePresentation(final ExplorerMeta meta, final String presentation, final ExplorerWidgetSticky sticky, 
			final ExplorerWidgetAbout about, final ExplorerWidgetCatalog catalog) {
		super(meta);
		this.presentation = presentation;
		this.sticky = sticky;
		this.about = about;
		this.catalog = catalog;
	}
	
	public String getPresentation() {
		return presentation;
	}
	
	public ExplorerWidgetSticky getSticky() {
		return sticky;
	}
	
	public ExplorerWidgetAbout getAbout() {
		return about;
	}
	
	public ExplorerWidgetCatalog getCatalog() {
		return catalog;
	}
	
	@Override
	public String getInboxId() {
		return null;
	}

	@Override
	public String toString() {
		return "ExplorerPagePresentation [presentation=" + presentation + ", sticky=" + sticky + ", about=" + about + ", catalog=" + catalog + "]";
	}

}
