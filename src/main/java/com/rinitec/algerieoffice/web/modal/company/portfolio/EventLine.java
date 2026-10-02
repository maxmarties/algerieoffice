package com.rinitec.algerieoffice.web.modal.company.portfolio;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class EventLine implements Serializable {
	private static final long serialVersionUID = -8116379696078292404L;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final String keysword;
	private final String urlExtern;
	private final String autor;
	private final String modifiedDate;
	private final String clickCount;
	private final boolean hasPublished;
	
	public EventLine(final Event event, final String autor) {
		this.id = event.getId().toString();
		this.title = event.getTitle();
		this.identifyURL = ConstraintesURL.getEventPreviewURL(event.getIdentify());
		this.keysword = !StringUtils.isEmpty(event.getKeysword()) ? event.getKeysword().replaceAll(",", ", ") : "-";
		this.urlExtern = event.getUrlExtern();
		this.autor = autor;
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(event.getModifiedDate());
		this.clickCount = ParseUtil.getFormattedOrder(event.getClickCount());
		this.hasPublished = event.getHasPublished();
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

	public String getKeysword() {
		return keysword;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public String getAutor() {
		return autor;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getClickCount() {
		return clickCount;
	}

	public boolean isHasPublished() {
		return hasPublished;
	}

	@Override
	public String toString() {
		return "EventLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", keysword=" + keysword
				+ ", urlExtern=" + urlExtern + ", autor=" + autor + ", modifiedDate=" + modifiedDate + ", clickCount="
				+ clickCount + ", hasPublished=" + hasPublished + "]";
	}

}
