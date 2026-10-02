package com.rinitec.algerieoffice.web.modal.analytic;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class ChartCompaniesType implements Serializable {
	private static final long serialVersionUID = 8311475511577291259L;
	
	private final Long[] countTypes;
	
	public ChartCompaniesType(final Long[] countTypes) {
		this.countTypes = countTypes;
	}

	public Long[] getCountTypes() {
		return countTypes;
	}
	
	public boolean hasPresent()  {
		for (int i = 0; i < countTypes.length; i++) {
			if(countTypes[i] != null && countTypes[i] > 0L) {
				return true;
			}
		}
		return false;
	}
	
	public String countTypesToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countTypes.length; i++) {
			builder.append(String.valueOf(countTypes[i]));
			if(i < countTypes.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getFormattedValue(int index) {
		return ParseUtil.getFormattedValue(countTypes[index]);
	}
	
	public String getSumFormattedValue() {
		long sum = 0;
		for (int i = 0; i < countTypes.length; i++) {
			sum += countTypes[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}

	@Override
	public String toString() {
		return "ChartCompaniesType [countTypes=" + Arrays.toString(countTypes) + "]";
	}

}
