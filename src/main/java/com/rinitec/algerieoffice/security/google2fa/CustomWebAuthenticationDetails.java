package com.rinitec.algerieoffice.security.google2fa;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final long serialVersionUID = -2958058387387287345L;
	
	private final String verificationCode;
	
	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
        verificationCode = request.getParameter("code");
	}
	
	public String getVerificationCode() {
		return verificationCode;
	}
	
}
