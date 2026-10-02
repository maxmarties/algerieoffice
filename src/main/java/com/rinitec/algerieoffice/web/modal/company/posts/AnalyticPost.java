package com.rinitec.algerieoffice.web.modal.company.posts;

import java.io.Serializable;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AnalyticPost implements Serializable {
	private static final long serialVersionUID = 8733804228082575802L;
	
	private final Long[] countPost;
	private final Long[] countService;
	
	public AnalyticPost(final Long[] countPost, final Long[] countService) {
		this.countPost = countPost;
		this.countService = countService;
	}
	
	public Long[] getCountPost() {
		return countPost;
	}
	
	public Long[] getCountService() {
		return countService;
	}
	
	public String getFormattedTotal(int index) {
		switch(index) {
		case 1: return ParseUtil.getFormattedValue(countPost[0]);
		case 2: return ParseUtil.getFormattedValue(countService[0]);
		default: return ParseUtil.getFormattedValue(countPost[0] + countService[0]);
		}
	}
	
	public String getFormattedPublished(int index) {
		switch(index) {
		case 1: return ParseUtil.getFormattedValue(countPost[1]);
		case 2: return ParseUtil.getFormattedValue(countService[1]);
		default: return ParseUtil.getFormattedValue(countPost[1] + countService[1]);
		}
	}
	
	public String getFormattedTrashed(int index) {
		switch(index) {
		case 1: return ParseUtil.getFormattedValue(countPost[2]);
		case 2: return ParseUtil.getFormattedValue(countService[2]);
		default: return ParseUtil.getFormattedValue(countPost[2] + countService[2]);
		}
	}
	
	public String getFormattedCloud(int index) {
		switch(index) {
		case 1: return countPost[1] - countPost[2] <= 0 ? "0" : ParseUtil.getFormattedValue(countPost[1] - countPost[2]);
		case 2: return countService[1] - countService[2] <= 0 ? "0" :  ParseUtil.getFormattedValue(countService[1] - countService[2]);
		default: final long countCloud = (countPost[1] + countService[1]) - (countPost[2] + countService[2]);
			return countCloud <= 0 ? "0" : ParseUtil.getFormattedValue(countCloud);
		}
	}
	
	public String countCreatedToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countPost.length; i++) {
			builder.append(String.valueOf(countPost[i]));
			if(i < countPost.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countUpdatedToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countService.length; i++) {
			builder.append(String.valueOf(countService[i]));
			if(i < countService.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public int getPesrsentInsert(int index) {
		final long sum = countPost[0] + countService[0];
		switch(index) {
		case 1: return sum > 0 ? (int) ((countPost[0] * 100) / sum) : 0;
		default: return sum > 0 ? (int) ((countService[0] * 100) / sum) : 0;
		}
	}
	
	public int getPesrsentPublished(int index) {
		final long all = (countPost[1] + countService[1]) - (countPost[2] + countService[2]);
		if(all <= 0) return 0; 
		switch(index) {
		case 1: return countPost[1] - countPost[2] <= 0 || all <= 0 ? 0 : (int) (((countPost[1] - countPost[2]) * 100) / all);
		default: return countService[1] - countService[2] <= 0 || all <= 0 ? 0 : (int) (((countService[1] - countService[2]) * 100) / all);
		}
	}

	@Override
	public String toString() {
		return "AnalyticPost [countPost=" + countPost + ", countService=" + countService + "]";
	}

}
