package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmAnalyticLogin implements Serializable {
	private static final long serialVersionUID = -4037606618177876835L;
	
	private final Long countUser;
	private final Integer countLogin;
	private final Long[] logins;
	private final Long[] counts;
	
	public AdmAnalyticLogin(final Long countUser, final Integer countLogin, final Long[] logins, final Long[] counts) {
		this.countUser = countUser;
		this.countLogin  = countLogin;
		this.logins = logins;
		this.counts = counts;
	}

	public Long getCountUser() {
		return countUser;
	}

	public Integer getCountLogin() {
		return countLogin;
	}

	public Long[] getLogins() {
		return logins;
	}

	public Long[] getCounts() {
		return counts;
	}
	
	public String parseCount() {
		return ParseUtil.getFormattedCount(countUser);
	}
	
	public String parseLogin() {
		return ParseUtil.getFormattedOrder(countLogin);
	}
	
	public int getPersentLogin() {
		if(countUser == 0L) {
			return 0;
		}
		final int persent = (int) ((countLogin * 100) / countUser);
		return persent > 100 ? 100 : persent;
	}
	
	public String parseLogins(int index) {
		return ParseUtil.getFormattedCount(logins[index - 1]);
	}
	
	public String parseCounts(int index) {
		return ParseUtil.getFormattedCount(counts[index - 1]);
	}
	
	public String parseLocked() {
		long countAll = 0L;
		for (final Long count : counts) {
			countAll += count;
		}
		return countAll == countUser ? "0" : ParseUtil.getFormattedCount(countAll - countUser);
	}
	
	public int getPersentLogins(int index) {
		if(counts[index - 1] == 0L) {
			return 0;
		}
		final int persent = (int) ((logins[index - 1] * 100) / counts[index - 1]);
		return persent > 100 ? 100 : persent;
	}

	@Override
	public String toString() {
		return "AdmAnalyticLogin [countUser=" + countUser + ", countLogin=" + countLogin + ", logins="
				+ Arrays.toString(logins) + ", counts=" + Arrays.toString(counts) + "]";
	}

}
