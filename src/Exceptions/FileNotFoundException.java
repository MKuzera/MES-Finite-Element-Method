package Exceptions;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
