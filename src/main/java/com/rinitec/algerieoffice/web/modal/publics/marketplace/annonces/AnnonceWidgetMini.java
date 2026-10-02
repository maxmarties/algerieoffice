package com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentWidgetMini;

public class AnnonceWidgetMini extends DocumentWidgetMini {
	private static final long serialVersionUID = 4317497692476600313L;
	
	private final String startDate;
	private final String endDate;
	private final Integer type;
	private final Integer visibility;
	
	public AnnonceWidgetMini(final Annonce annonce, final Integer visibility, final Company company, final String url, final Long userId, 
			final DateTime forOrder1, final DateTime forOrder2, final Integer forOrder3) {
		super(annonce, company, url, userId);
		this.startDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getStartDate());
		this.endDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getEndDate());
		this.type = annonce.getType();
		this.visibility = visibility;
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
		return "AnnonceWidgetMini [startDate=" + startDate + ", endDate=" + endDate + ", type=" + type + ", visibility=" + visibility + "]";
	}

}
