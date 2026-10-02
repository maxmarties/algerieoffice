package com.rinitec.algerieoffice.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.rinitec.algerieoffice.utils.SecurityUtil;

public class LocalUser extends User implements OAuth2User, OidcUser {
	private static final long serialVersionUID = -4231059995885480823L;
	
	private final OidcIdToken idToken;
	private final OidcUserInfo userInfo;
	private Map<String, Object> attributes;
	private com.rinitec.algerieoffice.persistence.modal.users.User user;

	public LocalUser(final String username, final String password, final boolean enabled, final boolean accountNonExpired,
			final boolean credentialsNonExpired, final boolean accountNonLocked, 
			final Collection<? extends GrantedAuthority> authorities, 
			final com.rinitec.algerieoffice.persistence.modal.users.User user) {
		this(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, user, null, null);
	}
	
	public LocalUser(final String username, final String password, final boolean enabled, final boolean accountNonExpired, 
			final boolean credentialsNonExpired, final boolean accountNonLocked, 
			final Collection<? extends GrantedAuthority> authorities, 
			final com.rinitec.algerieoffice.persistence.modal.users.User user, OidcIdToken idToken, OidcUserInfo userInfo) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
		this.idToken = idToken;
		this.userInfo = userInfo;
	}

	@Override
	public String getName() {
		return this.user.getDisplayName();
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}
	
	@Override
	public Map<String, Object> getClaims() {
		return this.attributes;
	}
	
	@Override
	public OidcUserInfo getUserInfo() {
		return this.userInfo;
	}
	
	@Override
	public OidcIdToken getIdToken() {
		return this.idToken;
	}
	
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public com.rinitec.algerieoffice.persistence.modal.users.User getUser() {
		return user;
	}
	
	public Long getUserId() {
		return user.getId();
	}
	
	public Long getCompanyId() {
		return user.getCompanyId();
	}
	
	public static LocalUser create(com.rinitec.algerieoffice.persistence.modal.users.User user, Map<String, Object> attributes, 
			OidcIdToken idToken, OidcUserInfo userInfo) {
		final LocalUser localUser = new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), !user.isExpired(), 
				true, !user.isLocked(), SecurityUtil.getAuthorities(user.getRoles()), user, idToken, userInfo);
		localUser.setAttributes(attributes);
		return localUser;
	}
	
	public static LocalUser build(com.rinitec.algerieoffice.persistence.modal.users.User user) {
		final LocalUser localUser = new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), !user.isExpired(),
				true, !user.isLocked(), SecurityUtil.getAuthorities(user.getRoles()), user);
		return localUser;
	}
	
}
