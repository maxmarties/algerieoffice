package com.rinitec.algerieoffice.web.modal.publics.members;

import java.io.Serializable;
import java.util.List;

public class MembersWidgetList implements Serializable {
	private static final long serialVersionUID = -1701703594150354014L;
	
	private final long countResult;
	private final List<MemberWidgetMini> lines;
	
	public MembersWidgetList(final long countResult, final List<MemberWidgetMini> lines) {
		this.countResult = countResult;
		this.lines = lines;
	}

	public long getCountResult() {
		return countResult;
	}

	public List<MemberWidgetMini> getLines() {
		return lines;
	}

	@Override
	public String toString() {
		return "MembersWidgetList [countResult=" + countResult + ", lines=" + lines + "]";
	}

}
