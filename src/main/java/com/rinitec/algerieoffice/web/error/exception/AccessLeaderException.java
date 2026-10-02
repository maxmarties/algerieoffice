package com.rinitec.algerieoffice.web.error.exception;

public class AccessLeaderException extends RuntimeException {
	private static final long serialVersionUID = -8501573967741286323L;

	public AccessLeaderException() {
	}
	
	public AccessLeaderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccessLeaderException(final String message) {
        super(message);
    }

    public AccessLeaderException(final Throwable cause) {
        super(cause);
    }

}
