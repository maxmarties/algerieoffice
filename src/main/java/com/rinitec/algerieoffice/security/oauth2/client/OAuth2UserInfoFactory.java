package com.rinitec.algerieoffice.security.oauth2.client;

import java.util.Map;

import com.rinitec.algerieoffice.enums.SocialProvider;
import com.rinitec.algerieoffice.web.error.oauth2.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {

	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(SocialProvider.GOOGLE.getProviderType())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.FACEBOOK.getProviderType())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(SocialProvider.LINKEDIN.getProviderType())) {
			return new LinkedinOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException("auth.oauth2.notSupported");
		}
	}
	
}
