package com.rinitec.algerieoffice.web.modal.feedback;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Sponsore;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class SponsoreFeedback implements Serializable {
	private static final long serialVersionUID = 510499016823026569L;
	
	private final String id;
	private final String bannerURL;
	private final String url;
	
	public SponsoreFeedback(final Sponsore sponsore) {
		this.id = sponsore.getId().toString();
		this.bannerURL = ConstraintesURL.URL_AOBNN + "?aobnId=".concat(sponsore.getBannerUUID().toString());
		this.url = sponsore.getUrl();
	}
	
	public String getId() {
		return id;
	}

	public String getBannerURL() {
		return bannerURL;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "SponsoreFeedback [id=" + id + ", bannerURL=" + bannerURL + ", url=" + url + "]";
	}

}
