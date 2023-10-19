package Gauss;
import Functions.*;
public class GaussInterpolation {
    private static GaussTable gaussTable;

    public static double calculateX(int k, MyFunctionX f){
        gaussTable = new GaussTable(k);
        double result = 0.0;

        for (int i =0; i<k; i++){
            result = result + (gaussTable.weights.get(i)* f.funkcja(gaussTable.pointsNum.get(i)));
        }
         return result;
    }

    public static double calculateXY(int k, MyFunctionXY f){
        gaussTable = new GaussTable(k);
        double result = 0.0;

        for (int i =0; i<k; i++){
            for(int j = 0; j<k;j++) {
                result = result + (gaussTable.weights.get(i) *gaussTable.weights.get(j) * f.funkcja(gaussTable.pointsNum.get(i),gaussTable.pointsNum.get(j)));
            }
        }
        return result;
    }


}
