package com.rinitec.algerieoffice.persistence.result;

import java.util.UUID;

import org.joda.time.DateTime;

public class EventMini {

	private final String title;
	private final String identify;
	private final String description;
	private final UUID photoUUID;
	private final DateTime eventDate;
	private final Integer clockOpen;
	private final Integer clockClose;
	private final Integer wilaya;
	
	public EventMini(final String title, final String identify, final String description, final UUID photoUUID, final DateTime eventDate, 
			final Integer clockOpen, final Integer clockClose, final Integer wilaya) {
		this.title = title;
		this.identify = identify;
		this.description = description;
		this.photoUUID = photoUUID;
		this.eventDate = eventDate;
		this.clockOpen = clockOpen;
		this.clockClose = clockClose;
		this.wilaya = wilaya;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentify() {
		return identify;
	}

	public String getDescription() {
		return description;
	}

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public DateTime getEventDate() {
		return eventDate;
	}

	public Integer getClockOpen() {
		return clockOpen;
	}

	public Integer getClockClose() {
		return clockClose;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	@Override
	public String toString() {
		return "EventMini [title=" + title + ", identify=" + identify + ", description=" + description + ", photoUUID="
				+ photoUUID + ", eventDate=" + eventDate + ", clockOpen=" + clockOpen + ", clockClose=" + clockClose
				+ ", wilaya=" + wilaya + "]";
	}
	
}
