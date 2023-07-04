package org.unibl.etf.exception;

public class UserBlockedException extends RuntimeException{
	
	public UserBlockedException() {
		super("Korisnicki nalog je blokiran");
	}
	
	public UserBlockedException(String message) {
		super(message);
	}

}
