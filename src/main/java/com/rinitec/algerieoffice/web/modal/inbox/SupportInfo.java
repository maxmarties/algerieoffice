package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

public class SupportInfo implements Serializable {
	private static final long serialVersionUID = -5103887954034292408L;
	
	private final String tradename;
	private final String companyURL;
	private final Integer premium;
	private final boolean hasLogin;
	
	public SupportInfo(final String tradename, final String url, final Integer premium, final boolean hasLogin) {
		this.tradename = tradename;
		this.companyURL = url;
		this.premium = premium == null ? 0 : premium;
		this.hasLogin = hasLogin;
	}

	public String getTradename() {
		return tradename;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public Integer getPremium() {
		return premium;
	}

	public boolean isHasLogin() {
		return hasLogin;
	}

	@Override
	public String toString() {
		return "SupportInfo [tradename=" + tradename + ", companyURL=" + companyURL + ", premium=" + premium
				+ ", hasLogin=" + hasLogin + "]";
	}

}
