package org.unibl.etf.exception;

public class UserAlreadyExistsException extends RuntimeException {
	
	public UserAlreadyExistsException() {
		super("Korisnicko ime je vec zauzeto");
	}
	
	public UserAlreadyExistsException(String message) {
		super(message);
	}

}
