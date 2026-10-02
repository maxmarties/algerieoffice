package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

public class AnalyticFavorite implements Serializable {
	private static final long serialVersionUID = -7674211096416153103L;
	
	private final Long countAlert;
	private final Long[] countFavorites;
	
	public AnalyticFavorite(final Long countAlert, final Long[] countFavorites) {
		this.countAlert = countAlert;
		this.countFavorites = countFavorites;
	}
	
	public Long getCountAlert() {
		return countAlert;
	}
	
	public Long[] getCountFavorites() {
		return countFavorites;
	}
	
	public boolean hasPresent()  {
		for (int i = 0; i < countFavorites.length; i++) {
			if(countFavorites[i] != null && countFavorites[i] > 0L) {
				return true;
			}
		}
		return false;
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
	
	public long getSumCountFavorites() {
		long sum = 0L;
		for (int i = 0; i < countFavorites.length; i++) {
			sum += countFavorites[i];
		}
		return sum;
	}
	
	public int getPesrsentAlert() {
		final long sum = getSumCountFavorites();
		return (int) ((countAlert * 100) / sum);
	}

	@Override
	public String toString() {
		return "AnalyticFavorite [countAlert=" + countAlert + ", countFavorites=" + countFavorites + "]";
	}

}
