package com.rinitec.algerieoffice.web.form.forums;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class SearchForumForm implements Serializable {
	private static final long serialVersionUID = -1688522130482025340L;
	
	private Long userId;
	private String token;
	private Integer filter;
	private Integer category;
	private Integer tabulation;
	private int row;
	private int sort;
	private int page;
	private boolean desc;
	
	public SearchForumForm() {
		this.filter = this.category = 0;
		this.sort = this.page = 1;
		this.desc = true;
	}
	
	public SearchForumForm(final Long userId, final String token, final Integer category, final int row) {
		this();
		this.userId = userId;
		if(!StringUtils.isEmpty(token)) {
			this.token = token.replaceAll("\\+", " ");
		}
		this.category = category;
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

	public Integer getFilter() {
		return filter;
	}

	public void setFilter(Integer filter) {
		this.filter = filter;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getTabulation() {
		return tabulation;
	}

	public void setTabulation(Integer tabulation) {
		this.tabulation = tabulation;
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
	
	public boolean hasPresentFilter() {
		return filter != null && filter > 0;
	}
	
	public boolean hasPresentCategory() {
		return category != null && category > 0;
	}
	
	public boolean hasPresentTabulation() {
		return tabulation != null && tabulation > 0;
	}

	@Override
	public String toString() {
		return "SearchForumsForm [userId=" + userId + ", token=" + token + ", filter=" + filter + ", category="
				+ category + ", tabulation=" + tabulation + ", row=" + row + ", sort=" + sort + ", page=" + page
				+ ", desc=" + desc + "]";
	}
	
}
