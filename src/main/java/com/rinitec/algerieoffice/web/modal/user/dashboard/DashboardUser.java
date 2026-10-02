package com.rinitec.algerieoffice.web.modal.user.dashboard;

import java.io.Serializable;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class DashboardUser implements Serializable {
	private static final long serialVersionUID = -7743634654125237684L;
	
	private final Long countPopularity;
	private final Long[] countGlobe;
	private final Long[] countFavorite;
	private final Long[][] countAlert;
	
	public DashboardUser(final Long countPopularity, final Long[] countGlobe, final Long[] countFavorite, final Long[][] countAlert) {
		this.countPopularity = countPopularity;
		this.countGlobe = countGlobe;
		this.countFavorite = countFavorite;
		this.countAlert = countAlert;
	}
	
	public Long getCountPopularity() {
		return countPopularity;
	}
	
	public Long[] getCountGlobe() {
		return countGlobe;
	}
	
	public Long[] getCountFavorite() {
		return countFavorite;
	}
	
	public Long[][] getCountAlert() {
		return countAlert;
	}
	
	public String getFormattedPopularity() {
		return ParseUtil.getFormattedValue(countPopularity);
	}
	
	public String getFormattedGlobe(final int index) {
		return ParseUtil.getFormattedValue(countGlobe[index - 1]);
	}
	
	public int getPersentGlobeCompany() {
		final long sumGlobe = countGlobe[0] + countGlobe[1];
		return sumGlobe == 0L ? 0 : (int) ((countGlobe[0] * 100) / sumGlobe);
	}
	
	public String getFormattedFavorite(final int index) {
		return ParseUtil.getFormattedValue(countFavorite[index - 1]);
	}
	
	private final long sumfavorite() {
		long sumFavorite = 0L;
		for(int i = 0; i < 4; i++) {
			sumFavorite += countFavorite[i];
		}
		return sumFavorite;
	}
	
	public int getPersentFavorite(int index) {
		final long sumFavorite = this.sumfavorite();
		return sumFavorite == 0L ? 0 : (int) ((countFavorite[index - 1] * 100) / sumFavorite);
	}
	
	public String getFormattedAlert(final int index) {
		return ParseUtil.getFormattedValue(countAlert[index - 1][0]);
	}
	
	public long countAlertPotentiel(final int index) {
		return countAlert[index - 1][1] == null ? 0L : countAlert[index - 1][1];
	}

	@Override
	public String toString() {
		return "DashboardUser [countPopularity=" + countPopularity + ", countGlobe=" + countGlobe + ", countFavorite="
				+ countFavorite + ", countAlert=" + countAlert + "]";
	}

}
