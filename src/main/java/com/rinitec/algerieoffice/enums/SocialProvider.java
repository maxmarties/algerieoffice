package com.rinitec.algerieoffice.enums;

public enum SocialProvider {

	FACEBOOK("facebook"), GOOGLE("google"), LINKEDIN("linkedin");

	private String providerType;

	SocialProvider(final String providerType) {
		this.providerType = providerType;
	}
	
	public String getProviderType() {
		return providerType;
	}
	
}
