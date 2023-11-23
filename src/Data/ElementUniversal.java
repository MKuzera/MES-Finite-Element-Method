package Data;

import Exceptions.BadPointsOfIntegralException;
import java.util.ArrayList;
import Functions.MatrixCalculator;
import Gauss.GaussTable;
/**
 * <p>
 * ElementUniversal is used to calculate the H matrix.
 * </p>
 *
 * The class is responsible for:
 *
 * <ul>
 *     <li>Inits ksi and eta Array for Calculating Jacobian Matrix</li>
 *     <li>Calculation of Jacobian Matrix</li>
 *     <li>Calculation of determinant of that Matrix</li>
 *     <li>Calculating dNdX and dNdY Matrices</li>
 *     <li>Calculating list of H matrices for each calculation point</li>
 *     <li>Calculation final H matrix</li>
 * </ul>
 */
public class ElementUniversal {

    private Integer pointsOfIntegral;
    private Integer arraySizeBasedOnPointsOfIntegral;
    private Element element;
    private Double [] ksiArray;
    private Double [] etaArray;
    private Double[][] dNdKsi;
    private Double [][] dNdEta;
    private Double[] dets;
    private ArrayList<Double[][]> HMatrixesList;
    private Double[][] H;
    private Double[][] dNdX;
    private Double[][] dNdY;
    private Double[] x;
    private Double[] y;
    private Grid grid;
    private Double kt;
    private Double alfa;
    private Double[][] Hbc;



    //private Surface[] = new Surface; 4

    /**
     *
     * @return matrix H
     */
    public Double[][] getH() {
        return H;
    }

    /**
     * <p>
     * Constructor creates a new Element Universal and Calculates matrix H for given parameters
     * @param pointsOfIntegral number of points of integral
     * @param kt parameter
     * @param element element that will be calculated
     * @param grid the global grid used in program
     * </p>
     */
    public ElementUniversal(int pointsOfIntegral,Element element,Double kt, Grid grid,Double alfa){

        if(pointsOfIntegral ==2 || pointsOfIntegral == 3 || pointsOfIntegral ==4) {

                Double[][] XY = element.createXYListBasedOnElement(grid);
                this.grid = grid;
                //Double[][] XY = element.createXYbasenOnPointXY();

                this.element = element;

                this.kt =kt;
                this.alfa= alfa;
                this.x = XY[0];
                this.y =  XY[1];
                this.pointsOfIntegral = pointsOfIntegral;
                this.arraySizeBasedOnPointsOfIntegral = pointsOfIntegral * pointsOfIntegral;

                // inits empty matrices (ksi/eta Array, dNdksi dNdeta, dets)
                initMatrices();

                // inits eta and ksi Arrays used for calc Jakobian
                initKsiEtaCalcPointsArray();

                // inits the dNdKsi and dNdEta matrix
                initdNdKsidNdEta();

                // inits the Jacobian Matrix, calculates the dets[] and dNdX dNdY matrices
                initJakobian();

                // inits the HMatrixesList and calculates H for each calc point
                calcListofMatrixesH();

                // inits the H matrix and calculates the H matrix by multiplying
                // following Matrices H from the HMatrixesList by appropriate weights
                calculateFinalHforElement();

                // prints the final matrix H
           //     MatrixCalculator.printMatrix(H);

                calcListofHBC();
        }
        else{
                throw new BadPointsOfIntegralException("Avaible Options: 2,3,4, entered: " + pointsOfIntegral);
        }

    }

    private void calcListofHBC() {
        Surface surface = new Surface(pointsOfIntegral,element,alfa,grid);
    }

