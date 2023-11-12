package Functions;

public abstract class MatrixCalculator {
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

    public static Double[][] multiplyMatrixByValue(Double[][] matrix1, Double value) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;

        Double[][] result = new Double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] * value;
            }
        }
        return result;
    }

    public static void printMatrix(Double[][] matrix) {
        for (Double[] row : matrix) {
            for (Double element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
    public static Double[][] zeros(int sizeX, int sizeY){
        Double[][] temp = new Double[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j=0;j<sizeY;j++){
                temp[i][j] = 0.0;
            }
        }
        return temp;
    }
}
