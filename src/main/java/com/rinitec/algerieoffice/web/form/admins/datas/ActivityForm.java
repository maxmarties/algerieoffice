package com.rinitec.algerieoffice.web.form.admins.datas;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.web.validator.ValidActivity;
import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidUrl;

public class ActivityForm implements Serializable {
	private static final long serialVersionUID = 8170291950350390261L;

	private Long id;
	
	@ValidActivity
	@NotNull
	private String code;
	
	@ValidChose
	private Integer sector;
	
	@ValidUrl
	@NotNull
	private String url;
	
	private String checkedUrl;
	
	public ActivityForm() {
	}
	
	public ActivityForm(final Activity codeActivity) {
		this.id = codeActivity.getId();
		this.code = codeActivity.getCode();
		this.sector = codeActivity.getSector();
		this.url = this.checkedUrl = codeActivity.getUrl();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSector() {
		return sector;
	}

	public void setSector(Integer sector) {
		this.sector = sector;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getCheckedUrl() {
		return checkedUrl;
	}
	
	public void setCheckedUrl(String checkedUrl) {
		this.checkedUrl = checkedUrl;
	}

	@Override
	public String toString() {
		return "ActivityForm [id=" + id + ", code=" + code + ", sector=" + sector + ", url=" + url + ", checkedUrl=" + checkedUrl + "]";
	}
	
}
