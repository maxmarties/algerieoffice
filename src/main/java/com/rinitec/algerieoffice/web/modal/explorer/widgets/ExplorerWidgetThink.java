package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainthink;
import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.ThinkItem;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetThink implements Serializable {
	private static final long serialVersionUID = 348563126789245369L;
	
	private final List<String> thinks = new ArrayList<String>();
	private final List<String> titles = new ArrayList<String>();
	private final List<String> descriptions = new ArrayList<String>();
	private final List<String> photosURL = new ArrayList<String>();
	
	public ExplorerWidgetThink(final Mainthink mainthink) {
		if(mainthink != null) {
			final List<ThinkItem> items = new ArrayList<ThinkItem>(mainthink.getItems());
			for (final ThinkItem item : items) {
				this.thinks.add(item.getThink());
				this.titles.add(item.getTitle());
				this.descriptions.add(item.getDescription());
				this.photosURL.add(ConstraintesURL.URL_PHOTOS + "?photoId=".concat(item.getPhotoUUID().toString()));
			}
		}
	}

	public List<String> getThinks() {
		return thinks;
	}

	public List<String> getTitles() {
		return titles;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public List<String> getPhotosURL() {
		return photosURL;
	}
	
	public boolean hasPresent() {
		return thinks.size() >= 4;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetThink [thinks=" + thinks + ", titles=" + titles + ", descriptions=" + descriptions
				+ ", photosURL=" + photosURL + "]";
	}

}
