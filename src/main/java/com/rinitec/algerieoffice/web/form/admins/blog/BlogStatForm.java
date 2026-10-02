package com.rinitec.algerieoffice.web.form.admins.blog;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class BlogStatForm implements Serializable {
	private static final long serialVersionUID = -6983158639968206510L;
	
	@NotNull
	private String id;
	
	@NotNull
	private String title;
	
	private int viewCount;
	private int simultude;
	private int market;
	
	public BlogStatForm() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getSimultude() {
		return simultude;
	}

	public void setSimultude(int simultude) {
		this.simultude = simultude;
	}

	public int getMarket() {
		return market;
	}

	public void setMarket(int market) {
		this.market = market;
	}

	@Override
	public String toString() {
		return "BlogStatForm [id=" + id + ", title=" + title + ", viewCount=" + viewCount + ", simultude=" + simultude
				+ ", market=" + market + "]";
	}

}
