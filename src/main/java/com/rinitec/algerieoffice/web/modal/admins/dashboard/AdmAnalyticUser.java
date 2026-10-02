package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmAnalyticUser implements Serializable {
	private static final long serialVersionUID = -3036836589495143803L;
	
	private final Long countAll;
	private final Long[] countPro;
	private final Long[] countSexe;
	private final Long[] countAgent;
	private final Long countPingled;
	
	public AdmAnalyticUser(final Long countAll, final Long[] countPro, final Long[] countSexe, final Long[] countAgent, final Long countPingled) {
		this.countAll = countAll;
		this.countPro = countPro;
		this.countSexe = countSexe;
		this.countAgent = countAgent;
		this.countPingled = countPingled;
	}

	public Long getCountAll() {
		return countAll;
	}

	public Long[] getCountPro() {
		return countPro;
	}

	public Long[] getCountSexe() {
		return countSexe;
	}
	
	public Long[] getCountAgent() {
		return countAgent;
	}
	
	public Long getCountPingled() {
		return countPingled;
	}
	
	public String getFormattedCountAll() {
		return ParseUtil.getFormattedValue(countAll);
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
	
	private long sumSexe() {
		long sum = 0L;
		for (final Long sexe : countSexe) {
			sum += sexe;
		}
		return sum;
	}
	
	public String getFormattedCountSexe() {
		return ParseUtil.getFormattedValue(this.sumSexe());
	}
	
	public int getPersentSexe(int index) {
		final long sumSexe = this.sumSexe();
		if(sumSexe == 0L) {
			return 0;
		}
		final int persent = (int) ((countSexe[index - 1] * 100) / sumSexe);
		return persent > 100 ? 100 : persent;
	}
	
	public String getFormattedSexe(int index) {
		return ParseUtil.getFormattedValue(countSexe[index - 1]);
	}
	
	public int getPersentProfile() {
		if(countAll == 0L) {
			return 0;
		}
		final int persent = (int) ((this.sumSexe() * 100) / countAll);
		return persent > 100 ? 100 : persent;
	}
	
	private long sumAgent() {
		long sum = 0L;
		for (final Long agent : countAgent) {
			sum += agent;
		}
		return sum;
	}
	
	public String getFormattedCountAgent() {
		return ParseUtil.getFormattedValue(this.sumAgent());
	}
	
	public String countAgentToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countAgent.length; i++) {
			builder.append(String.valueOf(countAgent[i]));
			if(i < countAgent.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public int getPersentPingled() {
		final long agent = this.sumAgent();
		if(agent == 0L) {
			return 0;
		}
		final int persent = (int) ((countPingled * 100) / agent);
		return persent > 100 ? 100 : persent;
	}
	
	public String getFormattedCountPingled() {
		return ParseUtil.getFormattedValue(countPingled);
	}

	@Override
	public String toString() {
		return "AdmAnalyticUser [countAll=" + countAll + ", countPro=" + Arrays.toString(countPro) + ", countSexe="
				+ Arrays.toString(countSexe) + ", countAgent=" + Arrays.toString(countAgent) + ", countPingled="
				+ countPingled + "]";
	}
	
}
