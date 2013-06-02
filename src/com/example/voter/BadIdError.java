package com.example.voter;

public class BadIdError extends Exception {
	private static final long serialVersionUID = 0x17A81E38L;
	
	public BadIdError() {
        super();
    }
	
	public BadIdError(String msg) {
        super(msg);
    }
	
	public BadIdError(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public BadIdError(Throwable cause) {
        super(cause);
    }
}
