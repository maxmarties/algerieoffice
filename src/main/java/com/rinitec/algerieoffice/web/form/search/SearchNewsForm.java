package com.rinitec.algerieoffice.web.form.search;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class SearchNewsForm implements Serializable {
	private static final long serialVersionUID = 4353248713257834388L;
	
	private Long userId;
	private String token;
	private Integer sector;
	private Integer wilaya;
	private int row;
	private int page;
	
	public SearchNewsForm() {
		this.sector = 0;
		this.wilaya = 0;
		this.row = 20;
		this.page = 1;
	}
	
	public SearchNewsForm(final Long userId, final String token, final Integer wilaya, final int row) {
		this();
		this.userId = userId;
		this.token = token;
		this.wilaya = wilaya;
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

	public Integer getSector() {
		return sector;
	}
	
	public void setSector(Integer sector) {
		this.sector = sector;
	}
	
	public Integer getWilaya() {
		return wilaya;
	}
	
	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public int getCurrItem() {
		return (page - 1) * row;
	}
	
	public boolean hasPresentSector() {
		return sector != null && sector != 0;
	}
	
	public boolean hasPresentWilaya() {
		return wilaya != null && wilaya != 0;
	}
	
	public boolean hasPresentFilter() {
		return !StringUtils.isEmpty(token) || hasPresentSector() || hasPresentWilaya();
	}
	
	public boolean hasBeginReaden() {
		return page == 1 && !hasPresentFilter();
	}

	@Override
	public String toString() {
		return "SearchNewsForm [userId=" + userId + ", token=" + token + ", sector=" + sector + ", wilaya=" + wilaya
				+ ", row=" + row + ", page=" + page + "]";
	}

}
