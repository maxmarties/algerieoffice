package com.rinitec.algerieoffice.ujson;

import java.io.Serializable;

public class FilereaderResponse implements Serializable {
	private static final long serialVersionUID = -1686936457294080688L;
	
	private String id;
	private String url;
	private String filename;
	
	public FilereaderResponse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "FilereaderResponse [id=" + id + ", url=" + url + ", filename=" + filename + "]";
	}

}
