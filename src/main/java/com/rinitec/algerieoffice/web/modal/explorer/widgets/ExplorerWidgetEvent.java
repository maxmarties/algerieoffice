package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.result.EventMini;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetEvent implements Serializable {
	private static final long serialVersionUID = -1837673812836699085L;
	
	private final String photoURL;
	private final String title;
	private final String identifyURL;
	private final String description;
	private final String eventDate;
	private final String eventClock;
	private final Integer wilaya;
	
	public ExplorerWidgetEvent(final EventMini eventMini, final String urlEvents) {
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(eventMini.getPhotoUUID().toString());
		this.title = eventMini.getTitle();
		this.identifyURL = urlEvents.concat("/").concat(eventMini.getIdentify());
		this.description = eventMini.getDescription();
		this.eventDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(eventMini.getEventDate());
		this.eventClock = this.getFormatedClock(eventMini.getClockOpen()).concat(" - ").concat(getFormatedClock(eventMini.getClockClose()));
		this.wilaya = eventMini.getWilaya();
	}
	
	private final String getFormatedClock(final Integer clock) {
		return clock == 24 ? "00:00" : (clock < 10 ? "0" : "").concat(String.valueOf(clock)).concat(":00");
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getDescription() {
		return description;
	}

	public String getEventDate() {
		return eventDate;
	}

	public String getEventClock() {
		return eventClock;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetEvent [photoURL=" + photoURL + ", title=" + title + ", identifyURL=" + identifyURL
				+ ", description=" + description + ", eventDate=" + eventDate + ", eventClock=" + eventClock
				+ ", wilaya=" + wilaya + "]";
	}

}
