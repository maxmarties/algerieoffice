package com.rinitec.algerieoffice.web.modal.company.tools;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;

public class RecycleEmployeLine implements Serializable {
	private static final long serialVersionUID = -4541698259314701888L;
	
	private final String id;
	private final String title;
	private final String expiredDate;
	private final String autor;
	private final String modifiedDate;
	private final int contract;
	
	public RecycleEmployeLine(final Employe employe, final String autor) {
		this.id = employe.getId().toString();
		this.title = employe.getTitle();
		this.expiredDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getExpiredDate());
		this.autor = autor;
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getModifiedDate());
		this.contract = employe.getContract();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public String getAutor() {
		return autor;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public int getContract() {
		return contract;
	}

	@Override
	public String toString() {
		return "RecycleEmployeLine [id=" + id + ", title=" + title + ", expiredDate=" + expiredDate + ", autor=" + autor
				+ ", modifiedDate=" + modifiedDate + ", contract=" + contract + "]";
	}

}
