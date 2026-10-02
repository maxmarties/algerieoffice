package com.rinitec.algerieoffice.web.error.exception;

public class AlreadyExistException extends RuntimeException {
	private static final long serialVersionUID = -1582603147376145667L;

	public AlreadyExistException() {
	}
	
	public AlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistException(final String message) {
        super(message);
    }

    public AlreadyExistException(final Throwable cause) {
        super(cause);
    }
	
}
