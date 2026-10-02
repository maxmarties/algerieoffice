package com.rinitec.algerieoffice.web.modal.company.marketplace;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AnnonceLine implements Serializable {
	private static final long serialVersionUID = 1031424389829218159L;
	
	private static final int STATE_PUBLISHED = 1;
	private static final int STATE_PAUSE = 2;
	private static final int STATE_WAIT = 3;
	private static final int STATE_EXPIRED = 4;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final String period;
	private final String autor;
	private final int type;
	private final int state;
	private final String clickCount;
	private final String workCount;
	private final boolean hasPublished;
	
	public AnnonceLine(final Annonce annonce, final String autor) {
		this.id = annonce.getId().toString();
		this.title = annonce.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplacePreviewURL(annonce.getIdentify());
		this.period = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getStartDate()).concat(" - ")
				.concat(DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getEndDate()));
		this.autor = autor;
		this.type = annonce.getType();
		this.state = annonce.getStartDate().isAfterNow() ? STATE_WAIT : annonce.getEndDate().isBeforeNow() ? STATE_EXPIRED : 
			!annonce.getHasPublished() ? STATE_PAUSE : STATE_PUBLISHED;
		this.clickCount = ParseUtil.getFormattedOrder(annonce.getClickCount());
		this.workCount = ParseUtil.getFormattedOrder(annonce.getWorkCount());
		this.hasPublished = annonce.getHasPublished();
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

	public String getPeriod() {
		return period;
	}

	public String getAutor() {
		return autor;
	}

	public int getType() {
		return type;
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
		return "AnnonceLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", period=" + period
				+ ", autor=" + autor + ", type=" + type + ", state=" + state + ", clickCount=" + clickCount
				+ ", workCount=" + workCount + ", hasPublished=" + hasPublished + "]";
	}

}
