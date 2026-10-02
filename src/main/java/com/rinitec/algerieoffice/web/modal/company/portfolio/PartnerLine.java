package com.rinitec.algerieoffice.web.modal.company.portfolio;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Partner;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class PartnerLine implements Serializable {
	private static final long serialVersionUID = -7122489530272809848L;
	
	private final String id;
	private final String name;
	private final String url;
	private final String autor;
	private final String urlAvatar;
	private final String modifiedDate;
	private final boolean hasPingled;
	
	public PartnerLine(final Partner partner, final String autor) {
		this.id = partner.getId().toString();
		this.name = partner.getName();
		this.url = partner.getUrl();
		this.autor = autor;
		this.urlAvatar = partner.getPhotoUUID() != null 
				? ConstraintesURL.URL_PHOTOS + "?photoId=".concat(partner.getPhotoUUID().toString()).concat("&width=32&height=32")
				: "/static/picts/avatars/company_mini-min.jpg";
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(partner.getModifiedDate());
		this.hasPingled = partner.getHasPingled();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getAutor() {
		return autor;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public boolean isHasPingled() {
		return hasPingled;
	}

	@Override
	public String toString() {
		return "PartnerLine [id=" + id + ", name=" + name + ", url=" + url + ", autor=" + autor + ", urlAvatar="
				+ urlAvatar + ", modifiedDate=" + modifiedDate + ", hasPingled=" + hasPingled + "]";
	}

}
