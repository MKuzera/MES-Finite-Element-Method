package Gauss;
import Functions.*;


public abstract class GaussInterpolation {

    public static double calculateX(int k, MyFunctionX f){
        GaussTable gaussTable = new GaussTable(k);
        double result = 0.0;

        for (int i =0; i<k; i++){
            result = result + (gaussTable.weights.get(i)* f.fun(gaussTable.pointsNum.get(i)));
        }
         return result;
    }

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
