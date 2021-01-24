package digital.number.scanner.exception;

public class InvalidChunkException extends RuntimeException {
	public InvalidChunkException(String message) {
		super("Invalid chunk - " + message);
	}
}
