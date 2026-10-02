package com.rinitec.algerieoffice.persistence.result;

import java.util.UUID;

import org.joda.time.DateTime;

public class ActualityMini {

	private final String title;
	private final DateTime actuDate;
	private final String photoURL;
	
	public ActualityMini(final String title, final DateTime actuDate, final UUID photoUUID) {
		this.title = title;
		this.actuDate = actuDate;
		this.photoURL = "/media/photo?photoId=".concat(photoUUID.toString()).concat("&width=68&height=38");
	}

	public String getTitle() {
		return title;
	}

	public DateTime getActuDate() {
		return actuDate;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	@Override
	public String toString() {
		return "ActualityMini [title=" + title + ", actuDate=" + actuDate + ", photoURL=" + photoURL + "]";
	}
	
}
