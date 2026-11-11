package backend.exception;

public class InvalidValidationCodeException extends RuntimeException {

    public InvalidValidationCodeException(String message) {
        super(message);
    }
}