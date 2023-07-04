package exceptions;

public class InvalidLoginException extends RuntimeException{
	
	public InvalidLoginException() {
		super("Desial se greska prilikom prijave");
	}
	
	public InvalidLoginException(String message) {
		super(message);
	}

}
