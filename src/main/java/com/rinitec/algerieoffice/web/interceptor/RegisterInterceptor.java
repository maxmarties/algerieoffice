package com.rinitec.algerieoffice.web.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rinitec.algerieoffice.utils.SecurityUtil;

public class RegisterInterceptor extends HandlerInterceptorAdapter {

	private List<String> patterns;
	
	public RegisterInterceptor() {
		patterns = new ArrayList<>(Arrays.asList("/users/**", "/register/**", "/website/update-password/new*"));
	}
	
	public List<String> getPatterns() {
		return patterns;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(SecurityUtil.hasAuthority("ACCOUNT_PRIVILEGE")) {
			response.sendRedirect(request.getContextPath() + "/website/login-target?info=loged");
			return false;
		}
		return true;
	}
	
}
