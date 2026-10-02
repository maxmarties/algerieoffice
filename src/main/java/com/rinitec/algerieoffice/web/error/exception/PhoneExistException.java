package com.rinitec.algerieoffice.web.error.exception;

public class PhoneExistException extends RuntimeException {
	private static final long serialVersionUID = 1433989280835077230L;

	public PhoneExistException() {
	}
	
	public PhoneExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PhoneExistException(final String message) {
        super(message);
    }

    public PhoneExistException(final Throwable cause) {
        super(cause);
    }
    
}
