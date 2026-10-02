package com.rinitec.algerieoffice.web.modal.company.tools;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class RecyclePromoteLine implements Serializable {
	private static final long serialVersionUID = 327573762110666519L;
	
	private final String id;
	private final String title;
	private final String url;
	private final String autor;
	private final String creditCount;
	private final int potentiel;
	
	public RecyclePromoteLine(final Promote promote, final String autor) {
		this.id = promote.getId().toString();
		this.title = promote.getTitle();
		this.url = promote.getUrl();
		this.autor = autor;
		this.creditCount = ParseUtil.getFormattedOrder(promote.getCreditCount());
		this.potentiel = promote.parseCreditCount();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getAutor() {
		return autor;
	}

	public String getCreditCount() {
		return creditCount;
	}

	public int getPotentiel() {
		return potentiel;
	}

	@Override
	public String toString() {
		return "RecyclePromoteLine [id=" + id + ", title=" + title + ", url=" + url + ", autor=" + autor
				+ ", creditCount=" + creditCount + ", potentiel=" + potentiel + "]";
	}

}
