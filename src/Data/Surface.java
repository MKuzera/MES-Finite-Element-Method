package Data;

import Functions.MatrixCalculator;
import Gauss.GaussTable;

import java.util.ArrayList;
import java.util.Arrays;


public class Surface {
    Double alfa;
    GaussTable gaussTable;
    Element element;
    ArrayList<Double[][]> listOfWalls;
    ArrayList<Double[][]> listOfHBC;
    ArrayList<Double[][]> listOfHBC2x2;
    ArrayList<Double> listOfDetJ;

    int calcPoints;
    public Surface(int calcPoints,Element element,Double alfa){
        this.alfa = alfa;
        listOfWalls = new ArrayList<>(4);
        listOfHBC = new ArrayList<>(4);
        listOfDetJ = new ArrayList<>(4);
        listOfHBC2x2 = new ArrayList<>(4);
        this.calcPoints =calcPoints;
        this.element = element;

        gaussTable = new GaussTable(calcPoints);
        gaussTable.initReversedWeightsAndPoints();

        initValuesInCalcPoints();
        calcListOfHBC();
        cut4x4to2x2HBCs();


    }

    private void initValuesInCalcPoints() {
        Double[][] tempSurface = new Double[calcPoints][4];
        for (int nrSurface = 0; nrSurface < 4; nrSurface++) {
            if (nrSurface == 0) {
                for (int j = 0; j < calcPoints; j++) {
                    tempSurface[j][0] = N1(gaussTable.pointsNum.get(j), -1.0);
                    tempSurface[j][1] = N2(gaussTable.pointsNum.get(j), -1.0);
                    tempSurface[j][2] = 0.0;
                    tempSurface[j][3] = 0.0;
                }

                listOfDetJ.add(calcDistance(gaussTable.pointsNum.get(0), -1.0 , gaussTable.pointsNum.get(1), -1.0));


            } else if (nrSurface == 1) {
                for (int j = 0; j < calcPoints; j++) {
                    tempSurface[j][0] = 0.0;
                    tempSurface[j][1] = N2(1.0, gaussTable.pointsNum.get(j));
                    tempSurface[j][2] = N3(1.0, gaussTable.pointsNum.get(j));
                    tempSurface[j][3] = 0.0;
                }


                listOfDetJ.add(calcDistance(1.0, gaussTable.pointsNum.get(0), 1.0, gaussTable.pointsNum.get(1)));

            } else if (nrSurface == 2) {
                for (int j = 0; j < calcPoints; j++) {
                    tempSurface[j][0] = 0.0;
                    tempSurface[j][1] = 0.0;
                    tempSurface[j][2] = N3(gaussTable.reversePointsNum.get(j), 1.0);
                    tempSurface[j][3] = N4(gaussTable.reversePointsNum.get(j), 1.0);
                }

                listOfDetJ.add(calcDistance(gaussTable.reversePointsNum.get(0), 1.0, gaussTable.reversePointsNum.get(1), 1.0));
            } else if (nrSurface == 3) {

                for (int j = 0; j < calcPoints; j++) {
                    tempSurface[j][0] = N1(-1.0, gaussTable.reversePointsNum.get(j));
                    tempSurface[j][1] = 0.0;
                    tempSurface[j][2] = 0.0;
                    tempSurface[j][3] = N4(-1.0, gaussTable.reversePointsNum.get(j));
                }

                listOfDetJ.add(calcDistance(-1.0, gaussTable.reversePointsNum.get(0), -1.0, gaussTable.reversePointsNum.get(1)));
            }

            listOfWalls.add(Arrays.stream(tempSurface)
                    .map(Double[]::clone)
                    .toArray(Double[][]::new));

            System.out.println("surface " + nrSurface);
            MatrixCalculator.printMatrix(tempSurface);
        }
    }


    private void calcListOfHBC() {

        for (int nrSurface = 0; nrSurface < 4; nrSurface++) {
            ArrayList<Double[][]> lisofTemps = new ArrayList<>(calcPoints);
            Double[][] temp;
            for (int i = 0; i < calcPoints; i++) {
                Double[][] tempSurface = listOfWalls.get(nrSurface);
                System.out.println("Matrix");
                for(int j =0; j<4;j++){
                    System.out.print(tempSurface[i][j] + " ");
                }
                System.out.println();
                temp = MatrixCalculator.multiplyVectorByItsOwnTranspose(tempSurface[i]);
                System.out.println("transpose");
                MatrixCalculator.printMatrix(temp);
                System.out.println("times weight "+ gaussTable.weights.get(i));
                temp = MatrixCalculator.multiplyMatrixByValue(temp,gaussTable.weights.get(i) * alfa);
                System.out.println("After * weight * alfa ");
                MatrixCalculator.printMatrix(temp);


                lisofTemps.add(temp);
            }

            temp = MatrixCalculator.zeros(4,4);
            for(int i = 0; i < calcPoints; i++){
                temp = MatrixCalculator.addMatrices(temp,lisofTemps.get(i));
            }
            temp = MatrixCalculator.multiplyMatrixByValue(temp,0.0125 );//listOfDetJ.get(nrSurface)
            listOfHBC.add(temp);
          }
        System.out.println("HBC");
        System.out.println("Dolna lewa gorna prawa");
        for (Double[][] x: listOfHBC) {
            MatrixCalculator.printMatrix(x);
            System.out.println();
         }

        }

    private void cut4x4to2x2HBCs() {
        Double[][] temp = new Double[2][2];
        int curX = 0,curY = 0;
        for(Double[][] hbc: listOfHBC){
            curX = 0;
            curY = 0;
            for(int i =0;i<4;i++){
                for (int j =0;j<4;j++){
                    if(hbc[i][j] != 0.0){
                        temp[curX][curY] = hbc[i][j];
                        curX+=1;
                        if(curX >1){
                            curX =0;
                            curY+=1;
                        }
                    }
                }
            }
            listOfHBC2x2.add(temp);
        }

        System.out.println("2x2");
        for (Double[][] x: listOfHBC2x2) {

            for(int i =0; i< 2;i++){
                for (int j = 0; j < 2; j++) {
                    System.out.print(x[i][j]);
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static double calcDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) / 2;
    }

    private double N1(Double ksi, Double eta){
        return 0.25*(1.0-ksi)*(1.0-eta);
    }
    private double N2(Double ksi, Double eta){
        return 0.25*(1.0+ksi)*(1.0-eta);
    }
    private double N3(Double ksi, Double eta){
        return 0.25*(1.0+ksi)*(1.0+eta);
    }
    private double N4(Double ksi, Double eta){
        return 0.25*(1.0-ksi)*(1.0+eta);
    }



}
