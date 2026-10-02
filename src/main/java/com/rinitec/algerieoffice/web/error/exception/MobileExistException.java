package com.rinitec.algerieoffice.web.error.exception;

public class MobileExistException extends RuntimeException {
	private static final long serialVersionUID = 2139337133200353276L;

	public MobileExistException() {
	}
	
	public MobileExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MobileExistException(final String message) {
        super(message);
    }

    public MobileExistException(final Throwable cause) {
        super(cause);
    }
	
}
