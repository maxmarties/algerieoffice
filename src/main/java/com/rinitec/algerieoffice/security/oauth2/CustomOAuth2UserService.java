package com.rinitec.algerieoffice.security.oauth2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.enums.SocialProvider;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.services.register.IRegistrationService;
import com.rinitec.algerieoffice.web.error.oauth2.OAuth2AuthenticationProcessingException;
import com.rinitec.algerieoffice.web.listener.events.OnRegisterEvent;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	private Environment environment;
	private HttpServletRequest request;
	private IRegistrationService registrationService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public CustomOAuth2UserService(Environment environment, HttpServletRequest request, 
			IRegistrationService registrationService, ApplicationEventPublisher eventPublisher) {
		this.environment = environment;
		this.request = request;
		this.registrationService = registrationService;
		this.eventPublisher = eventPublisher;
	}
	
	@Override
	public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		final OAuth2User oAuth2User = super.loadUser(userRequest);
		try {
			Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
			String provider = userRequest.getClientRegistration().getRegistrationId();
			if (provider.equals(SocialProvider.LINKEDIN.getProviderType())) {
				populateEmailAddressFromLinkedIn(userRequest, attributes);
			}
			final LocalUser localUser = registrationService.registerOrLogin(provider, attributes, null, null);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void populateEmailAddressFromLinkedIn(final OAuth2UserRequest userRequest, 
			Map<String, Object> attributes) throws OAuth2AuthenticationException {
		final String emailEndpointUri = environment.getProperty("linkedin.email-address-uri");
		Assert.notNull(emailEndpointUri, "LinkedIn email address end point required");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + userRequest.getAccessToken().getTokenValue());
		HttpEntity<?> entity = new HttpEntity<>("", headers);
		ResponseEntity<Map> response = restTemplate.exchange(emailEndpointUri, HttpMethod.GET, entity, Map.class);
		List<?> list = (List<?>) response.getBody().get("elements");
		Map map = (Map<?, ?>) ((Map<?, ?>) list.get(0)).get("handle~");
		attributes.putAll(map);
	}
	
}
