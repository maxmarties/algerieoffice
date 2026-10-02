package com.rinitec.algerieoffice.security.oauth2.client;

import java.util.Map;

public class LinkedinOAuth2UserInfo extends OAuth2UserInfo {

	public LinkedinOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return (String) attributes.get("id");
	}
	
	@Override
	public String getFirstname() {
		return (String) attributes.get("localizedFirstName");
	}
	
	@Override
	public String getLastname() {
		return (String) attributes.get("localizedLastName");
	}

	@Override
	public String getDisplayName() {
		return ((String) attributes.get("localizedFirstName")).concat(" ").concat((String) attributes.get("localizedLastName"));
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("emailAddress");
	}

	@Override
	public String getImageUrl() {
		return (String) attributes.get("pictureUrl");
	}

}
