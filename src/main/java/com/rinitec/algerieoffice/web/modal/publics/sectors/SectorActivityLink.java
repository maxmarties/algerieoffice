package com.rinitec.algerieoffice.web.modal.publics.sectors;

import java.io.Serializable;

public class SectorActivityLink implements Serializable {
	private static final long serialVersionUID = 5103105374033257953L;
	
	private final String wilayaURL;
	private final long count;
	
	public SectorActivityLink(final String wilayaURL, final long count) {
		this.wilayaURL = wilayaURL;
		this.count = count;
	}

	public String getWilayaURL() {
		return wilayaURL;
	}

	public long getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "SectorActivityLink [wilayaURL=" + wilayaURL + ", count=" + count + "]";
	}

}
