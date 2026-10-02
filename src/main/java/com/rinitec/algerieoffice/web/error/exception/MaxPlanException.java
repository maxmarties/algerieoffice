package com.rinitec.algerieoffice.web.error.exception;

public class MaxPlanException extends RuntimeException {
	private static final long serialVersionUID = 6694913141918656870L;

	public MaxPlanException() {
	}
	
	public MaxPlanException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MaxPlanException(final Throwable cause) {
        super(cause);
    }
    
    public MaxPlanException(final String message) {
        super(message);
    }
	
}
