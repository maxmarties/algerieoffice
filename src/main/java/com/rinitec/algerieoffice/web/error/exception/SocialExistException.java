package com.rinitec.algerieoffice.web.error.exception;

public class SocialExistException extends RuntimeException {
	private static final long serialVersionUID = 5684359685627154881L;

	public SocialExistException() {
	}
	
	public SocialExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SocialExistException(final String message) {
        super(message);
    }

    public SocialExistException(final Throwable cause) {
        super(cause);
    }
    
}
