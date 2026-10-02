package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AnalyticActivity implements Serializable {
	private static final long serialVersionUID = -355048063866072931L;
	
	private final Long[] countLogins;
	private final Long[] countActivities;
	
	public AnalyticActivity(final Long[] countLogins, final Long[] countActivities) {
		this.countLogins = countLogins;
		this.countActivities = countActivities;
	}
	
	public Long[] getCountLogins() {
		return countLogins;
	}
	
	public Long[] getCountActivities() {
		return countActivities;
	}
	
	public String countLoginsToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countLogins.length; i++) {
			builder.append(String.valueOf(countLogins[i]));
			if(i < countLogins.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countActivitiesToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countActivities.length; i++) {
			builder.append(String.valueOf(countActivities[i]));
			if(i < countActivities.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getFormattedSumLogins() {
		long sum = 0L;
		for (int i = 0; i < countLogins.length; i++) {
			sum += countLogins[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedSumActivities() {
		long sum = 0L;
		for (int i = 0; i < countActivities.length; i++) {
			sum += countActivities[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}

	@Override
	public String toString() {
		return "AnalyticActivity [countLogins=" + countLogins + ", countActivities=" + countActivities + "]";
	}

}
