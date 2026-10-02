package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmAnalyticAccess implements Serializable {
	private static final long serialVersionUID = 6134045550346540973L;

	private final Long[] countPro;
	private final Long[] countIndividualy;
	
	public AdmAnalyticAccess(final Long[] countPro, final Long[] countIndividualy) {
		this.countPro = countPro;
		this.countIndividualy = countIndividualy;
	}
	
	public Long[] getCountPro() {
		return countPro;
	}
	
	public Long[] getCountIndividualy() {
		return countIndividualy;
	}
	
	public String countProToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countPro.length; i++) {
			builder.append(String.valueOf(countPro[i]));
			if(i < countPro.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countIndividualyToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countIndividualy.length; i++) {
			builder.append(String.valueOf(countIndividualy[i]));
			if(i < countIndividualy.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getFormattedSumPro() {
		long sum = 0L;
		for (int i = 0; i < countPro.length; i++) {
			sum += countPro[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedSumIndividualy() {
		long sum = 0L;
		for (int i = 0; i < countIndividualy.length; i++) {
			sum += countIndividualy[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}

	@Override
	public String toString() {
		return "AdmAnalyticAccess [countPro=" + Arrays.toString(countPro) + ", countIndividualy="
				+ Arrays.toString(countIndividualy) + "]";
	}
	
}
