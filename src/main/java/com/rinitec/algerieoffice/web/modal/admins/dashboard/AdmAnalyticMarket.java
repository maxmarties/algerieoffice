package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmAnalyticMarket implements Serializable {
	private static final long serialVersionUID = -5137003504202771259L;
	
	private final Long[] countAll;
	private final Long[] countPublished;
	
	public AdmAnalyticMarket(final Long[] countAll, final Long[] countPublished) {
		this.countAll = countAll;
		this.countPublished = countPublished;
	}
	
	public Long[] getCountAll() {
		return countAll;
	}
	
	public Long[] getCountPublished() {
		return countPublished;
	}
	
	public String countAllToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countAll.length; i++) {
			builder.append(String.valueOf(countAll[i]));
			if(i < countAll.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countPublishedToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countPublished.length; i++) {
			builder.append(String.valueOf(countPublished[i]));
			if(i < countPublished.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	private long sumAll() {
		long sum = 0L;
		for (int i = 0; i < countAll.length; i++) {
			sum += countAll[i];
		}
		return sum;
	}
	
	public String getFormattedSumAll() {
		return ParseUtil.getFormattedValue(this.sumAll());
	}
	
	private long sumPublished() {
		long sum = 0L;
		for (int i = 0; i < countPublished.length; i++) {
			sum += countPublished[i];
		}
		return sum;
	}
	
	public String getFormattedSumPublished() {
		return ParseUtil.getFormattedValue(this.sumPublished());
	}
	
	public int getPersentMarket(int index) {
		if(countAll[index - 1] == 0L) {
			return 0;
		}
		final int persent = (int) ((countPublished[index - 1] * 100) / countAll[index - 1]);
		return persent > 100 ? 100 : persent;
	}
	
	public int getPersentAllMarket() {
		if(this.sumAll() == 0L) {
			return 0;
		}
		final int persent = (int) ((this.sumPublished() * 100) / this.sumAll());
		return persent > 100 ? 100 : persent;
	}

	@Override
	public String toString() {
		return "AdmAnalyticMarket [countAll=" + Arrays.toString(countAll) + ", countPublished="
				+ Arrays.toString(countPublished) + "]";
	}

}
