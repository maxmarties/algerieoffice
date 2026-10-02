package com.rinitec.algerieoffice.web.form.user.setting;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.web.modal.CurrentConfig;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class GenralForm implements Serializable {
	private static final long serialVersionUID = 8727625933160063078L;
	
	@NotNull
	private Long id;
	
	private boolean welcome;
	
	@ValidChose
	@NotNull
	private Integer style;
	
	@ValidChose
	@NotNull
	private Integer token;
	
	@ValidChose
	@NotNull
	private Integer row;
	
	@ValidChose
	@NotNull
	private Integer result;
	
	@ValidChose
	@NotNull
	private Integer blog;
	
	private boolean simultude;
	
	private boolean scroll;
	
	public GenralForm() {
	}
	
	public GenralForm(final Long userId, final CurrentConfig currentConfig) {
		this.id = userId;
		this.welcome = currentConfig.isWelcome();
		this.style = currentConfig.getDefaultStyle();
		this.token = currentConfig.getDefaultToken() + 1;
		this.row = currentConfig.getDefaultRow() + 1;
		this.result = currentConfig.getDefaultResult() + 1;
		this.blog = currentConfig.getDefaultBlog() + 1;
		this.simultude = currentConfig.isSimultude();
		this.scroll = currentConfig.isScroll();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isWelcome() {
		return welcome;
	}

	public void setWelcome(boolean welcome) {
		this.welcome = welcome;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public Integer getToken() {
		return token;
	}

	public void setToken(Integer token) {
		this.token = token;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getBlog() {
		return blog;
	}

	public void setBlog(Integer blog) {
		this.blog = blog;
	}

	public boolean isSimultude() {
		return simultude;
	}

	public void setSimultude(boolean simultude) {
		this.simultude = simultude;
	}

	public boolean isScroll() {
		return scroll;
	}

	public void setScroll(boolean scroll) {
		this.scroll = scroll;
	}

	@Override
	public String toString() {
		return "GenralForm [id=" + id + ", welcome=" + welcome + ", style=" + style + ", token=" + token + ", row="
				+ row + ", result=" + result + ", blog=" + blog + ", simultude=" + simultude + ", scroll=" + scroll + "]";
	}

}
