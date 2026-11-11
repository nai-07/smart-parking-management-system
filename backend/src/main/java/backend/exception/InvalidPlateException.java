package backend.exception;

public class InvalidPlateException extends RuntimeException {

    public InvalidPlateException(String message) {
        super(message);
    }
}