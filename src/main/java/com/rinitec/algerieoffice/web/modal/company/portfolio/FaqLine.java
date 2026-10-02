package com.rinitec.algerieoffice.web.modal.company.portfolio;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Faq;

public class FaqLine implements Serializable {
	private static final long serialVersionUID = -2517390367783149722L;
	
	private final String id;
	private final String question;
	private final String urlExtern;
	private final String autor;
	private final String modifiedDate;
	private final boolean hasPublished;
	
	public FaqLine(final Faq faq, final String autor) {
		this.id = faq.getId().toString();
		this.question = faq.getQuestion();
		this.urlExtern = faq.getUrlExtern();
		this.autor = autor;
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(faq.getModifiedDate());
		this.hasPublished = faq.getHasPublished();
	}

	public String getId() {
		return id;
	}

	public String getQuestion() {
		return question;
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

	public boolean isHasPublished() {
		return hasPublished;
	}

	@Override
	public String toString() {
		return "FaqLine [id=" + id + ", question=" + question + ", urlExtern=" + urlExtern + ", autor=" + autor
				+ ", modifiedDate=" + modifiedDate + ", hasPublished=" + hasPublished + "]";
	}

}
