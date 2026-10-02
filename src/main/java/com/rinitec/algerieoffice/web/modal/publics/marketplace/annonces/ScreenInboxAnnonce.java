package com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces;

import java.util.List;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceDetail;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentInbox;

public class ScreenInboxAnnonce extends DocumentInbox {
	private static final long serialVersionUID = -2873466786078478252L;
	
	private final Integer type;
	private final Integer visibility;
	private final String startDate;
	private final String endDate;
	private final String urlFile;
	private final List<Integer> sectors;
	private final List<Integer> wilayas;
	
	public ScreenInboxAnnonce(final Annonce annonce, final AnnonceDetail annonceDetail, final List<Integer> sectors, final List<Integer> wilayas) {
		super(annonce, annonceDetail.getDetail(), annonceDetail.getUrlExtern());
		this.type = annonce.getType();
		this.visibility = annonceDetail.getVisibility();
		this.startDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getStartDate());
		this.endDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getEndDate());
		this.urlFile = annonceDetail.getFileUUID() != null ? ConstraintesURL.URL_FILES + "?fileId=".concat(annonceDetail.getFileUUID().toString()) : null;
		this.sectors = sectors;
		this.wilayas = wilayas;
	}

	public Integer getType() {
		return type;
	}

	public Integer getVisibility() {
		return visibility;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getUrlFile() {
		return urlFile;
	}

	public List<Integer> getSectors() {
		return sectors;
	}

	public List<Integer> getWilayas() {
		return wilayas;
	}

	@Override
	public String toString() {
		return "ScreenInboxAnnonce [type=" + type + ", visibility=" + visibility + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", urlFile=" + urlFile + ", sectors=" + sectors + ", wilayas=" + wilayas + "]";
	}

}
