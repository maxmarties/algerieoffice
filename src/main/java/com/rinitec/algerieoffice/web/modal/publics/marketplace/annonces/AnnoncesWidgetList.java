package com.rinitec.algerieoffice.web.modal.publics.marketplace.annonces;

import java.io.Serializable;
import java.util.List;

public class AnnoncesWidgetList implements Serializable {
	private static final long serialVersionUID = -4380737578228855419L;
	
	private final long countResult;
	private final List<AnnonceWidgetMini> lines;
	
	public AnnoncesWidgetList(final long countResult, final List<AnnonceWidgetMini> lines) {
		this.countResult = countResult;
		this.lines = lines;
	}

	public long getCountResult() {
		return countResult;
	}

	public List<AnnonceWidgetMini> getLines() {
		return lines;
	}
	
	public boolean isEmpty() {
		return lines.isEmpty();
	}

	@Override
	public String toString() {
		return "AnnoncesWidgetList [countResult=" + countResult + ", lines=" + lines + "]";
	}

}
