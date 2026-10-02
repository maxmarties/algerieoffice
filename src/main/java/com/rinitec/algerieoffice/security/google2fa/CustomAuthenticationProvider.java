package com.rinitec.algerieoffice.security.google2fa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private UserRepository userRepository;
	
	public CustomAuthenticationProvider() {
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final User user = userRepository.findByEmail(authentication.getName());
		if (user == null) {
			throw new BadCredentialsException("Invalid email or password");
		}
		final Authentication result = super.authenticate(authentication);
		return new UsernamePasswordAuthenticationToken(LocalUser.build(user), result.getCredentials(), result.getAuthorities());
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
