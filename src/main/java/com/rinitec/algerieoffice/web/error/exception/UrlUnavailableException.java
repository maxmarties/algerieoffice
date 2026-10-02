package com.rinitec.algerieoffice.web.error.exception;

public class UrlUnavailableException extends RuntimeException {
	private static final long serialVersionUID = -9071947405970118796L;

	public UrlUnavailableException() {
	}
	
	public UrlUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UrlUnavailableException(final String message) {
        super(message);
    }

    public UrlUnavailableException(final Throwable cause) {
        super(cause);
    }
	
}
