package com.rinitec.algerieoffice.web.modal.publics.marketplace.events;

import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentWidgetMini;

public class EventWidgetMini extends DocumentWidgetMini {
	private static final long serialVersionUID = -2400415495184998681L;
	
	private final String photoURL;
	private final String eventDate;
	private final String eventClock;
	private final Integer wilaya;
	
	public EventWidgetMini(final Event event, final EventCalendar eventCalendar, final UUID photoUUID, final String description, 
			final Company company, final String url, final Long userId, final DateTime forOrder1, final Integer forOrder2, final Integer forOrder3) {
		super(event, description, company, url, userId);
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(photoUUID.toString());
		this.eventDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(eventCalendar.getEventDate());
		this.eventClock = this.getFormatedClock(eventCalendar.getClockOpen()).concat(" - ").concat(getFormatedClock(eventCalendar.getClockClose()));
		this.wilaya = eventCalendar.getWilaya();
	}
	
	private final String getFormatedClock(final Integer clock) {
		return clock == 24 ? "00:00" : (clock < 10 ? "0" : "").concat(String.valueOf(clock)).concat(":00");
	}

	public String getPhotoURL() {
		return photoURL;
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
		return "EventWidgetMini [photoURL=" + photoURL + ", eventDate=" + eventDate + ", eventClock=" + eventClock
				+ ", wilaya=" + wilaya + "]";
	}

}
