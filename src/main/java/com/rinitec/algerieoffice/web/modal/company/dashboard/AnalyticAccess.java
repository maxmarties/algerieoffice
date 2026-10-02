package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

public class AnalyticAccess implements Serializable {
	private static final long serialVersionUID = -7165649888757973816L;
	
	private final Long[] countAccess;
	private final Long[] countAttributs;
	
	public AnalyticAccess(final Long[] countAccess, final Long[] countAttributs) {
		this.countAccess = countAccess;
		this.countAttributs = countAttributs;
	}
	
	private final long countSumAccess() {
		long sum = 0L;
		for (int i = 0; i < countAccess.length; i++) {
			sum += countAccess[i];
		}
		return sum;
	}

	public Long[] getCountAccess() {
		return countAccess;
	}

	public Long[] getCountAttributs() {
		return countAttributs;
	}
	
	public boolean hasPresent() {
		return this.countSumAccess() > 0L;
	}
	
	public String countAccessToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countAccess.length; i++) {
			builder.append(String.valueOf(countAccess[i]));
			if(i < countAccess.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public int getPesrsentAttribut(int index) {
		final long sum = this.countSumAccess();
		if(sum == 0L) {
			return 0;
		}
		final int persent = (int) ((countAttributs[index - 1] * 100) / sum);
		return persent > 100 ? 100 : persent;
	}

	@Override
	public String toString() {
		return "AnalyticAccess [countAccess=" + countAccess + ", countAttributs=" + countAttributs + "]";
	}
	
}
