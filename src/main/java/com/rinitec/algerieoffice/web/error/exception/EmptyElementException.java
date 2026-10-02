package com.rinitec.algerieoffice.web.error.exception;

public class EmptyElementException extends RuntimeException {
	private static final long serialVersionUID = -3380577683238681225L;
	
	public EmptyElementException() {
	}
	
	public EmptyElementException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmptyElementException(final String message) {
        super(message);
    }

    public EmptyElementException(final Throwable cause) {
        super(cause);
    }

}
