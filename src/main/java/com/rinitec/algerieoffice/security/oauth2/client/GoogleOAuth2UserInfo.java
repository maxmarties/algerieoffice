package com.rinitec.algerieoffice.security.oauth2.client;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

	public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return (String) attributes.get("sub");
	}
	
	@Override
	public String getFirstname() {
		final String[] providers = ((String) attributes.get("name")).split(" ");
		if(providers.length == 2) {
			return providers[0];
		}
		String firstname = "";
		for (int i = 0; i < providers.length - 1; i++) {
			firstname += providers[i].concat(i < providers.length - 2 ? " " : "");
		}
		return firstname;
	}
	
	@Override
	public String getLastname() {
		final String[] providers = ((String) attributes.get("name")).split(" ");
		return providers.length > 0 ? providers[providers.length - 1] : "";
	}

	@Override
	public String getDisplayName() {
		return (String) attributes.get("name");
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getImageUrl() {
		return (String) attributes.get("picture");
	}
	
}
