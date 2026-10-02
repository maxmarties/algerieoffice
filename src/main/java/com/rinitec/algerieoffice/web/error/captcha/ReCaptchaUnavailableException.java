package com.rinitec.algerieoffice.web.error.captcha;

public class ReCaptchaUnavailableException extends RuntimeException {
	private static final long serialVersionUID = -6123228620098713807L;

	public ReCaptchaUnavailableException() {
        super();
    }

    public ReCaptchaUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReCaptchaUnavailableException(final String message) {
        super(message);
    }

    public ReCaptchaUnavailableException(final Throwable cause) {
        super(cause);
    }
	
}
