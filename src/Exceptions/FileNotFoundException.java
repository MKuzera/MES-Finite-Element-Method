package Exceptions;
/**
 * <p>Class FileNotFoundException is a exception used when while importing data file is not found
 * </p>
 *Class FileNotFoundException is a exception used when while importing data file is not found
 */
public class FileNotFoundException extends RuntimeException {
    /**
     * Constructs a new FileNotFoundException with the specified error message.
     *
     * @param errorMessage The error message associated with the exception.
     */
    public FileNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
