package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;


import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;

public class ExplorerWidgetAnnonce implements Serializable {
	private static final long serialVersionUID = 8953709726023617267L;
	
	private final String title;
	private final String identifyURL;
	private final String description;
	private final String startDate;
	private final String endDate;
	private final Integer type;
	private final Integer visibility;
	
	public ExplorerWidgetAnnonce(final Annonce annonce, final Integer visibility, final String urlMarketplace) {
		this.title = annonce.getTitle();
		this.identifyURL = urlMarketplace.concat("/").concat(annonce.getIdentify());
		this.description = annonce.getDescription();
		this.startDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getStartDate());
		this.endDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getEndDate());
		this.type = annonce.getType();
		this.visibility = visibility;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getDescription() {
		return description;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public Integer getType() {
		return type;
	}

	public Integer getVisibility() {
		return visibility;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetAnnonce [title=" + title + ", identifyURL=" + identifyURL + ", description=" + description
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", type=" + type + ", visibility=" + visibility + "]";
	}

}
