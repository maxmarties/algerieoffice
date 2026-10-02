package com.rinitec.algerieoffice.security.oauth2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.register.IRegistrationService;
import com.rinitec.algerieoffice.web.error.oauth2.OAuth2AuthenticationProcessingException;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;

@Service
public class CustomOidcUserService extends OidcUserService {

	private HttpServletRequest request;
	private IRegistrationService registrationService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CustomOidcUserService(HttpServletRequest request, IRegistrationService registrationService, 
			ApplicationEventPublisher eventPublisher) {
		this.request = request;
		this.registrationService = registrationService;
		this.eventPublisher = eventPublisher;
	}
	
	@Override
	public OidcUser loadUser(final OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		final OidcUser oidcUser = super.loadUser(userRequest);
		try {
			final LocalUser localUser = registrationService.registerOrLogin(userRequest.getClientRegistration().getRegistrationId(), 
					oidcUser.getAttributes(), oidcUser.getIdToken(), oidcUser.getUserInfo());
			if(!StringUtils.isEmpty(localUser.getUser().getRandomPassword())) {
				eventPublisher.publishEvent(new OnRegisterEvent(localUser.getUser(), request, EmailType.registerPassword));
			}
			return localUser;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			throw new OAuth2AuthenticationProcessingException(e.getMessage(), e.getCause());
		}
	}
	
}
