package com.rinitec.algerieoffice.web.modal.publics.companies;

import java.io.Serializable;
import java.util.Collection;

public class CompanySimultudesList implements Serializable {
	private static final long serialVersionUID = -6150564635219311508L;
	
	private final Integer wilaya;
	private final long countResult;
	private final Collection<CompanySimultudeLine> lines;
	
	public CompanySimultudesList(final Integer wilaya, final long countResult, final Collection<CompanySimultudeLine> lines) {
		this.wilaya = wilaya;
		this.countResult = countResult;
		this.lines = lines;
	}
	
	public Integer getWilaya() {
		return wilaya;
	}

	public long getCountResult() {
		return countResult;
	}

	public Collection<CompanySimultudeLine> getLines() {
		return lines;
	}

	@Override
	public String toString() {
		return "CompanySimultudesList [wilaya=" + wilaya + ", countResult=" + countResult + ", lines=" + lines + "]";
	}

}
