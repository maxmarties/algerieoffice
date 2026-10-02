package com.rinitec.algerieoffice.web.error.exception;

public class CompanymailExistException extends RuntimeException {
	private static final long serialVersionUID = 7178619994195530709L;

	public CompanymailExistException() {
	}
	
	public CompanymailExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CompanymailExistException(final String message) {
        super(message);
    }

    public CompanymailExistException(final Throwable cause) {
        super(cause);
    }
	
}
