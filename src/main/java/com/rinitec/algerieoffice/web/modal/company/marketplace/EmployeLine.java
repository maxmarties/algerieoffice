package com.rinitec.algerieoffice.web.modal.company.marketplace;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class EmployeLine implements Serializable {
	private static final long serialVersionUID = -312916037847122137L;
	
	private static final int STATE_PUBLISHED = 1;
	private static final int STATE_PAUSE = 2;
	private static final int STATE_EXPIRED = 4;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final String expiredDate;
	private final String autor;
	private final int contract;
	private final int state;
	private final String clickCount;
	private final String workCount;
	private final boolean hasPublished;
	
	public EmployeLine(final Employe employe, final String autor) {
		this.id = employe.getId().toString();
		this.title = employe.getTitle();
		this.identifyURL = ConstraintesURL.getEmployePreviewURL(employe.getIdentify());
		this.expiredDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getExpiredDate());
		this.autor = autor;
		this.contract = employe.getContract();
		this.state = employe.getExpiredDate().isBeforeNow() ? STATE_EXPIRED : !employe.getHasPublished() ? STATE_PAUSE : STATE_PUBLISHED;
		this.clickCount = ParseUtil.getFormattedOrder(employe.getClickCount());
		this.workCount = ParseUtil.getFormattedOrder(employe.getWorkCount());
		this.hasPublished = employe.getHasPublished();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public String getAutor() {
		return autor;
	}

	public int getContract() {
		return contract;
	}

	public int getState() {
		return state;
	}

	public String getClickCount() {
		return clickCount;
	}
	
	public String getWorkCount() {
		return workCount;
	}

	public boolean isHasPublished() {
		return hasPublished;
	}

	@Override
	public String toString() {
		return "EmployeLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", expiredDate="
				+ expiredDate + ", autor=" + autor + ", contract=" + contract + ", state=" + state + ", clickCount="
				+ clickCount + ", workCount=" + workCount + ", hasPublished=" + hasPublished + "]";
	}

}
