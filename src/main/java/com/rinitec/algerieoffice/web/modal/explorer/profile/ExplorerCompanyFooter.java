package com.rinitec.algerieoffice.web.modal.explorer.profile;

import java.io.Serializable;
import java.util.List;

import com.rinitec.algerieoffice.persistence.result.ActualityMini;

public class ExplorerCompanyFooter implements Serializable {
	private static final long serialVersionUID = -4735035494186419926L;
	
	private final String tageline;
	private final List<ActualityMini> actus;
	
	public ExplorerCompanyFooter(final String tageline, final List<ActualityMini> actus) {
		this.tageline = tageline;
		this.actus = actus;
	}

	public String getTageline() {
		return tageline;
	}

	public List<ActualityMini> getActus() {
		return actus;
	}

	@Override
	public String toString() {
		return "ExplorerCompanyFooter [tageline=" + tageline + ", actus=" + actus + "]";
	}

}
