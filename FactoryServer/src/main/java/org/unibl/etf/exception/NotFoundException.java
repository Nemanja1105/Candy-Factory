package org.unibl.etf.exception;

public class NotFoundException extends RuntimeException{
	public NotFoundException() {
		super("Korisnicki nalog sa datim kredencijalima nije pronadjen");
	}
	
	public NotFoundException(String message) {
		super(message);
	}

}
