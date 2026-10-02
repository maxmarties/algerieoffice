package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Partner;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetPartner implements Serializable {
	private static final long serialVersionUID = 1454571555072310203L;
	
	private final String urlAvatar;
	private final String name;
	private final String biography;
	private final String url;
	
	public ExplorerWidgetPartner(final Partner partner) {
		this.urlAvatar = partner.getPhotoUUID() != null ? ConstraintesURL.URL_PHOTOS + "?photoId=".concat(partner.getPhotoUUID().toString()) 
				: "/static/picts/avatars/company-min.jpg";
		this.name = partner.getName();
		this.biography = partner.getBiography();
		this.url = partner.getUrl();
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getName() {
		return name;
	}
	
	public String getBiography() {
		return biography;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetPartner [urlAvatar=" + urlAvatar + ", name=" + name + ", biography=" + biography
				+ ", url=" + url + "]";
	}
	
}
