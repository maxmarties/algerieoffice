package com.rinitec.algerieoffice.web.modal.publics.marketplace.events;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventDetail;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentInbox;

public class ScreenInboxEvent extends DocumentInbox {
	private static final long serialVersionUID = -3389765554329222667L;
	
	private final String photoURL;
	private final List<String> eventsDate = new ArrayList<String>();
	private final List<String> eventsClock = new ArrayList<String>();
	private final List<Integer> wilayas = new ArrayList<Integer>();
	
	public ScreenInboxEvent(final Event event, final EventDetail eventDetail, final List<EventCalendar> eventsCalendar) {
		super(event, eventDetail.getDetail(), eventDetail.getDescription());
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(eventDetail.getPhotoUUID().toString());
		for (final EventCalendar eventCalendar : eventsCalendar) {
			this.eventsDate.add(DateTimeFormat.forPattern("dd/MM/yyyy").print(eventCalendar.getEventDate()));
			this.eventsClock.add(this.getFormatedClock(eventCalendar.getClockOpen()).concat(" - ").concat(this.getFormatedClock(eventCalendar.getClockClose())));
			this.wilayas.add(eventCalendar.getWilaya());
		}
	}
	
	private final String getFormatedClock(final Integer clock) {
		return clock == 24 ? "00:00" : (clock < 10 ? "0" : "").concat(String.valueOf(clock)).concat(":00");
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public List<String> getEventsDate() {
		return eventsDate;
	}

	public List<String> getEventsClock() {
		return eventsClock;
	}

	public List<Integer> getWilayas() {
		return wilayas;
	}

	@Override
	public String toString() {
		return "ScreenInboxEvent [photoURL=" + photoURL + ", eventsDate=" + eventsDate + ", eventsClock=" + eventsClock
				+ ", wilayas=" + wilayas + "]";
	}
	
}
