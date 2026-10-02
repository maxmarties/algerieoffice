package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.overview.Maincatalog;
import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.CatalogItem;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetCatalog implements Serializable {
	private static final long serialVersionUID = 2715404219355274455L;
	
	private final Boolean style;
	private final List<String> titles = new ArrayList<String>();
	private final List<String> photosURL = new ArrayList<String>();
	
	public ExplorerWidgetCatalog(final Maincatalog maincatalog) {
		if(maincatalog != null) {
			this.style = maincatalog.getStyle();
			final List<CatalogItem> items = new ArrayList<CatalogItem>(maincatalog.getItems());
			for (final CatalogItem item : items) {
				this.titles.add(item.getTitle());
				this.photosURL.add(ConstraintesURL.URL_PHOTOS + "?photoId=".concat(item.getPhotoUUID().toString()));
			}
		} else {
			this.style = null;
		}
	}

	public Boolean getStyle() {
		return style;
	}

	public List<String> getTitles() {
		return titles;
	}

	public List<String> getPhotosURL() {
		return photosURL;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetCatalog [style=" + style + ", titles=" + titles + ", photosURL=" + photosURL + "]";
	}

}
