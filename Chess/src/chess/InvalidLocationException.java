package chess;

public class InvalidLocationException extends Exception {
	public InvalidLocationException(String errMsg) { 
		super(errMsg);
	}
}
