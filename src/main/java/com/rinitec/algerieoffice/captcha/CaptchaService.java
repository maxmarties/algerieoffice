package com.rinitec.algerieoffice.captcha;

import java.net.URI;


import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import com.rinitec.algerieoffice.web.error.captcha.ReCaptchaInvalidException;

@Service("captchaService")
public class CaptchaService implements ICaptchaService {
	private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
	
	private HttpServletRequest request;
    private CaptchaSettings captchaSettings;
    private ReCaptchaAttemptService reCaptchaAttemptService;
    private RestOperations restTemplate;
    
    @Autowired
	public CaptchaService(HttpServletRequest request, CaptchaSettings captchaSettings,
			ReCaptchaAttemptService reCaptchaAttemptService, RestOperations restTemplate) {
		this.request = request;
		this.captchaSettings = captchaSettings;
		this.reCaptchaAttemptService = reCaptchaAttemptService;
		this.restTemplate = restTemplate;
	}
    
    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
	
	private boolean responseSanityCheck(final String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }
	
	@Override
	public String getReCaptchaSite() {
		return captchaSettings.getSite();
	}
	
	@Override
	public String getReCaptchaSecret() {
		return captchaSettings.getSecret();
	}
	
	@Override
	public void processResponse(String response) throws ReCaptchaInvalidException {
		if (reCaptchaAttemptService.isBlocked(getClientIP())) {
			throw new ReCaptchaInvalidException("auth.recaptcha.message.clientExceeded");
		}
		if (!responseSanityCheck(response)) {
            throw new ReCaptchaInvalidException("auth.recaptcha.message.expired");
        }
		final URI verifyUri = URI.create(String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s", 
				getReCaptchaSecret(), response, getClientIP()));
		try {
			final GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
			if (!googleResponse.isSuccess()) {
				if (googleResponse.hasClientError()) {
					reCaptchaAttemptService.reCaptchaFailed(getClientIP());
				}
				throw new ReCaptchaInvalidException("auth.recaptcha.message.notValidated");
			}
		} catch (RestClientException e) {
			throw new ReCaptchaInvalidException("auth.recaptcha.message.unavailable");
		}
		reCaptchaAttemptService.reCaptchaSucceeded(getClientIP());
	}
	
}
