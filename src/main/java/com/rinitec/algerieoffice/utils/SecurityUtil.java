package com.rinitec.algerieoffice.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rinitec.algerieoffice.persistence.modal.users.Privilege;
import com.rinitec.algerieoffice.persistence.modal.users.Role;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.security.LocalUser;

public class SecurityUtil {

	
	public static LocalUser getAuthentication() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() instanceof LocalUser) {
			return (LocalUser) authentication.getPrincipal();
		}
		return null;
	}
	
	public static void authWithoutPassword(final User user) {
		final List<Privilege> privileges = user.getRoles().stream().map(role -> role.getPrivileges())
				.flatMap(list -> list.stream()).distinct().collect(Collectors.toList());
		final List<GrantedAuthority> authorities = privileges.stream().map(p -> new SimpleGrantedAuthority(p.getName()))
				.collect(Collectors.toList());
		final Authentication authentication = new UsernamePasswordAuthenticationToken(LocalUser.build(user), null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public static void authUpdatePassword(final User user) {
		final Authentication auth = new UsernamePasswordAuthenticationToken(LocalUser.build(user), null,  
				Arrays.asList(new SimpleGrantedAuthority("PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	public static boolean hasAuthority(final String authority) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			return authentication.getAuthorities().contains(new SimpleGrantedAuthority(authority));
		}
		return false;
	}
	
	public static final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (final Role role : roles) {
			role.getPrivileges().stream().map(p -> new SimpleGrantedAuthority(p.getName())).forEach(authorities::add);
		}
		return authorities;
	}
	
}
