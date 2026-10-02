package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmAnalyticCompany implements Serializable {
	private static final long serialVersionUID = 4488456695526635352L;
	
	private final Long countAll;
	private final Long[] countAttribut;
	private final Long[] countType;
	
	public AdmAnalyticCompany(final Long countAll, final Long[] countAttribut, final Long[] countType) {
		this.countAll = countAll;
		this.countAttribut = countAttribut;
		this.countType = countType;
	}

	public Long getCountAll() {
		return countAll;
	}

	public Long[] getCountAttribut() {
		return countAttribut;
	}

	public Long[] getCountType() {
		return countType;
	}
	
	public String getFormattedCountAll() {
		return ParseUtil.getFormattedValue(countAll);
	}
	
	public int getPersentAttribut(int index) {
		if(countAll == 0L) {
			return 0;
		}
		final int persent = (int) ((countAttribut[index - 1] * 100) / countAll);
		return persent > 100 ? 100 : persent;
	}
	
	public String getFormattedAttribut(int index) {
		return ParseUtil.getFormattedValue(countAttribut[index - 1]);
	}
	
	public String getFormattedType(int index) {
		return ParseUtil.getFormattedValue(countType[index - 1]);
	}
	
	public int getPersentType(int index) {
		if(countAll == 0L) {
			return 0;
		}
		final int persent = (int) ((countType[index - 1] * 100) / countAll);
		return persent > 100 ? 100 : persent;
	}

	@Override
	public String toString() {
		return "AdmAnalyticCompany [countAll=" + countAll + ", countAttribut=" + Arrays.toString(countAttribut)
				+ ", countType=" + Arrays.toString(countType) + "]";
	}
	
}
