package com.rinitec.algerieoffice.security.oauth2.client;

import java.util.Map;

public abstract class OAuth2UserInfo {

	protected Map<String, Object> attributes;
	
	public OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public abstract String getId();

	public abstract String getFirstname();
	
	public abstract String getLastname();
	
	public abstract String getDisplayName();

	public abstract String getEmail();

	public abstract String getImageUrl();
	
	
}
