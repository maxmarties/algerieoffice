package com.rinitec.algerieoffice.web.form.search;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class SearchCompanyQuickly implements Serializable {
	private static final long serialVersionUID = -8304779589270436609L;
	
	private Long userId;
	private String token;
	private Integer wilaya;
	private Integer category;
	private Integer activity;
	private Integer family;
	private int row;
	private int sort;
	private int page;
	private boolean desc;
	private boolean filtred;
	
	public SearchCompanyQuickly() {
		this.sort = this.page = 1;
		this.desc = true;
	}
	
	public SearchCompanyQuickly(final Long userId, final int row) {
		this();
		this.userId = userId;
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

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}
	
	public Integer getFamily() {
		return family;
	}
	
	public void setFamily(Integer family) {
		this.family = family;
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

	public boolean isFiltred() {
		return filtred;
	}

	public void setFiltred(boolean filtred) {
		this.filtred = filtred;
	}
	
	public boolean hasPresentToken() {
		return !StringUtils.isEmpty(token);
	}
	
	public boolean hasPresentWilaya() {
		return wilaya != null && wilaya > 0;
	}
	
	public boolean hasPresentCategory() {
		return category != null && category > 0;
	}
	
	public boolean hasPresentActivity() {
		return activity != null && activity > 0;
	}
	
	public boolean hasBeginReaden() {
		return userId == null && page == 1 && !hasPresentToken() && !hasPresentWilaya() && !hasPresentCategory() && !hasPresentActivity();
	}
	
	public boolean hasPresentFilter() {
		return hasPresentWilaya() || hasPresentCategory() || hasPresentActivity() || filtred;
	}

	@Override
	public String toString() {
		return "SearchCompanyQuickly [userId=" + userId + ", token=" + token + ", wilaya=" + wilaya + ", category="
				+ category + ", activity=" + activity + ", family=" + family + ", row=" + row + ", sort=" + sort
				+ ", page=" + page + ", desc=" + desc + ", filtred=" + filtred + "]";
	}

}
