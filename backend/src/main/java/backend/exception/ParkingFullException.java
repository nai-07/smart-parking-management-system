package backend.exception;

public class ParkingFullException extends RuntimeException {

    public ParkingFullException(String message) {
        super(message);
    }
}