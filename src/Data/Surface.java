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
    ArrayList<Double[][]> listOfHBC2x2;
    ArrayList<Double> listOfDetJ;
    Grid grid;
    Double[][] finalHBC;
    Double[] finalP;

    int calcPoints;
    public Surface(int calcPoints,Element element,Double alfa,Grid grid){
        this.alfa = alfa;
        this.grid =grid;
        listOfWalls = new ArrayList<>(4);

        listOfDetJ = new ArrayList<>(4);
        listOfHBC2x2 = new ArrayList<>(4);
        this.calcPoints =calcPoints;
        this.element = element;

        gaussTable = new GaussTable(calcPoints);
        gaussTable.initReversedWeightsAndPoints();

        finalHBC = MatrixCalculator.zeros(4,4);
        finalP = MatrixCalculator.VECTORzeros(4);
        initValuesInCalcPoints();
        initDetJ();
        calcHBCandP();
        //cut4x4to2x2HBCs();
   //     System.out.println("HBC");
    //    MatrixCalculator.printMatrix(finalHBC);
    //    System.out.println("P");
    //    MatrixCalculator.VECTORprint(finalP);
   //     System.out.println();



    }

    private void initDetJ() {
       // element.createXYbasenOnPointXY();

        Double[] x = element.x;
        Double[] y = element.y;

        listOfDetJ.add(calcDistance(x[0],y[0],x[1],y[1]));
        listOfDetJ.add(calcDistance(x[1],y[1],x[2],y[2]));
        listOfDetJ.add(calcDistance(x[2],y[2],x[3],y[3]));
        listOfDetJ.add(calcDistance(x[3],y[3],x[0],y[0]));
//        System.out.println(element.toString());
//        System.out.println("DETJ");
//        System.out.print(listOfDetJ + " \n");


    }

    private void initValuesInCalcPoints() {
        Double[][] tempSurface = new Double[calcPoints][4];
        for (int nrSurface = 0; nrSurface < 4; nrSurface++) {
            if (nrSurface == 0) {
                for (int j = 0; j < calcPoints; j++) {
                    // dolna sciana
                    tempSurface[j][0] = N1(gaussTable.pointsNum.get(j), -1.0);
                    tempSurface[j][1] = N2(gaussTable.pointsNum.get(j), -1.0);
                    tempSurface[j][2] = 0.0;
                    tempSurface[j][3] = 0.0;

                }
            } else if (nrSurface == 1) {
                for (int j = 0; j < calcPoints; j++) {
                    //prawa sciana
                    tempSurface[j][0] = 0.0;
                    tempSurface[j][1] = N2(1.0, gaussTable.pointsNum.get(j));
                    tempSurface[j][2] = N3(1.0, gaussTable.pointsNum.get(j));
                    tempSurface[j][3] = 0.0;
                }
            } else if (nrSurface == 2) {
                for (int j = 0; j < calcPoints; j++) {
                    // gorna sciana
                    tempSurface[j][0] = 0.0;
                    tempSurface[j][1] = 0.0;
                    tempSurface[j][2] = N3(gaussTable.reversePointsNum.get(j), 1.0);
                    tempSurface[j][3] = N4(gaussTable.reversePointsNum.get(j), 1.0);
                }
            } else if (nrSurface == 3) {

                for (int j = 0; j < calcPoints; j++) {
                    // lewa sciana
                    tempSurface[j][0] = N1(-1.0, gaussTable.reversePointsNum.get(j));
                    tempSurface[j][1] = 0.0;
                    tempSurface[j][2] = 0.0;
                    tempSurface[j][3] = N4(-1.0, gaussTable.reversePointsNum.get(j));
                }

            }

            listOfWalls.add(Arrays.stream(tempSurface)
                    .map(Double[]::clone)
                    .toArray(Double[][]::new));

         //   System.out.println("surface " + nrSurface);
         //   MatrixCalculator.printMatrix(tempSurface);



        }
    }


    private void calcHBCandP() {

        for (int nrSurface = 0; nrSurface < 4; nrSurface++) {


            int node1 = element.ID.get(nrSurface);
            int tempnumber = nrSurface + 1;
            if (tempnumber > 3) tempnumber = 0;
            int node2 = element.ID.get(tempnumber);

            if (GlobalData.DC.contains(node1) && GlobalData.DC.contains(node2)) {
                ArrayList<Double[][]> lisofTempsHBC = new ArrayList<>(calcPoints);
                ArrayList<Double[]> lisofTempsP = new ArrayList<>(calcPoints);

                for (int i = 0; i < calcPoints; i++) {
                    Double[][] temp =  listOfWalls.get(nrSurface);
                    temp = MatrixCalculator.multiplyVectorByItsOwnTranspose(temp[i]);
                    temp = MatrixCalculator.multiplyMatrixByValue(temp, gaussTable.weights.get(i) * alfa);
                    lisofTempsHBC.add(temp);

                    Double[] tempP;
                    temp = listOfWalls.get(nrSurface);
                    tempP = MatrixCalculator.VECTORmultiply(temp[i],gaussTable.weights.get(i) * GlobalData.Tot * alfa);
                    lisofTempsP.add(tempP);
                }

                Double[][] temp = MatrixCalculator.zeros(4, 4);
                Double[] tempP = MatrixCalculator.VECTORzeros(4);

                for (int i = 0; i < calcPoints; i++) {
                    temp = MatrixCalculator.addMatrices(temp, lisofTempsHBC.get(i));
                    tempP = MatrixCalculator.VECTORadd(tempP,lisofTempsP.get(i));
                }


                temp = MatrixCalculator.multiplyMatrixByValue(temp, listOfDetJ.get(nrSurface));
                tempP = MatrixCalculator.VECTORmultiply(tempP,listOfDetJ.get(nrSurface));

                finalHBC = MatrixCalculator.addMatrices(finalHBC, temp);
                finalP = MatrixCalculator.VECTORadd(finalP,tempP);

            }


        }
    }

    public Double[][] getFinalHBC() {
        return finalHBC;
    }

    public Double[] getFinalP() {
        return finalP;
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
