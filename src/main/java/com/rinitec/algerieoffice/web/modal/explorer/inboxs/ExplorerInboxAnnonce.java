package com.rinitec.algerieoffice.web.modal.explorer.inboxs;

import java.io.Serializable;
import java.util.List;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.AnnonceDetail;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerInboxAnnonce implements Serializable {
	private static final long serialVersionUID = 7612485526320950005L;
	
	private final String id;
	private final String title;
	private final String detail;
	private final String urlExtern;
	private final Integer type;
	private final Integer visibility;
	private final String startDate;
	private final String endDate;
	private final String modifiedDate;
	private final String urlFile;
	private final List<Integer> sectors;
	private final List<Integer> wilayas;
	
	public ExplorerInboxAnnonce(final Annonce annonce, final AnnonceDetail annonceDetail, final List<Integer> sectors, final List<Integer> wilayas) {
		this.id = annonce.getId().toString();
		this.title = annonce.getTitle();
		this.detail = new String(annonceDetail.getDetail());
		this.urlExtern = annonceDetail.getUrlExtern();
		this.type = annonce.getType();
		this.visibility = annonceDetail.getVisibility();
		this.startDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getStartDate());
		this.endDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getEndDate());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getModifiedDate());
		this.urlFile = annonceDetail.getFileUUID() != null ? ConstraintesURL.URL_FILES + "?fileId=".concat(annonceDetail.getFileUUID().toString()) : null;
		this.sectors = sectors;
		this.wilayas = wilayas;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDetail() {
		return detail;
	}

	public String getUrlExtern() {
		return urlExtern;
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

	public String getModifiedDate() {
		return modifiedDate;
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
		return "ExplorerInboxAnnonce [id=" + id + ", title=" + title + ", detail=" + detail + ", urlExtern=" + urlExtern
				+ ", type=" + type + ", visibility=" + visibility + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", modifiedDate=" + modifiedDate + ", urlFile=" + urlFile + ", sectors=" + sectors + ", wilayas="
				+ wilayas + "]";
	}

}
