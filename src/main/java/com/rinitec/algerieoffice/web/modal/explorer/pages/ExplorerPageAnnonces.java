package com.rinitec.algerieoffice.web.modal.explorer.pages;

import java.util.Arrays;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetSticky;

public class ExplorerPageAnnonces extends ExplorerPage {
	private static final long serialVersionUID = 345722165486160217L;
	
	private final ExplorerWidgetSticky sticky;
	private final Long[] countsMarket;
	
	public ExplorerPageAnnonces(final ExplorerMeta meta, final ExplorerWidgetSticky sticky, final Long[] countsMarket) {
		super(meta);
		this.sticky = sticky;
		this.countsMarket  = countsMarket;
	}

	public ExplorerWidgetSticky getSticky() {
		return sticky;
	}

	public Long[] getCountsMarket() {
		return countsMarket;
	}
	
	public long countAnnonces() {
		long countAnnonces = 0;
		for(int i = 0; i < 5; i++) {
			countAnnonces += countsMarket[i];
		}
		return countAnnonces;
	}

	@Override
	public String getInboxId() {
		return null;
	}

	@Override
	public String toString() {
		return "ExplorerPageAnnonces [sticky=" + sticky + ", countsMarket=" + Arrays.toString(countsMarket) + "]";
	}
	
}
