package com.rinitec.algerieoffice.web.form.search;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class SearchMemberForm implements Serializable {
	private static final long serialVersionUID = 5577565172999585690L;
	
	private Long userId;
	private String token;
	private Integer letter;
	private Integer statu;
	private Integer wilaya;
	private int row;
	private int page;
	
	public SearchMemberForm() {
		this.statu = 0;
		this.wilaya = 0;
		this.row = 20;
		this.page = 1;
	}
	
	public SearchMemberForm(final Long userId, final String token, final Integer letter, final int row) {
		this();
		this.userId = userId;
		this.token = token;
		this.letter = letter;
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

	public Integer getLetter() {
		return letter;
	}

	public void setLetter(Integer letter) {
		this.letter = letter;
	}
	
	public Integer getStatu() {
		return statu;
	}
	
	public void setStatu(Integer statu) {
		this.statu = statu;
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
	
	public boolean hasPresentStatu() {
		return statu != null && statu != 0;
	}
	
	public boolean hasPresentWilaya() {
		return wilaya != null && wilaya != 0;
	}
	
	public boolean hasPresentFilter() {
		return !StringUtils.isEmpty(token) || letter != null || hasPresentStatu() || hasPresentWilaya();
	}
	
	public String parseLetter() {
		return ConstraintesURL.URL_LETTERS[letter - 1].toLowerCase();
	}

	@Override
	public String toString() {
		return "SearchMemberForm [userId=" + userId + ", token=" + token + ", letter=" + letter + ", statu=" + statu
				+ ", wilaya=" + wilaya + ", row=" + row + ", page=" + page + "]";
	}

}
