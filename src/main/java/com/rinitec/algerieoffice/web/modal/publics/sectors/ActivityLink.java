package com.rinitec.algerieoffice.web.modal.publics.sectors;

import java.io.Serializable;

public class ActivityLink implements Serializable {
	private static final long serialVersionUID = -1850401674504822733L;
	
	private final String code;
	private final String url;
	
	public ActivityLink(final String code, final String url) {
		this.code = code;
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "ActivityLink [code=" + code + ", url=" + url + "]";
	}

}
