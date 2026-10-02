package com.rinitec.algerieoffice.security.local;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rinitec.algerieoffice.persistence.dao.users.UserRepository;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.utils.RequestUtil;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private HttpServletRequest request;
	private UserRepository userRepository;
	private LoginAttemptService loginAttemptService;
	
	@Autowired
	public UserDetailsServiceImpl(HttpServletRequest request, UserRepository userRepository,
			LoginAttemptService loginAttemptService) {
		this.request = request;
		this.userRepository = userRepository;
		this.loginAttemptService = loginAttemptService;
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		final String ip = RequestUtil.getClientIP(request);
		if (loginAttemptService.isBlocked(ip)) {
			throw new RuntimeException("blocked");
		}
		try {
			final User user = userRepository.findByEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException("Compte introuvable avec l'attribut : " + email);
			}
			return LocalUser.build(user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
