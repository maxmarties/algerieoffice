package com.rinitec.algerieoffice.web.error.exception;

public class InvalidPasswordException extends RuntimeException {
	private static final long serialVersionUID = 5339926540308735824L;

	public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(final String message) {
        super(message);
    }

    public InvalidPasswordException(final Throwable cause) {
        super(cause);
    }
    
}
