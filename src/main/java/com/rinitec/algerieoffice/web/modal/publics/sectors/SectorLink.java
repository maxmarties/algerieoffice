package com.rinitec.algerieoffice.web.modal.publics.sectors;

import java.io.Serializable;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class SectorLink implements Serializable {
	private static final long serialVersionUID = -6911793974716648851L;
	
	private final String code;
	private final String activityURL;
	private final long count;
	
	public SectorLink(final String code, final String url, final long count) {
		this.code = code;
		this.activityURL = ConstraintesURL.URL_SECTOR_ACTIVITY.concat("/").concat(url);
		this.count = count;
	}
	
	public SectorLink(final String code, final String url, final long count, final String wilayaURL) {
		this.code = code;
		this.count = count;
		this.activityURL = wilayaURL.concat("/").concat(url);
	}

	public String getCode() {
		return code;
	}

	public String getActivityURL() {
		return activityURL;
	}

	public long getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "SectorLink [code=" + code + ", activityURL=" + activityURL + ", count=" + count + "]";
	}

}
