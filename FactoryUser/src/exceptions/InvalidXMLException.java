package exceptions;

public class InvalidXMLException extends RuntimeException{
	
	public InvalidXMLException() {
		super("XML dokument nije u skladu sa definisanom semom");
	}
	
	public InvalidXMLException(String message) {
		super(message);
	}
	
	

}
