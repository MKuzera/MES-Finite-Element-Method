package Data;

import Exceptions.BadPointsOfIntegralException;
import java.util.ArrayList;
import java.util.Arrays;

import Functions.MatrixCalculator;
import Gauss.GaussTable;


public class ElementUniversal {

    private Integer pointsOfIntegral;
    private Integer arraySizeBasedOnPointsOfIntegral;
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





    public ElementUniversal(int pointsOfIntegral,Double[] x , Double[] y){

        if(pointsOfIntegral ==2 || pointsOfIntegral == 3 || pointsOfIntegral ==4) {
            
                this.x = x;
                this.y = y;
                this.pointsOfIntegral = pointsOfIntegral;

                initMatrices();

                initKsiEtaCalcPointsArray();

                initdNdKsidNdEta();

              //  System.out.println("dndksi");
              //  MatrixCalculator.printMatrix(dNdKsi);
              //  System.out.println("dndeta");
              //  MatrixCalculator.printMatrix(dNdEta);

                initJakobian();

                System.out.println("dndX");
                MatrixCalculator.printMatrix(dNdX);
                System.out.println("dndy");
                MatrixCalculator.printMatrix(dNdY);


                calcListofMatrixesH();
                calculateFinalHforElement();

                System.out.println("FINAL H");
                MatrixCalculator.printMatrix(H);
        }
        else{
                throw new BadPointsOfIntegralException("Avaible Options: 2,3,4, entered: " + pointsOfIntegral);
        }

    }

    private void initMatrices() {
        arraySizeBasedOnPointsOfIntegral = pointsOfIntegral * pointsOfIntegral;
        dNdKsi = new Double[4][arraySizeBasedOnPointsOfIntegral];
        dNdEta = new Double[4][arraySizeBasedOnPointsOfIntegral];
        ksiArray = new Double[arraySizeBasedOnPointsOfIntegral];
        etaArray = new Double[arraySizeBasedOnPointsOfIntegral];
        dets = new Double[arraySizeBasedOnPointsOfIntegral];
    }

    private void initJakobian() {
        dNdX = new Double[4][4];
        dNdY = new Double[4][4];
        Double[][] matrx = new Double[2][2];

        for (int i =0; i< 4;i++) {

            Double dXdKsi = dNdKsi[i][0] * x[0] + dNdKsi[i][1] * x[1] + dNdKsi[i][2] * x[2] + dNdKsi[i][3] * x[3];
            Double dXdEta = dNdEta[i][0] * x[0] + dNdEta[i][1] * x[1] + dNdEta[i][2] * x[2] + dNdEta[i][3] * x[3];
            Double dYdKsi = dNdKsi[i][0] * y[0] + dNdKsi[i][1] * y[1] + dNdKsi[i][2] * y[2] + dNdKsi[i][3] * y[3];
            Double dYdEta = dNdEta[i][0] * y[0] + dNdEta[i][1] * y[1] + dNdEta[i][2] * y[2] + dNdEta[i][3] * y[3];



            matrx[0][0] = dYdKsi;
            matrx[0][1] = -dYdEta;
            matrx[1][0] = -dXdKsi;
            matrx[1][1] = dXdEta;
            System.out.println("------- PUNKT" + (i+1));
            System.out.println("matrix");
            MatrixCalculator.printMatrix(matrx);
            Double det = matrx[0][0] * matrx[1][1] - matrx[1][0] * matrx[0][1];
            dets[i] = det;
            System.out.println("det:" +dets[i]);

            Double oneByDet;
            oneByDet = 1.0 / dets[i];

            System.out.println("1/det");
            System.out.println(oneByDet);

            MatrixCalculator.printMatrix(matrx);

            for (int k = 0; k < 4; k++) {
               dNdY[i][k] = (matrx[0][0]*dNdKsi[i][k] + matrx[0][1]*dNdEta[i][k]) * oneByDet;
               dNdX[i][k] = (matrx[1][0]*dNdKsi[i][k] + matrx[1][1]*dNdEta[i][k]) * oneByDet;
           }

        }
    }

    private void calcListofMatrixesH(){

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
            matrixH = MatrixCalculator.multiplyMatrixByValue(matrixH,25.0); // k(t)
            matrixH = MatrixCalculator.multiplyMatrixByValue(matrixH,dets[i]);
            HMatrixesList.add(matrixH);
        }

    }

    private void calculateFinalHforElement(){
        GaussTable gaussTable = new GaussTable(pointsOfIntegral);
        int weightX = 0;
        int weightY = 0;

        H = MatrixCalculator.zeros(arraySizeBasedOnPointsOfIntegral,arraySizeBasedOnPointsOfIntegral);
        Double[][] temp = MatrixCalculator.zeros(arraySizeBasedOnPointsOfIntegral,arraySizeBasedOnPointsOfIntegral);

        for (int k =0; k<arraySizeBasedOnPointsOfIntegral; k++) {

            temp = MatrixCalculator.multiplyMatrixByValue(HMatrixesList.get(k),
                    gaussTable.weights.get(weightX)*gaussTable.weights.get(weightY));
            H = MatrixCalculator.addMatrices(H,temp);
            weightX+=1;
            if(weightX == pointsOfIntegral){
                weightY+=1;
                weightX=0;
            }
        }

    }


    // ξ - ksi
    // n - eta
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


    private void initKsiEtaCalcPointsArray() {
        // innits eta and ksi Arrays used for calc Jakobian
        if(pointsOfIntegral == 2){
            Double f = Math.sqrt(1.0/3.0);
            etaArray = new Double[]{-f,-f,f,f};
            ksiArray = new Double[]{-f,f,-f,f};

        } else if (pointsOfIntegral == 3) {
            Double f = Math.sqrt(3.0/5.0);
            System.out.println(f);
            ksiArray = new Double[]{-f,-f,-f,0.0,0.0,0.0,f,f,f};
            etaArray = new Double[]{-f,0.0,f,-f,0.0,f,-f,0.0,f};

        } else if (pointsOfIntegral == 4) {
            Double f1= Math.sqrt(3.0/7.0 + (2.0/7.0  *Math.sqrt(6.0/5.0)));
            Double f2= Math.sqrt(3.0/7.0 - (2.0/7.0  *Math.sqrt(6.0/5.0)));
            etaArray = new Double[]{-f1,-f1,-f1,-f1,-f2,-f2,-f2,-f2,f1,f1,f1,f1,f2,f2,f2,f2};
            ksiArray = new Double[]{-f1,-f2,f2,f1,-f1,-f2,f2,f1,-f1,-f2,f2,f1,-f1,-f2,f2,f1};
        }

    }

    // ξ - ksi

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
    private double dN3dn(Double ksi){
        return 0.25*(1.0+ksi);}
    private double dN4dn(Double ksi){
        return 0.25*(1.0-ksi);
    }


}

