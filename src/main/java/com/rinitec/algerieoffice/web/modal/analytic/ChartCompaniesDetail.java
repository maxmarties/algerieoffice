package com.rinitec.algerieoffice.web.modal.analytic;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class ChartCompaniesDetail implements Serializable {
	private static final long serialVersionUID = -6735224208937868924L;
	
	private final Long[] countContact;
	private final Long[] countDigital;
	private final Long[] countSocial;
	private final Long[] countBuild;
	
	public ChartCompaniesDetail(final Long[] countContact, final Long[] countDigital, final Long[] countSocial, final Long[] countBuild) {
		this.countContact = countContact;
		this.countDigital = countDigital;
		this.countSocial = countSocial;
		this.countBuild = countBuild;
	}

	public Long[] getCountContact() {
		return countContact;
	}
	
	public Long[] getCountDigital() {
		return countDigital;
	}

	public Long[] getCountSocial() {
		return countSocial;
	}

	public Long[] getCountBuild() {
		return countBuild;
	}
	
	public boolean hasPresentContact()  {
		for (int i = 0; i < countContact.length; i++) {
			if(countContact[i] > 0) {
				return true;
			}
		}
		return false;
	}
	
	public String countContactToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countContact.length; i++) {
			builder.append(String.valueOf(countContact[i]));
			if(i < countContact.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public boolean hasPresentDigital()  {
		for (int i = 0; i < countDigital.length; i++) {
			if(countDigital[i] > 0) {
				return true;
			}
		}
		return false;
	}
	
	public String getFormattedDigital(int index) {
		return ParseUtil.getFormattedValue(countDigital[index]);
	}
	
	public boolean hasPresentSocial()  {
		for (int i = 0; i < countSocial.length; i++) {
			if(countSocial[i] > 0) {
				return true;
			}
		}
		return false;
	}
	
	public String countSocialToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countSocial.length; i++) {
			builder.append(String.valueOf(countSocial[i]));
			if(i < countSocial.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countBuildToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countBuild.length; i++) {
			builder.append(String.valueOf(countBuild[i]));
			if(i < countBuild.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getYearsBuild() {
		return ParseUtil.getLastYearsToString(10);
	}

	@Override
	public String toString() {
		return "ChartCompaniesDetail [countContact=" + Arrays.toString(countContact) + ", countDigital="
				+ Arrays.toString(countDigital) + ", countSocial=" + Arrays.toString(countSocial) + ", countBuild="
				+ Arrays.toString(countBuild) + "]";
	}

}
