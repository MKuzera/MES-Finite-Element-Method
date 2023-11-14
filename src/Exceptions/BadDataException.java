package Exceptions;
/**
 * <p>Class BadException is a exception used when user enter incorrect value
 * </p>
 *Class BadException is a exception used when user enter incorrect value
 * egz.
 *Class Gauss.GaussTable checks if user entered one of the allowed values, if not allowed throw exception
 */
public class BadDataException extends RuntimeException {
    /**
     * Constructs a new BadDataException with the specified error message.
     *
     * @param errorMessage The error message associated with the exception.
     */
    public BadDataException(String errorMessage) {
        super(errorMessage);
    }
}