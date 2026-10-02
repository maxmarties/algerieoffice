package com.rinitec.algerieoffice.web.error.exception;

public class AccessUploadException extends RuntimeException {
	private static final long serialVersionUID = 5422254285132546875L;

	public AccessUploadException() {
	}
	
	public AccessUploadException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccessUploadException(final String message) {
        super(message);
    }

    public AccessUploadException(final Throwable cause) {
        super(cause);
    }
    
}
