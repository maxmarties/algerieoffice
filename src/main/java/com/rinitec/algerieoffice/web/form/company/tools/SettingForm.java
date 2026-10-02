package com.rinitec.algerieoffice.web.form.company.tools;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class SettingForm implements Serializable {
	private static final long serialVersionUID = -4724642888550691496L;
	
	@NotNull
	private Long id;
	
	private boolean[] params = new boolean[5];
	
	private boolean service;
	private boolean posthome;
	private boolean indexed;
	
	private String banner;
	private String alertname;
	
	public SettingForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean[] getParams() {
		return params;
	}

	public void setParams(boolean[] params) {
		this.params = params;
	}

	public boolean isService() {
		return service;
	}

	public void setService(boolean service) {
		this.service = service;
	}

	public boolean isPosthome() {
		return posthome;
	}

	public void setPosthome(boolean posthome) {
		this.posthome = posthome;
	}

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	public String getAlertname() {
		return alertname;
	}
	
	public void setAlertname(String alertname) {
		this.alertname = alertname;
	}
	
	public void parseParams(final String params) {
		for (int i = 0; i < 5; i++) {
			this.params[i] = (params.charAt(i) == '1');
		}
	}
	
	public String builderParams() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			builder.append(params[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public void parseDefaultParams() {
		for (int i = 0; i < 5; i++) {
			this.params[i] = i != 2 && i != 3;
		}
	}

	@Override
	public String toString() {
		return "SettingForm [id=" + id + ", params=" + params + ", service=" + service + ", posthome=" + posthome
				+ ", indexed=" + indexed + ", banner=" + banner + ", alertname=" + alertname + "]";
	}

}
