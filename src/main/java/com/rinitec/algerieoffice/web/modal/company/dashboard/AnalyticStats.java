package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AnalyticStats implements Serializable {
	private static final long serialVersionUID = 639493581594904657L;
	
	private final Long[] countMonth;
	private final Long[] countStats;
	
	public AnalyticStats(final Long[] countMonth, final Long[] countStats) {
		this.countMonth = countMonth;
		this.countStats = countStats;
	}
	
	public Long[] getCountMonth() {
		return countMonth;
	}
	
	public Long[] getCountStats() {
		return countStats;
	}
	
	public String countMonthToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countMonth.length; i++) {
			builder.append(String.valueOf(countMonth[i]));
			if(i < countMonth.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getFormattedStats(int index) {
		return ParseUtil.getFormattedValue(countStats[index - 1]);
	}
	
	public int getPersentStats(int index) {
		final long sum = getSumStats();
		return sum > 0 ? (int) ((countStats[index - 1] * 100) / sum) : 0;
	}
	
	public long getSumStats() {
		long sum = 0L;
		for (int i = 0; i < countStats.length; i++) {
			sum += countStats[i];
		}
		return sum;
	} 
	
	public String getFormattedSumStats() {
		return ParseUtil.getFormattedValue(getSumStats());
	}
	
	public int getPersentStatsPost() {
		return countStats[1] > 0 ? (int) ((countStats[0] * 100) / countStats[1]) : 0;
	}

	@Override
	public String toString() {
		return "AnalyticStats [countMonth=" + countMonth + ", countStats=" + countStats + "]";
	}

}
