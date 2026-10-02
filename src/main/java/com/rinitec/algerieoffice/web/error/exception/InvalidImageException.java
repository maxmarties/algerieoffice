package com.rinitec.algerieoffice.web.error.exception;

public class InvalidImageException extends RuntimeException {
	private static final long serialVersionUID = -8977199379962541295L;

	public InvalidImageException() {
	}
	
	public InvalidImageException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidImageException(final String message) {
        super(message);
    }

    public InvalidImageException(final Throwable cause) {
        super(cause);
    }
	
}
