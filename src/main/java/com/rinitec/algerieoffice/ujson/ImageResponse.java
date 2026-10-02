package com.rinitec.algerieoffice.ujson;

import java.io.Serializable;

public class ImageResponse implements Serializable {
	private static final long serialVersionUID = -7839308088209474328L;
	
	private String id;
	private String thumb;
	private String url;
	private String filename;
	
	public ImageResponse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
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
		return "ImageResponse [id=" + id + ", thumb=" + thumb + ", url=" + url + ", filename=" + filename + "]";
	}

}