    public void initMatrices() {
        dNdKsi = new Double[arraySizeBasedOnPointsOfIntegral][4];
        dNdEta = new Double[arraySizeBasedOnPointsOfIntegral][4];
        ksiArray = new Double[arraySizeBasedOnPointsOfIntegral];
        etaArray = new Double[arraySizeBasedOnPointsOfIntegral];
        dets = new Double[arraySizeBasedOnPointsOfIntegral];
        Hbc = new Double[4][4];
    }
    private void initKsiEtaCalcPointsArray() {
        if(pointsOfIntegral == 2){
            Double f = Math.sqrt(1.0/3.0);
            etaArray = new Double[]{-f,-f,f,f};
            ksiArray = new Double[]{-f,f,-f,f};

        } else if (pointsOfIntegral == 3) {
            Double f = Math.sqrt(3.0/5.0);

            ksiArray = new Double[]{-f,-f,-f,0.0,0.0,0.0,f,f,f};
            etaArray = new Double[]{-f,0.0,f,-f,0.0,f,-f,0.0,f};

        } else if (pointsOfIntegral == 4) {

            Double f1= Math.sqrt(3.0/7.0 + (2.0/7.0  *Math.sqrt(6.0/5.0)));
            Double f2= Math.sqrt(3.0/7.0 - (2.0/7.0  *Math.sqrt(6.0/5.0)));
            etaArray = new Double[]{-f1,-f1,-f1,-f1,-f2,-f2,-f2,-f2,f2,f2,f2,f2,f1,f1,f1,f1,};
            ksiArray = new Double[]{-f1,-f2,f2,f1,-f1,-f2,f2,f1,-f1,-f2,f2,f1,-f1,-f2,f2,f1};
        }

    }

    private void initdNdKsidNdEta() {
        // innits the dNdKsi and dNdEta matrix
        for(int i =0;i<arraySizeBasedOnPointsOfIntegral;i++) {
            dNdEta[i][0] = dN1dS(etaArray[i]);
            dNdEta[i][1] = dN2dS(etaArray[i]);
            dNdEta[i][2] = dN3dS(etaArray[i]);
            dNdEta[i][3] = dN4dS(etaArray[i]);

            dNdKsi[i][0]  = dN1dn(ksiArray[i]);
            dNdKsi[i][1]  = dN2dn(ksiArray[i]);
            dNdKsi[i][2]  = dN3dn(ksiArray[i]);
            dNdKsi[i][3]  = dN4dn(ksiArray[i]);
        }
    }

    private void initJakobian() {
        // utowrzenie macierzy dNdX dNdY o wielkosci liczbapunktcalkowania^2 i 4 (4 jest zawsze stałe)
        dNdX = new Double[arraySizeBasedOnPointsOfIntegral][4];
        dNdY = new Double[arraySizeBasedOnPointsOfIntegral][4];
        // macierz jakobiego
        Double[][] matrx = new Double[2][2];

       // System.out.println("dNdETA");
     //   MatrixCalculator.printMatrix(dNdEta);
     //   System.out.println("dNdKSI");
      //  MatrixCalculator.printMatrix(dNdKsi);

        for (int i =0; i< arraySizeBasedOnPointsOfIntegral;i++) {

            // wyznaczenie danych do macierzy jakobiego

            Double dXdKsi = dNdKsi[i][0] * x[0] + dNdKsi[i][1] * x[1] + dNdKsi[i][2] * x[2] + dNdKsi[i][3] * x[3];
            Double dXdEta = dNdEta[i][0] * x[0] + dNdEta[i][1] * x[1] + dNdEta[i][2] * x[2] + dNdEta[i][3] * x[3];
            Double dYdKsi = dNdKsi[i][0] * y[0] + dNdKsi[i][1] * y[1] + dNdKsi[i][2] * y[2] + dNdKsi[i][3] * y[3];
            Double dYdEta = dNdEta[i][0] * y[0] + dNdEta[i][1] * y[1] + dNdEta[i][2] * y[2] + dNdEta[i][3] * y[3];



            Double[][] matrix2 = new Double[2][2];

            // macierz jakobiego
            matrix2[0][0] = dXdEta;
            matrix2[0][1] = dYdEta;
            matrix2[1][0] = dXdKsi;
            matrix2[1][1] = dYdKsi;

            // odwrocona macierz jakobiego
            matrx[0][0] = dYdKsi;
            matrx[0][1] = -dYdEta;
            matrx[1][0] = -dXdKsi;
            matrx[1][1] = dXdEta;

            // dodaje wyznacznik do tabeli wyznacznikow
            dets[i] = MatrixCalculator.detMatrx2x2(matrx);

            // mnozy kazdy element odwroconej macierzy jakobiego przez 1/det
            matrx = MatrixCalculator.multiplyMatrixByValue(matrx,1.0/dets[i]);

            // oblicza dNdX dNdY dla danego punktu calkowania
            for (int k = 0; k < 4; k++) {
                dNdX[i][k] = (matrx[1][1]*dNdKsi[i][k] + matrx[1][0]*dNdEta[i][k]) ;
                dNdY[i][k] = (matrx[0][1]*dNdKsi[i][k] + matrx[0][0]*dNdEta[i][k]) ;
           }


        }
//        System.out.println("DNDX");
//        MatrixCalculator.printMatrix(dNdX);
//        System.out.println("DNDY");
//        MatrixCalculator.printMatrix(dNdY);
//        System.out.println();
    }

