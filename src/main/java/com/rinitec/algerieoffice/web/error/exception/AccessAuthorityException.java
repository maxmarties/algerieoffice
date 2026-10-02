package com.rinitec.algerieoffice.web.error.exception;

public class AccessAuthorityException extends RuntimeException {
	private static final long serialVersionUID = -8501573967741286323L;

	public AccessAuthorityException() {
	}
	
	public AccessAuthorityException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccessAuthorityException(final String message) {
        super(message);
    }

    public AccessAuthorityException(final Throwable cause) {
        super(cause);
    }
	
}
