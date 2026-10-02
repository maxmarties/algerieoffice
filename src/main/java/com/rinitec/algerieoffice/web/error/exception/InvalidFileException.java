package com.rinitec.algerieoffice.web.error.exception;

public class InvalidFileException extends RuntimeException {
	private static final long serialVersionUID = 7399464862453489129L;
	
	public InvalidFileException() {
	}
	
	public InvalidFileException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidFileException(final String message) {
        super(message);
    }

    public InvalidFileException(final Throwable cause) {
        super(cause);
    }

}
