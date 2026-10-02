package com.rinitec.algerieoffice.persistence.result;

public class CompanyAvatar {

	private final String tradename;
	private final boolean hasAvatar;
	
	public CompanyAvatar(final String tradename, final boolean hasAvatar) {
		this.tradename = tradename;
		this.hasAvatar = hasAvatar;
	}

	public String getTradename() {
		return tradename;
	}

	public boolean isHasAvatar() {
		return hasAvatar;
	}

	@Override
	public String toString() {
		return "CompanyAvatar [tradename=" + tradename + ", hasAvatar=" + hasAvatar + "]";
	}
	
}
