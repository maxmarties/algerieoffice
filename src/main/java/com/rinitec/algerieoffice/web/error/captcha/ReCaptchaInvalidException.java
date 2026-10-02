package com.rinitec.algerieoffice.web.error.captcha;

public class ReCaptchaInvalidException extends RuntimeException {
	private static final long serialVersionUID = 3782800002184585217L;

	public ReCaptchaInvalidException() {
        super();
    }

    public ReCaptchaInvalidException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReCaptchaInvalidException(final String message) {
        super(message);
    }

    public ReCaptchaInvalidException(final Throwable cause) {
        super(cause);
    }
    
}
