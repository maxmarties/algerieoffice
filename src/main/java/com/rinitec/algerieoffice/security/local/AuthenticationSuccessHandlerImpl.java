package com.rinitec.algerieoffice.security.local;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.rinitec.algerieoffice.security.LocalUser;
import com.rinitec.algerieoffice.security.users.ActiveUserStore;
import com.rinitec.algerieoffice.security.users.LoggedUser;
import com.rinitec.algerieoffice.services.user.account.IAccountService;
import com.rinitec.algerieoffice.utils.RequestUtil;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	private IAccountService accountService;
	private ActiveUserStore activeUserStore;
	private RedirectStrategy redirectStrategy;
	
	@Autowired
	public AuthenticationSuccessHandlerImpl(IAccountService accountService, ActiveUserStore activeUserStore) {
		this.accountService = accountService;
		this.activeUserStore = activeUserStore;
		this.redirectStrategy = new DefaultRedirectStrategy();
	}
	
	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
	
	protected void clearAuthenticationAttributes(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session == null) {
            return;
        }
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	protected String determineTargetUrl(final Authentication authentication) {
		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (final GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("VISITOR_PRIVILEGE")) {
				return "/user/dashboard?logSucc=true";
			} else if (grantedAuthority.getAuthority().equals("COMPANY_VISIT_PRIVILEGE")) {
				return "/company/dashboard?logSucc=true";
			} else if (grantedAuthority.getAuthority().equals("SUPPORT_AUTOR_PRIVILEGE")) {
				return "/admin/dashboard?logSucc=true";
			}
		}
		throw new IllegalStateException();
	}
	
	protected void handle(final HttpServletRequest request, final HttpServletResponse response, 
			final Authentication authentication) throws IOException {
		final String targetUrl = determineTargetUrl(authentication);
		if (response.isCommitted()) {
			System.out.println("\nAuthenticationSuccessHandlerImpl.println : "
					+ " (Response has already been committed. Unable to redirect to = " + targetUrl + ")\n");
			return;
		}
		RequestUtil.postOrUpdateCookieConfig(request, response);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		final LocalUser localUser = (LocalUser) authentication.getPrincipal();
		accountService.registerVisit(localUser.getUser(), request.getHeader("user-agent"));
		handle(request, response, authentication);
		final HttpSession session = request.getSession(false);
		if (session != null) {
			final LoggedUser loggedUser = new LoggedUser(localUser.getUser().getEmail(), activeUserStore);
			session.setMaxInactiveInterval(60 * 60 * 2); //2H
			session.setAttribute("user", loggedUser);
		}
		clearAuthenticationAttributes(request);
	}
	
}
