package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.analytic.FollowCompany;
import com.rinitec.algerieoffice.persistence.modal.analytic.Outlook;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySearch;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class AnalyticSearch implements Serializable {
	private static final long serialVersionUID = -8922276665259554474L;
	
	private final Long numberOfVisits;
	private final Long[] countSearch = new Long[5];
	private final Long[] countClick = new Long[3];
	private final Long[] countFollow = new Long[5];
	private final Long[] countUpwork = new Long[3];
	
	public AnalyticSearch(final Long numberOfVisits, final CompanySearch companySearch, final Outlook outlook, final FollowCompany followCompany, 
			final Long countNotice, final Long countAppoint, final Long countCollaborator) {
		this.numberOfVisits = numberOfVisits;
		this.countSearch[0] = companySearch.getView();
		this.countSearch[1] = companySearch.getToken();
		this.countSearch[2] = companySearch.getFilter();
		this.countSearch[3] = companySearch.getTag();
		this.countSearch[4] = companySearch.getSimultude();
		if(outlook != null) {
			this.countClick[0] = outlook.getGetPhone();
			this.countClick[1] = outlook.getAppPhone();
			this.countClick[2] = outlook.getSendMail();
		} else {
			for(int i = 0; i < 3; i++) {
				this.countClick[i] = 0L;
			}
		}
		if(followCompany != null) {
			this.countFollow[0] = followCompany.getFacebook();
			this.countFollow[1] = followCompany.getTwitter();
			this.countFollow[2] = followCompany.getGoogle();
			this.countFollow[3] = followCompany.getLinkedin();
			this.countFollow[4] = followCompany.getViadeo();
		} else {
			for(int i = 0; i < 5; i++) {
				this.countFollow[i] = 0L;
			}
		}
		this.countUpwork[0] = countNotice;
		this.countUpwork[1] = countAppoint;
		this.countUpwork[2] = countCollaborator;
	}

	public Long getNumberOfVisits() {
		return numberOfVisits;
	}

	public Long[] getCountSearch() {
		return countSearch;
	}
	
	public Long[] getCountClick() {
		return countClick;
	}
	
	public Long[] getCountFollow() {
		return countFollow;
	}
	
	public Long[] getCountUpwork() {
		return countUpwork;
	}
	
	public String getFormattedSumSearch() {
		long sum = 0L;
		for(int i = 0; i < 5; i++) {
			sum += countSearch[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String countSearchToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countSearch.length; i++) {
			builder.append(String.valueOf(countSearch[i]));
			if(i < countSearch.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getFormattedVisite() {
		return ParseUtil.getFormattedValue(numberOfVisits);
	}
	
	public String getFormattedValue(int index) {
		return ParseUtil.getFormattedValue(countSearch[index]);
	}
	
	public int parsePersentView() {
		final int persent = numberOfVisits > 0 ? (int) ((countSearch[0] * 100) / numberOfVisits) : 0;
		return persent > 100 ? 100 : persent;
	}
	
	public int parsePersentVisit() {
		final int persent = countSearch[0] > 0 ? (int) ((numberOfVisits * 100) / countSearch[0]) : 0;
		return persent > 100 ? 100 : persent;
	}
	
	public int parsePersentSearch(int index) {
		final int persent = countSearch[0] > 0 ? (int) ((countSearch[index] * 100) / countSearch[0]) : 0;
		return persent > 100 ? 100 : persent;
	}
	
	public String getReferingValue(int index) {
		if(index == 6) return getFormattedVisite();
		else return getFormattedValue(index - 1);
	}
	
	public int getReferingPersent(int index) {
		if(index == 1) return parsePersentView();
		else if(index == 6) return parsePersentVisit();
		else return parsePersentSearch(index - 1);
	}
	
	public long getSumClick() {
		long sum = 0L;
		for(int i = 0; i < 3; i++) {
			sum += countClick[i];
		}
		return sum;
	}
	
	public String getFormattedClick(int index) {
		return ParseUtil.getFormattedValue(countClick[index - 1]);
	}
	
	public int parsePersentClick(int index) {
		final long sum = getSumClick();
		return sum > 0 ? (int) ((countClick[index - 1] * 100) / sum) : 0;
	}
	
	public String getFormattedFollow(int index) {
		return ParseUtil.getFormattedValue(countFollow[index - 1]);
	}
	
	public String getFormattedSumFollow() {
		long sum = 0L;
		for(int i = 0; i < 5; i++) {
			sum += countFollow[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedUpwork(int index) {
		return ParseUtil.getFormattedValue(countUpwork[index - 1]);
	}

	@Override
	public String toString() {
		return "AnalyticSearch [numberOfVisits=" + numberOfVisits + ", countSearch=" + countSearch + ", countClick="
				+ countClick + ", countFollow=" + countFollow + ", countUpwork=" + countUpwork + "]";
	}

}
