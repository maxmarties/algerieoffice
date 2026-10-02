package com.rinitec.algerieoffice.web.modal.publics.marketplace.employes;

import java.io.Serializable;
import java.util.List;

public class EmployesWidgetList implements Serializable {
	private static final long serialVersionUID = 5131753187565207812L;
	
	private final long countResult;
	private final List<EmployeWidgetMini> lines;
	
	public EmployesWidgetList(final long countResult, final List<EmployeWidgetMini> lines) {
		this.countResult = countResult;
		this.lines = lines;
	}

	public long getCountResult() {
		return countResult;
	}

	public List<EmployeWidgetMini> getLines() {
		return lines;
	}
	
	public boolean isEmpty() {
		return lines.isEmpty();
	}

	@Override
	public String toString() {
		return "EmployesWidgetList [countResult=" + countResult + ", lines=" + lines + "]";
	}

}
