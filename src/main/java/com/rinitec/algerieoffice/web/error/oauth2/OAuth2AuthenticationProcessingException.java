package com.rinitec.algerieoffice.web.error.oauth2;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
	private static final long serialVersionUID = -4631358990891945640L;

	public OAuth2AuthenticationProcessingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public OAuth2AuthenticationProcessingException(final String message) {
		super(message);
	}
	
}
