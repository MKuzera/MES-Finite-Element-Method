package Functions;
/**
 * A functional interface representing a mathematical function of two variables.
 */
@FunctionalInterface
public interface MyFunctionXY  {/**
 /**
 * Calculates the function value for given inputs.
 *
 * @param x The first input value for the function.
 * @param y The second input value for the function.
 * @return The result of applying the function to the inputs.
 */
    double fun(double x, double y);
}
