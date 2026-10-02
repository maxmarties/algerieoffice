package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Faq;

public class ExplorerWidgetFaq implements Serializable {
	private static final long serialVersionUID = 4972047277081978274L;
	
	private final String title;
	private final String description;
	private final String urlExtern;
	
	public ExplorerWidgetFaq(final Faq faq) {
		this.title = faq.getQuestion();
		this.description = new String(faq.getDetail());
		this.urlExtern = faq.getUrlExtern();
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetFaq [title=" + title + ", description=" + description + ", urlExtern=" + urlExtern + "]";
	}

}
