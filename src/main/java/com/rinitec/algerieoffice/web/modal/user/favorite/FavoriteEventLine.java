package com.rinitec.algerieoffice.web.modal.user.favorite;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventCalendar;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;

public class FavoriteEventLine extends FavoriteDocumentLine {
	private static final long serialVersionUID = -1892363210808239609L;
	
	private final String eventDate;
	private final String eventClock;
	private final Integer wilaya;
	
	public FavoriteEventLine(final FavoriteDocument favoriteDocument, final Event event, final EventCalendar eventCalendar, final String tradename, 
			final String companyURL) {
		super(favoriteDocument, event, tradename, companyURL);
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
		return "FavoriteEventLine [eventDate=" + eventDate + ", eventClock=" + eventClock + ", wilaya=" + wilaya + "]";
	}
	
}
