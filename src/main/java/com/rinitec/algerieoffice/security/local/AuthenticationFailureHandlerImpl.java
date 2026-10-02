package com.rinitec.algerieoffice.security.local;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.rinitec.algerieoffice.web.error.oauth2.OAuth2AuthenticationProcessingException;

@Component
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {
	
	private static final String URL_LOGIN = "/users/login";

	private MessageSource messages;
    private LocaleResolver localeResolver;
    
    @Autowired
	public AuthenticationFailureHandlerImpl(MessageSource messages, LocaleResolver localeResolver) {
		this.messages = messages;
		this.localeResolver = localeResolver;
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		setDefaultFailureUrl(URL_LOGIN.concat("?error=true"));
		super.onAuthenticationFailure(request, response, exception);
		final Locale locale = localeResolver.resolveLocale(request);
		String errorMessage = messages.getMessage("auth.message.badCredentials", null, locale);
		if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
			errorMessage = messages.getMessage("auth.message.disabled", null, locale);
		}
		else if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
			errorMessage = messages.getMessage("auth.message.locked", null, locale);
		}
		else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
			errorMessage = messages.getMessage("auth.message.expired", null, locale);
		} 
		else if (exception.getMessage().equalsIgnoreCase("blocked")) {
			errorMessage = messages.getMessage("auth.message.blocked", null, locale);
		} else if(exception instanceof OAuth2AuthenticationProcessingException) {
			errorMessage = messages.getMessage(exception.getMessage() == null ? "auth.oauth2.unavailable" 
					: exception.getMessage(), null, locale);
		}
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
	}
	
}
