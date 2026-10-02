package com.rinitec.algerieoffice.web.error.exception;

public class MaxKeyswordException extends RuntimeException {
	private static final long serialVersionUID = 6811894820110579097L;

	public MaxKeyswordException() {
	}
	
	public MaxKeyswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MaxKeyswordException(final Throwable cause) {
        super(cause);
    }
    
    public MaxKeyswordException(final String message) {
        super(message);
    }
	
}
