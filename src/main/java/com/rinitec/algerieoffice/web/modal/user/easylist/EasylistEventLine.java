package com.rinitec.algerieoffice.web.modal.user.easylist;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;

public class EasylistEventLine extends EasylistDocumentLine {
	private static final long serialVersionUID = -8637207743650599464L;
	
	private final String eventDate;
	private final String eventClock;
	private final Integer wilaya;
	
	public EasylistEventLine(final Event event, final EventCalendar eventCalendar, final Company company, final String companyURL) {
		super(event, company, companyURL);
		this.eventDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(eventCalendar.getEventDate());
		this.eventClock = this.getFormatedClock(eventCalendar.getClockOpen()).concat(" - ").concat(this.getFormatedClock(eventCalendar.getClockClose()));
		this.wilaya = eventCalendar.getWilaya();
	}
	
	private final String getFormatedClock(final Integer clock) {
		return clock == 24 ? "00:00" : (clock < 10 ? "0" : "").concat(String.valueOf(clock)).concat(":00");
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
		return "EasylistEventLine [eventDate=" + eventDate + ", eventClock=" + eventClock + ", wilaya=" + wilaya + "]";
	}
	
}
