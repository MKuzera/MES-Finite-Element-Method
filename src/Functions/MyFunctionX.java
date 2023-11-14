package Functions;

/**
 * A functional interface representing a mathematical function of one variable.
 */
@FunctionalInterface
public interface MyFunctionX {
        /**
         * Calculates the function value for a given input.
         *
         * @param x The input value for which the function is calculated.
         * @return The result of applying the function to the input.
         */
        double fun(double x);
}
