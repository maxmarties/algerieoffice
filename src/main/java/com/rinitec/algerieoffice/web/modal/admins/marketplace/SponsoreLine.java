package com.rinitec.algerieoffice.web.modal.admins.marketplace;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Sponsore;

public class SponsoreLine implements Serializable {
	private static final long serialVersionUID = 784517029846758566L;
	
	private final String id;
	private final String url;
	private final int type;
	private final int viewCount;
	private final int clickCount;
	private final int creditCount;
	private final boolean hasPublished;
	
	public SponsoreLine(final Sponsore sponsore) {
		this.id = sponsore.getId().toString();
		this.url = sponsore.getUrl();
		this.type = sponsore.getType();
		this.viewCount = sponsore.getViewCount();
		this.clickCount = sponsore.getClickCount();
		this.creditCount = sponsore.getCreditCount();
		this.hasPublished = sponsore.getHasPublished();
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public int getType() {
		return type;
	}

	public int getViewCount() {
		return viewCount;
	}

	public int getClickCount() {
		return clickCount;
	}

	public int getCreditCount() {
		return creditCount;
	}

	public boolean isHasPublished() {
		return hasPublished;
	}

	@Override
	public String toString() {
		return "SponsoreLine [id=" + id + ", url=" + url + ", type=" + type + ", viewCount=" + viewCount
				+ ", clickCount=" + clickCount + ", creditCount=" + creditCount + ", hasPublished=" + hasPublished + "]";
	}

}
