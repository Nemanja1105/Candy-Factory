package org.unibl.etf.exception;

public class UserNotActivatedException extends RuntimeException{
	public UserNotActivatedException() {
		super("Korisnicki nalog nije aktiviran");
	}
	
	public UserNotActivatedException(String message) {
		super(message);
	}
	

}
