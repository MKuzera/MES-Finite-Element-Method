package Exceptions;

public class BadDataException extends RuntimeException {
    public BadDataException(String errorMessage) {
        super(errorMessage);
    }
}
