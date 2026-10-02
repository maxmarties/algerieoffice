package com.rinitec.algerieoffice.web.modal.admins;

import java.io.Serializable;
import java.util.Collection;

public class ElementsList implements Serializable {
	private static final long serialVersionUID = 3019727863466825900L;
	
	private final long countResult;
	private final Collection<?> lines;
	
	public ElementsList(final long countResult, final Collection<?> lines) {
		this.countResult = countResult;
		this.lines = lines;
	}
	
	public long getCountResult() {
		return countResult;
	}
	
	public Collection<?> getLines() {
		return lines;
	}

	@Override
	public String toString() {
		return "ElementsList [countResult=" + countResult + ", lines=" + lines + "]";
	}

}
