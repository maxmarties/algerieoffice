package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AnalyticContact implements Serializable {
	private static final long serialVersionUID = -2730663821982033609L;
	
	private final Long[] counts;
	
	public AnalyticContact(final Long[] counts) {
		this.counts = counts;
	}
	
	public Long[] getCounts() {
		return counts;
	}
	
	public String countsToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < counts.length; i++) {
			builder.append(String.valueOf(counts[i]));
			if(i < counts.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getFormattedSum() {
		long sum = 0L;
		for (int i = 0; i < counts.length; i++) {
			sum += counts[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedCounts(int index) {
		return ParseUtil.getFormattedValue(counts[index - 1]);
	}

	@Override
	public String toString() {
		return "AnalyticContact [counts=" + counts + "]";
	}

}
