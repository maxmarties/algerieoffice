package com.rinitec.algerieoffice.web.modal.company.tools;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;

public class RecycleAnnonceLine implements Serializable {
	private static final long serialVersionUID = 4234404761503886960L;
	
	private final String id;
	private final String title;
	private final String period;
	private final String autor;
	private final String modifiedDate;
	private final int type;
	
	public RecycleAnnonceLine(final Annonce annonce, final String autor) {
		this.id = annonce.getId().toString();
		this.title = annonce.getTitle();
		this.period = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getStartDate()).concat(" - ")
				.concat(DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getEndDate()));
		this.autor = autor;
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getModifiedDate());
		this.type = annonce.getType();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getPeriod() {
		return period;
	}

	public String getAutor() {
		return autor;
	}
	
	public String getModifiedDate() {
		return modifiedDate;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "RecycleAnnonceLine [id=" + id + ", title=" + title + ", period=" + period + ", autor=" + autor
				+ ", modifiedDate=" + modifiedDate + ", type=" + type + "]";
	}

}
