package com.rinitec.algerieoffice.web.modal.user.dashboard;

import java.io.Serializable;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class DashboardAnalytic implements Serializable {
	private static final long serialVersionUID = -3189765824549167123L;
	
	private final Long[] countAccess;
	private final Long[] countFavorites;
	private final Long[] countHistories;
	
	public DashboardAnalytic(final Long[] countAccess, final Long[] countFavorites, final Long[] countHistories) {
		this.countAccess = countAccess;
		this.countFavorites = countFavorites;
		this.countHistories = countHistories;
	}

	public Long[] getCountAccess() {
		return countAccess;
	}

	public Long[] getCountFavorites() {
		return countFavorites;
	}

	public Long[] getCountHistories() {
		return countHistories;
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
	
	public String countFavoritesToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countFavorites.length; i++) {
			builder.append(String.valueOf(countFavorites[i]));
			if(i < countFavorites.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countHistoriesToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countHistories.length; i++) {
			builder.append(String.valueOf(countHistories[i]));
			if(i < countHistories.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getFormattedSumAccess() {
		long sum = 0L;
		for (int i = 0; i < countAccess.length; i++) {
			sum += countAccess[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedSumFavorites() {
		long sum = 0L;
		for (int i = 0; i < countFavorites.length; i++) {
			sum += countFavorites[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedSumHistories() {
		long sum = 0L;
		for (int i = 0; i < countHistories.length; i++) {
			sum += countHistories[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}

	@Override
	public String toString() {
		return "DashboardAnalytic [countAccess=" + countAccess + ", countFavorites=" + countFavorites
				+ ", countHistories=" + countHistories + "]";
	}

}
