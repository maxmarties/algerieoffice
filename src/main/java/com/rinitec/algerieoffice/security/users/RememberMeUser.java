package com.rinitec.algerieoffice.security.users;

import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;

public class RememberMeUser extends PersistentTokenBasedRememberMeServices {

	@Autowired
	private UserRepository userRepository;
	
	private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
	private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
	private PersistentTokenRepository tokenRepository = new InMemoryTokenRepositoryImpl();
	private String key;
	
	public RememberMeUser(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
		super(key, userDetailsService, tokenRepository);
		this.tokenRepository = tokenRepository;
        this.key = key;
	}
	
	private void addCookie(PersistentRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
		setCookie(new String[] { token.getSeries(), token.getTokenValue() }, getTokenValiditySeconds(), request, response);
	}
	
	@Override
	protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {
		final String email = ((LocalUser) successfulAuthentication.getPrincipal()).getUser().getEmail();
		PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(email, generateSeriesData(), 
				generateTokenData(), new Date());
		try {
			tokenRepository.createNewToken(persistentToken);
			addCookie(persistentToken, request, response);
		} catch (Exception e) {
			System.out.println("\nRememberMeUser.Exception : " + " (Failed to save persistent token = " + e + ")\n");	
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails userDetails) {
		final User user = userRepository.findByEmail(userDetails.getUsername());
		RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(key, LocalUser.build(user), 
				authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));
		auth.setDetails(authenticationDetailsSource.buildDetails(request));
		return auth;
	}
	
}
