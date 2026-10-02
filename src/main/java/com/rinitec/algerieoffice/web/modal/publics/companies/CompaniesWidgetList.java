package com.rinitec.algerieoffice.web.modal.publics.companies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompaniesWidgetList implements Serializable {
	private static final long serialVersionUID = -4380737578228855419L;
	
	private final long countResult;
	private final List<?> lines;
	
	public CompaniesWidgetList(final long countResult, final List<?> lines) {
		this.countResult = countResult;
		this.lines = lines;
	}

	public long getCountResult() {
		return countResult;
	}

	public List<?> getLines() {
		return lines;
	}
	
	public List<Long> getCompaniesId() {
		final List<Long> companiesId = new ArrayList<Long>();
		for (final Object line : lines) {
			companiesId.add(((CompanyWidgetMini) line).getId());
		}
		return companiesId;
	}
	
	public List<Long> getCompaniesB2CId() {
		final List<Long> companiesId = new ArrayList<Long>();
		for (final Object line : lines) {
			companiesId.add(((CompanyWidgetB2C) line).getId());
		}
		return companiesId;
	}
	
	public boolean isEmpty() {
		return lines.isEmpty();
	}

	@Override
	public String toString() {
		return "CompaniesWidgetList [countResult=" + countResult + ", lines=" + lines + "]";
	}

}
