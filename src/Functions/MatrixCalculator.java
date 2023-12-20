
package Functions;

import java.nio.DoubleBuffer;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>MatrixCalculator is a simple calculator used to calculate <b>two-dimensional matrices</b> in an application.
 * </p>
 */
public abstract class MatrixCalculator {
    /**
     * <p> Method that multiply Double vector by its transpose
     * </p>
     *
     * @param vector is a one dim Double Matrix
     * @return returns two dim Double Matrix
     */
    public static Double[][] multiplyVectorByItsOwnTranspose(Double[] vector) {
        int length = vector.length;

        Double[][] result = new Double[length][length];

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                result[i][j] = vector[i] * vector[j];
            }
        }

        return result;
    }
    /**
     * <p> Method that adds 2 two-dimensional Double matrices
     * </p>
     *
     * @param matrix1 First Double matrix
     * @param matrix2 Second Double matrix
     * @return returns two-dimensional Double matrix: sum of matrix1 and matrix2
     */
    public static Double[][] addMatrices(Double[][] matrix1, Double[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;

        Double[][] result = MatrixCalculator.zeros(rows,cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }
    /**
     * <p> Method that multiplies two-dimensional matrix by value
     * </p>
     *
     * @param matrix Matrix to be multiplied
     * @param value Value to multiply by
     * @return returns two-dimensional Double matrix: multiply of matrix by value
     */
    public static Double[][] multiplyMatrixByValue(Double[][] matrix, Double value) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        Double[][] result = new Double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix[i][j] * value;
            }
        }
        return result;
    }
    /**
     * <p> Method that displays in the console matrix
     * </p>
     *
     * @param matrix Matrix to be displayed
     */
    public static void printMatrix(Double[][] matrix) {
        for (Double[] row : matrix) {
            for (Double element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
    public static String toStringMatrix(Double[][] matrix){
        StringBuilder s = new StringBuilder();
        for (Double[] row : matrix) {
            for (Double element : row) {
                s.append(element + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }



                                      /**
     * <p> Method that creates and returns two-dimensional matrix filled with (Double) 0.0 values
     * </p>
     *
     * @param sizeX number of rows
     * @param sizeY number of columns
     * @return returns two-dimensional matrix of given size filled with (Double) 0.0 values
     */
    public static Double[][] zeros(int sizeX, int sizeY){
        Double[][] temp = new Double[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j=0;j<sizeY;j++){
                temp[i][j] = 0.0;
            }
        }
        return temp;
    }



    /**
     * <p> Method that calculates the determinant of two-dimensional matrix
     * </p>
     *
     * @param matrx Two-Dimensional matrix
     *
     * @return returns the determinant of the given matrix
     */
    public static Double detMatrx2x2(Double[][] matrx){
        return matrx[0][0] * matrx[1][1] - matrx[1][0] * matrx[0][1];
    }

    public static Double[] VECTORzeros(int sizeX){
        Double[] temp = new Double[sizeX];
        for (int i = 0; i < sizeX; i++) {
                temp[i] = 0.0;
        }
        return temp;
    }

    public static void VECTORprint(Double[] vec){
        for (Double d:vec) {
            System.out.print(d + " ");
        }

    }
    public static String VECTORtoString(Double[] vec){
        StringBuilder s = new StringBuilder();
        for (Double d:vec) {
            s.append(d + " ");
        }
        return s.toString();
    }
    public static Double[][] divideMatrixByValue(Double[][] matrix, Double value ){
        int rows = matrix.length;
        int cols = matrix[0].length;

        Double[][] nMatrix = MatrixCalculator.zeros(rows,cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                nMatrix[i][j] = matrix[i][j] / value;
            }
        }
        return nMatrix;
    }
    public static Double[] multiplyMatrixByVector(Double[][] matrix, Double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;



        if (cols != vector.length) {
            throw new IllegalArgumentException("Matrix columns must be equal to vector length");
        }

        Double[] result = MatrixCalculator.VECTORzeros(rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }

        return result;
    }

    public static Double[] multiply(Double[][] matrix, Double[] vector) {
        int rows = matrix.length;
        int columns = matrix[0].length;


        Double[] result = new Double[rows];

        for (int row = 0; row < rows; row++) {
            Double sum = 0.0;
            for (int column = 0; column < columns; column++) {

                sum += matrix[row][column]
                        * vector[column];
            }
            result[row] = sum;
        }
        return result;
    }




    public static Double[] VECTORadd(Double[] vec1, Double[] vec2){
        int len = vec1.length;

        Double[] result = MatrixCalculator.VECTORzeros(len);
        for (int i = 0; i < len; i++) {
            result[i] = vec1[i] + vec2[i];
        }
        return result;
    }

    public static Double[] VECTORsub(Double[] vec1, Double[] vec2){
        int len = vec1.length;

        Double[] result = MatrixCalculator.VECTORzeros(len);
        for (int i = 0; i < len; i++) {
            result[i] = vec1[i] - vec2[i];
        }
        return result;
    }
    public static Double[] VECTORmultiply(Double[] vec1,Double value){
        int len = vec1.length;

        Double[] result = MatrixCalculator.VECTORzeros(len);
        for (int i = 0; i < len; i++) {
            result[i] = vec1[i] * value;
        }
        return result;
    }
    public static Double findMax(Double[] tablica) {

        Double maksimum = tablica[0];

        for (int i = 1; i < tablica.length; i++) {
            if (tablica[i] > maksimum) {
                maksimum = tablica[i];
            }
        }

        return maksimum;
    }

   public static Double findMin(Double[] tablica) {

        Double minimum = tablica[0];

        for (int i = 1; i < tablica.length; i++) {
            if (tablica[i] < minimum) {
                minimum = tablica[i];
            }
        }

        return minimum;
    }

}
