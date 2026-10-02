package com.rinitec.algerieoffice.web.error.exception;

public class InvalidResourceException extends RuntimeException {
	private static final long serialVersionUID = 4169102396085015277L;

	public InvalidResourceException() {
	}
	
	public InvalidResourceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidResourceException(final String message) {
        super(message);
    }

    public InvalidResourceException(final Throwable cause) {
        super(cause);
    }
}
