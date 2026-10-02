package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmAnalyticVisit implements Serializable {
	private static final long serialVersionUID = -6152721836657275955L;
	
	private final Long[] countAuthentified;
	private final Long[] countAnonyme;
	private final Long[] countWeb;
	
	public AdmAnalyticVisit(final Long[] countAuthentified, final Long[] countAnonyme, final Long[] countWeb) {
		this.countAuthentified = countAuthentified;
		this.countAnonyme = countAnonyme;
		this.countWeb = countWeb;
	}

	public Long[] getCountAuthentified() {
		return countAuthentified;
	}

	public Long[] getCountAnonyme() {
		return countAnonyme;
	}

	public Long[] getCountWeb() {
		return countWeb;
	}
	
	public String countAuthentifiedToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countAuthentified.length; i++) {
			builder.append(String.valueOf(countAuthentified[i]));
			if(i < countAuthentified.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countAnonymeToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countAnonyme.length; i++) {
			builder.append(String.valueOf(countAnonyme[i]));
			if(i < countAnonyme.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	private long sumAuthentified() {
		long sum = 0L;
		for (int i = 0; i < countAuthentified.length; i++) {
			sum += countAuthentified[i];
		}
		return sum;
	}
	
	public String getFormattedSumAuthtified() {
		return ParseUtil.getFormattedValue(this.sumAuthentified());
	}
	
	private long sumAnonyme() {
		long sum = 0L;
		for (int i = 0; i < countAnonyme.length; i++) {
			sum += countAnonyme[i];
		}
		return sum;
	}
	
	public String getFormattedSumAnonyme() {
		return ParseUtil.getFormattedValue(this.sumAnonyme());
	}
	
	public long sumVisit() {
		long sum = 0L;
		for (int i = 0; i < countWeb.length; i++) {
			sum += countWeb[i];
		}
		return sum;
	}
	
	public String parseCountVisit() {
		return ParseUtil.getFormattedCount(this.sumVisit());
	}
	
	public String parseCountVisit(int index) {
		switch(index) {
		case 1: return ParseUtil.getFormattedValue(this.sumAuthentified());
		case 2: return ParseUtil.getFormattedValue(this.sumAnonyme());
		case 3: case 4: return ParseUtil.getFormattedValue(countWeb[index - 3]);
		}
		return "0";
	}
	
	public int getPersentVisit(int index) {
		final long sum = this.sumVisit();
		if(sum == 0L) {
			return 0;
		}
		int persent = 0;
		switch(index) {
		case 1: persent = (int) ((this.sumAuthentified() * 100) / sum); break;
		case 2: persent = (int) ((this.sumAnonyme() * 100) / sum); break;
		case 3: case 4: persent = (int) ((countWeb[index - 3] * 100) / sum);
		}
		return persent > 100 ? 100 : persent;
	}

	@Override
	public String toString() {
		return "AdmAnalyticVisit [countAuthentified=" + Arrays.toString(countAuthentified) + ", countAnonyme="
				+ Arrays.toString(countAnonyme) + ", countWeb=" + Arrays.toString(countWeb) + "]";
	}

}
