package com.rinitec.algerieoffice.web.modal.analytic;

import java.io.Serializable;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class ChartCompaniesCapital implements Serializable {
	private static final long serialVersionUID = -3773523039145613173L;
	
	private final Long sumCapital;
	private final Double avgCapital;
	
	public ChartCompaniesCapital(final Long sumCapital, final Double avgCapital) {
		this.sumCapital = sumCapital;
		this.avgCapital = avgCapital;
	}

	public Long getSumCapital() {
		return sumCapital;
	}

	public Double getAvgCapital() {
		return avgCapital;
	}
	
	public String getFormattedAvgCapital() {
		return ParseUtil.getFormattedValue(avgCapital);
	}
	
	public String getFormattedSumCapital() {
		return ParseUtil.getFormattedValue(sumCapital);
	}
	
	public boolean hasPresent() {
		return avgCapital != null && avgCapital > 0;
	}

	@Override
	public String toString() {
		return "ChartCompaniesCapital [sumCapital=" + sumCapital + ", avgCapital=" + avgCapital + "]";
	}

}
