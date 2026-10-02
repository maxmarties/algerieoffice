package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmAnalyticPremium implements Serializable {
	private static final long serialVersionUID = -6463455995035040950L;
	
	private final Long countActive;
	private final Long[] countPass;
	private final Long[] countCreate;
	private final Long[] countExpire;
	
	public AdmAnalyticPremium(final Long countActive, final Long[] countPass, final Long[] countCreate, final Long[] countExpire) {
		this.countActive = countActive;
		this.countPass = countPass;
		this.countCreate = countCreate;
		this.countExpire = countExpire;
	}

	public Long getCountActive() {
		return countActive;
	}

	public Long[] getCountPass() {
		return countPass;
	}

	public Long[] getCountCreate() {
		return countCreate;
	}

	public Long[] getCountExpire() {
		return countExpire;
	}
	
	public String countCreateToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countCreate.length; i++) {
			builder.append(String.valueOf(countCreate[i]));
			if(i < countCreate.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countExpireToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countExpire.length; i++) {
			builder.append(String.valueOf(countExpire[i]));
			if(i < countExpire.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getFormattedSumCreate() {
		long sum = 0L;
		for (int i = 0; i < countCreate.length; i++) {
			sum += countCreate[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedSumExpire() {
		long sum = 0L;
		for (int i = 0; i < countExpire.length; i++) {
			sum += countExpire[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String parseCountActive() {
		return ParseUtil.getFormattedValue(countActive);
	}
	
	public String parseCountPass(int index) {
		return ParseUtil.getFormattedValue(countPass[index - 1]);
	}
	
	public int getPersentPass(int index) {
		if(countActive == 0L) {
			return 0;
		}
		final int persent = (int) ((countPass[index - 1] * 100) / countActive);
		return persent > 100 ? 100 : persent;
	}

	@Override
	public String toString() {
		return "AdmAnalyticPremium [countActive=" + countActive + ", countPass=" + Arrays.toString(countPass)
				+ ", countCreate=" + Arrays.toString(countCreate) + ", countExpire=" + Arrays.toString(countExpire) + "]";
	}

}
