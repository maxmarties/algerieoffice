package com.rinitec.algerieoffice.web.modal.analytic;

import java.io.Serializable;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class OfficeSkills implements Serializable {
	private static final long serialVersionUID = -3272976480431114645L;
	
	private final Long[] count;
	
	public OfficeSkills(final Long[] count) {
		this.count = count;
	}
	
	public Long[] getCount() {
		return count;
	}
	
	public String getFormattedValue(int index) {
		return count[index - 1] == 0L ? "0" : ParseUtil.getFormattedValue(count[index - 1]);
	}

	@Override
	public String toString() {
		return "OfficeSkills [count=" + count + "]";
	}

}
