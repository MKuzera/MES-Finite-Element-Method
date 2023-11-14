package Exceptions;
/**
 * <p>Class BadPointsOfIntegralException is a exception used when user enter incorrect number of calculation points
 * </p>
 *Class BadPointsOfIntegralException is a exception used when user enter incorrect number of calculation points
 * egz.
 *Class Data.ElementUniversal checks if user entered one of the allowed values, if not allowed throw exception
 */

public class BadPointsOfIntegralException extends RuntimeException {
    /**
     * Constructs a new BadPointsOfIntegralException with the specified error message.
     *
     * @param errorMessage The error message associated with the exception.
     */
    public BadPointsOfIntegralException(String errorMessage) {
            super(errorMessage);
        }
}


