package com.rinitec.algerieoffice.web.error.exception;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2130304496339214241L;

	public NotFoundException() {
        super();
    }

    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final Throwable cause) {
        super(cause);
    }
	
}
