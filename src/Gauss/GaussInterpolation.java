package Gauss;
import Functions.*;

/**
 * <p>GaussInterpolation is a class that is used to interpolate function in one or two dimensional space
 * </p>
 */
public abstract class GaussInterpolation {
    /**
     * Calculates the interpolation of a function in one-dimensional space using Gauss quadrature.
     *
     * @param k The number of points in the Gauss quadrature.
     * @param f The one-dimensional function to interpolate.
     * @return The interpolated value.
     */
    public static double calculateX(int k, MyFunctionX f){
        GaussTable gaussTable = new GaussTable(k);
        double result = 0.0;

        for (int i =0; i<k; i++){
            result = result + (gaussTable.weights.get(i)* f.fun(gaussTable.pointsNum.get(i)));
        }
         return result;
    }
    /**
     * Calculates the interpolation of a function in two-dimensional space using Gauss quadrature.
     *
     * @param k The number of points in the Gauss quadrature.
     * @param f The two-dimensional function to interpolate.
     * @return The interpolated value.
     */
    public static double calculateXY(int k, MyFunctionXY f){
        GaussTable gaussTable = new GaussTable(k);
        double result = 0.0;

        for (int i =0; i<k; i++){
            for(int j = 0; j<k;j++) {
                result = result + (gaussTable.weights.get(i) *gaussTable.weights.get(j) * f.fun(gaussTable.pointsNum.get(i),gaussTable.pointsNum.get(j)));
            }
        }
        return result;
    }


}
