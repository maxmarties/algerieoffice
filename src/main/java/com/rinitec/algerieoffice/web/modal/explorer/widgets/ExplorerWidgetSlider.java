package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainslider;
import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.SliderItem;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetSlider implements Serializable {
	private static final long serialVersionUID = -2603965578397962098L;
	
	private final Boolean hasAutoplay;
	private final Boolean hasHover;
	private final Boolean hasNavigation;
	private final Boolean hasDots;
	private final String speed;
	private final String timeout;
	private final String animateIn;
	private final String animateOut;
	private final String animateFade;
	private final String fadeColor;
	private final String textColor;
	private final List<String> titles = new ArrayList<String>();
	private final List<String> descriptions = new ArrayList<String>();
	private final List<String> photosURL = new ArrayList<String>();
	
	public ExplorerWidgetSlider(final Mainslider mainslider) {
		if(mainslider != null) {
			this.hasAutoplay = mainslider.getHasAutoplay();
			this.hasHover = mainslider.getHasHover();
			this.hasNavigation = mainslider.getHasNavigation();
			this.hasDots = mainslider.getHasDots();
			this.speed = mainslider.getSpeed();
			this.timeout = mainslider.getTimeout();
			this.animateIn = mainslider.getAnimateIn();
			this.animateOut = mainslider.getAnimateOut();
			this.animateFade = mainslider.getAnimateFade();
			this.fadeColor = mainslider.getFadeColor();
			this.textColor = mainslider.getTextColor();
			final List<SliderItem> items = new ArrayList<SliderItem>(mainslider.getItems());
			for (final SliderItem item : items) {
				this.titles.add(item.getTitle());
				this.descriptions.add(item.getDescription());
				this.photosURL.add(ConstraintesURL.URL_PHOTOS + "?photoId=".concat(item.getPhotoUUID().toString()));
			}
		} else {
			this.hasAutoplay = this.hasHover = this.hasNavigation = this.hasDots = null;
			this.speed = this.timeout = this.animateIn = this.animateOut = this.animateFade = this.fadeColor = this.textColor = null;
		}
	}

	public Boolean getHasAutoplay() {
		return hasAutoplay;
	}

	public Boolean getHasHover() {
		return hasHover;
	}

	public Boolean getHasNavigation() {
		return hasNavigation;
	}

	public Boolean getHasDots() {
		return hasDots;
	}

	public String getSpeed() {
		return speed;
	}

	public String getTimeout() {
		return timeout;
	}

	public String getAnimateIn() {
		return animateIn;
	}

	public String getAnimateOut() {
		return animateOut;
	}

	public String getAnimateFade() {
		return animateFade;
	}

	public String getFadeColor() {
		return fadeColor;
	}

	public String getTextColor() {
		return textColor;
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

	@Override
	public String toString() {
		return "ExplorerWidgetSlider [hasAutoplay=" + hasAutoplay + ", hasHover=" + hasHover + ", hasNavigation="
				+ hasNavigation + ", hasDots=" + hasDots + ", speed=" + speed + ", timeout=" + timeout + ", animateIn="
				+ animateIn + ", animateOut=" + animateOut + ", animateFade=" + animateFade + ", fadeColor=" + fadeColor
				+ ", textColor=" + textColor + ", titles=" + titles + ", descriptions=" + descriptions + ", photosURL="
				+ photosURL + "]";
	}

}
