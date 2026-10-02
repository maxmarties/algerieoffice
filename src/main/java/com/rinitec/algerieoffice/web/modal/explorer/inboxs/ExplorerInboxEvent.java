package com.rinitec.algerieoffice.web.modal.explorer.inboxs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventDetail;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerInboxEvent implements Serializable {
	private static final long serialVersionUID = -3496371653891277458L;
	
	private final String id;
	private final String title;
	private final String urlExtern;
	private final String photoURL;
	private final String detail;
	private final String modifiedDate;
	private final List<String> eventsDate = new ArrayList<String>();
	private final List<String> eventsClock = new ArrayList<String>();
	private final List<Integer> wilayas = new ArrayList<Integer>();
	
	public ExplorerInboxEvent(final Event event, final EventDetail eventDetail, final List<EventCalendar> eventsCalendar) {
		this.id = event.getId().toString();
		this.title = event.getTitle();
		this.urlExtern = event.getUrlExtern();
		this.detail = new String(eventDetail.getDetail());
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(eventDetail.getPhotoUUID().toString());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(event.getModifiedDate());
		for (final EventCalendar eventCalendar : eventsCalendar) {
			this.eventsDate.add(DateTimeFormat.forPattern("dd/MM/yyyy").print(eventCalendar.getEventDate()));
			this.eventsClock.add(this.getFormatedClock(eventCalendar.getClockOpen()).concat(" - ").concat(this.getFormatedClock(eventCalendar.getClockClose())));
			this.wilayas.add(eventCalendar.getWilaya());
		}
	}
	
	private final String getFormatedClock(final Integer clock) {
		return clock == 24 ? "00:00" : (clock < 10 ? "0" : "").concat(String.valueOf(clock)).concat(":00");
	}
	
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getDetail() {
		return detail;
	}

	public String getModifiedDate() {
		return modifiedDate;
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
		return "ExplorerInboxEvent [id=" + id + ", title=" + title + ", urlExtern=" + urlExtern + ", photoURL="
				+ photoURL + ", detail=" + detail + ", modifiedDate=" + modifiedDate + ", eventsDate=" + eventsDate
				+ ", eventsClock=" + eventsClock + ", wilayas=" + wilayas + "]";
	}

}
