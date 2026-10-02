package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

public class MessageInfo implements Serializable {
	private static final long serialVersionUID = 5616295192513859438L;
	
	private final String tradename;
	private final String companyURL;
	private final boolean hasBlocked;
	private final boolean hasLogin;
	
	public MessageInfo(final String tradename, final String url, final boolean hasBlocked, final boolean hasLogin) {
		this.tradename = tradename;
		this.companyURL = url;
		this.hasBlocked = hasBlocked;
		this.hasLogin = hasLogin;
	}

	public String getTradename() {
		return tradename;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public boolean isHasBlocked() {
		return hasBlocked;
	}

	public boolean isHasLogin() {
		return hasLogin;
	}

	@Override
	public String toString() {
		return "MessageInfo [tradename=" + tradename + ", companyURL=" + companyURL + ", hasBlocked=" + hasBlocked
				+ ", hasLogin=" + hasLogin + "]";
	}

}