    private void calcListofMatrixesH(){

        // tworzy liste macierzy H (1 macierz H dla 1 punktu)
        HMatrixesList = new ArrayList<>(arraySizeBasedOnPointsOfIntegral);
        Double[] tempInputX = new Double[4];
        Double[] tempInputY = new Double[4];
        Double[][] matrixH;

        for(int i =0; i<arraySizeBasedOnPointsOfIntegral;i++){

            for (int j = 0; j < 4; j++) {
                tempInputX[j] = dNdX[i][j];
                tempInputY[j] = dNdY[i][j];
            }


            Double[][] tempDNDX = MatrixCalculator.multiplyVectorByItsOwnTranspose(tempInputX);
            Double[][] tempDNDY = MatrixCalculator.multiplyVectorByItsOwnTranspose(tempInputY);

            matrixH = MatrixCalculator.addMatrices(tempDNDX,tempDNDY);

            matrixH = MatrixCalculator.multiplyMatrixByValue(matrixH,kt*dets[i]);

//            System.out.println("H matrix nr" + i);
//            MatrixCalculator.printMatrix(matrixH);
            HMatrixesList.add(matrixH);
        }

    }

    private void calculateFinalHforElement(){
        GaussTable gaussTable = new GaussTable(pointsOfIntegral);
        int weightX = 0;
        int weightY = 0;

        H = MatrixCalculator.zeros(4,4);
        Double[][] temp = MatrixCalculator.zeros(4,4);

        for (int k =0; k<arraySizeBasedOnPointsOfIntegral; k++) {

            temp = MatrixCalculator.multiplyMatrixByValue(HMatrixesList.get(k),(
                    gaussTable.weights.get(weightX)*gaussTable.weights.get(weightY)));

            H = MatrixCalculator.addMatrices(H,temp);

            weightX+=1;
            if(weightX == pointsOfIntegral){
                weightY+=1;
                weightX=0;
            }
        }

    }



    private double dN1dS(Double eta){
        return -0.25*(1.0-eta);
    }
    private double dN2dS(Double eta){
        return 0.25*(1.0-eta);
    }
    private double dN3dS(Double eta){
        return 0.25*(1.0+eta);
    }
    private double dN4dS(Double eta){
        return -0.25*(1.0+eta);
    }

    // η - eta
    private double dN1dn(Double ksi){
        return -0.25*(1.0-ksi);
    }
    private double dN2dn(Double ksi){
        return -0.25*(1.0+ksi);
    }
    private double dN3dn(Double ksi){ return 0.25*(1.0+ksi);}
    private double dN4dn(Double ksi){
        return 0.25*(1.0-ksi);
    }
}

