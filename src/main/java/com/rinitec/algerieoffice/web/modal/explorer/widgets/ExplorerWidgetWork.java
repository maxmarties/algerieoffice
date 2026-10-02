package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;
import java.util.UUID;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetWork implements Serializable {
	private static final long serialVersionUID = 6639747584731583661L;
	
	private final String photoURL;
	private final String title;
	private final String identifyURL;
	private final String expertise;
	private final DateTime workDate;
	
	public ExplorerWidgetWork(final Work work, final UUID photoUUID, final String urlWorks) {
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(photoUUID.toString());
		this.title = work.getTitle();
		this.identifyURL = urlWorks.concat("/").concat(work.getIdentify());
		this.expertise = work.getExpertise().replaceAll(",", ", ");
		this.workDate = work.getWorkDate();
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

	public String getExpertise() {
		return expertise;
	}
	
	public DateTime getWorkDate() {
		return workDate;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetWork [photoURL=" + photoURL + ", title=" + title + ", identifyURL=" + identifyURL
				+ ", expertise=" + expertise + ", workDate=" + workDate + "]";
	}

}
