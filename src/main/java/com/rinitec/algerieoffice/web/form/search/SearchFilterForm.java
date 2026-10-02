package com.rinitec.algerieoffice.web.form.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class SearchFilterForm implements Serializable {
	private static final long serialVersionUID = -6783251365884906861L;
	
	protected Long userId;
	private String token;
	private String keysword;
	private int row;
	private int sort;
	protected int page;
	private boolean desc;
	private boolean[] wilayas = new boolean[ConstraintesForm.COUNT_WILAYA];
	private String easylist;
	
	public SearchFilterForm(final Long userId) {
		this.userId = userId;
		this.sort = this.page = 1;
		this.desc = true;
	}
	
	public SearchFilterForm(final Long userId, final String token, final String keyword, final Integer wilaya, final int row) {
		this(userId);
		if(!StringUtils.isEmpty(token)) {
			this.token = token.replaceAll("\\+", " ");
		}
		if(!StringUtils.isEmpty(keyword)) {
			this.keysword = keyword.replaceAll("\\+", " ");
		}
		if(wilaya != null && wilaya != 0) {
			this.wilayas[wilaya - 1] = true;
		}
		this.row = row;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKeysword() {
		return keysword;
	}

	public void setKeysword(String keysword) {
		this.keysword = keysword;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	public boolean[] getWilayas() {
		return wilayas;
	}

	public void setWilayas(boolean[] wilayas) {
		this.wilayas = wilayas;
	}
	
	public String getEasylist() {
		return easylist;
	}
	
	public void setEasylist(String easylist) {
		this.easylist = easylist;
	}
	
	public int getCurrItem() {
		return (page - 1) * row;
	}
	
	public String[] parseKeysword() {
		return keysword.split(",");
	}
	
	public boolean hasPresentToken() {
		return !StringUtils.isEmpty(token);
	}
	
	public boolean hasPresentKeysword() {
		return !StringUtils.isEmpty(keysword);
	}
	
	public boolean hasPresentWilayas() {
		for (final boolean wilaya : wilayas) {
			if(wilaya) return true;
		}
		return false;
	}
	
	public List<Integer> parseWilayas() {
		final List<Integer> lines = new ArrayList<Integer>();
		for(int i = 0; i < wilayas.length; i++) {
			if(wilayas[i]) lines.add(i + 1);
		}
		return lines;
	}

	@Override
	public String toString() {
		return "SearchFilterForm [userId=" + userId + ", token=" + token + ", keysword=" + keysword + ", row=" + row
				+ ", sort=" + sort + ", page=" + page + ", desc=" + desc + ", wilayas=" + Arrays.toString(wilayas)
				+ ", easylist=" + easylist + "]";
	}

}
