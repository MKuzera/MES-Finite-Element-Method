package Data;

import Exceptions.BadPointsOfIntegralException;

import java.util.ArrayList;

public class ElementUniversal {
    // podczas liczenia pochodnych za ksi i eta wstawiamy punkty calkowania
    Double[][] dNdKsi;
    Double [][] dNdEta;
    private Integer pointsOfIntegral;
    private Integer arraySizeBasedOnPointsOfIntegral;
    private Double[][] matrixH;

    public Double[][] getdNdKsi() {
        return dNdKsi;
    }

    public void setdNdKsi(Double[][] dNdKsi) {
        this.dNdKsi = dNdKsi;
    }

    public Double[][] getdNdEta() {
        return dNdEta;
    }

    public void setdNdEta(Double[][] dNdEta) {
        this.dNdEta = dNdEta;
    }

    private Double [] ksiArray;
    private Double [] etaArray;

    // pointsOfIntegral - punkty calkowania

    public ElementUniversal(int pointsOfIntegral){

        if(pointsOfIntegral ==2 || pointsOfIntegral == 3 || pointsOfIntegral ==4) {

                this.pointsOfIntegral = pointsOfIntegral;
                this.arraySizeBasedOnPointsOfIntegral = pointsOfIntegral * pointsOfIntegral;
                dNdKsi = new Double[4][arraySizeBasedOnPointsOfIntegral];
                dNdEta = new Double[4][arraySizeBasedOnPointsOfIntegral];
                ksiArray = new Double[arraySizeBasedOnPointsOfIntegral];
                etaArray = new Double[arraySizeBasedOnPointsOfIntegral];

                initKsiEtaCalcPointsArray();
                initdNdKsidNdEta();
                calcMatrixH();
        }
        else{
                throw new BadPointsOfIntegralException("Avaible Options: 2,3,4, entered: " + pointsOfIntegral);
        }

    }

    private void calcMatrixH(){
        Double[] x = new Double[]{0.0 , 0.025 , 0.025,0.0};
        Double[] y = new Double[]{0.0 , 0.0 , 0.025,0.025};

        Double[][] dNdX = new Double[4][4];
        Double[][] dNdY = new Double[4][4];

        for (int i =0; i< 4;i++) {

            Double dXdKsi = dNdKsi[i][0] * x[0] + dNdKsi[i][1] * x[1] + dNdKsi[i][2] * x[2] + dNdKsi[i][3] * x[3];
            Double dXdEta = dNdEta[i][0] * x[0] + dNdEta[i][1] * x[1] + dNdEta[i][2] * x[2] + dNdEta[i][3] * x[3];
            Double dYdKsi = dNdKsi[i][0] * y[0] + dNdKsi[i][1] * y[1] + dNdKsi[i][2] * y[2] + dNdKsi[i][3] * y[3];
            Double dYdEta = dNdEta[i][0] * y[0] + dNdEta[i][1] * y[1] + dNdEta[i][2] * y[2] + dNdEta[i][3] * y[3];

            Double[][] matrx = new Double[2][2];
            matrx[0][0] = dXdKsi;
            matrx[0][1] = dYdKsi;
            matrx[1][0] = dXdEta;
            matrx[1][1] = dYdEta;

            System.out.println("XXXXXXXXXXXXXXX");
            System.out.println(matrx[0][0] + " " + matrx[0][1]);
            System.out.println(matrx[1][0] + " " + matrx[1][1]);

            Double det = matrx[0][0] * matrx[1][1] - matrx[1][0] * matrx[0][1];
            Double oneByDet = 1.0 / det;
            System.out.println(oneByDet);

            matrx[0][0] = matrx[0][0] * oneByDet;
            matrx[0][1] = matrx[0][1] * oneByDet;
            matrx[1][0] = matrx[1][0] * oneByDet;
            matrx[1][1] = matrx[1][1] * oneByDet;

            System.out.println(matrx[0][0] + " " + matrx[0][1]);
            System.out.println(matrx[1][0] + " " + matrx[1][1]);



            for (int k = 0; k < 4; k++) {
                dNdX[i][k] = matrx[0][0]*dNdKsi[i][k] + matrx[0][1]*dNdEta[i][k];
                dNdY[i][k] = matrx[1][0]*dNdKsi[i][k] + matrx[1][1]*dNdEta[i][k];
            }


        }


        System.out.println("Wynik dNdx");

        for (int j = 0; j < 4; j++) {
            for (int l = 0; l < 4; l++) {
                System.out.print(dNdX[j][l] + " ");
            }
            System.out.println();
        }
        System.out.println("dndy");
        for (int j = 0; j < 4; j++) {
            for (int l = 0; l < 4; l++) {
                System.out.print(dNdY[j][l] + " ");
            }
            System.out.println();
        }

        for(int i =0; i<4;i++){
            matrixTimesTransMatrix(dNdX[0]);
        }


    }

    private void matrixTimesTransMatrix(Double[] matrix) {
        // to do
        for (Double d:matrix) {

        }
    }


    private void initdNdKsidNdEta() {

            for(int i =0;i<arraySizeBasedOnPointsOfIntegral;i++) {


                dNdKsi[i][0] = dN1dS(etaArray[i]);
                dNdKsi[i][1] = dN2dS(etaArray[i]);
                dNdKsi[i][2] = dN3dS(etaArray[i]);
                dNdKsi[i][3] = dN4dS(etaArray[i]);

                dNdEta[i][0]  = dN1dn(ksiArray[i]);
                dNdEta[i][1]  = dN2dn(ksiArray[i]);
                dNdEta[i][2]  = dN3dn(ksiArray[i]);
                dNdEta[i][3]  = dN4dn(ksiArray[i]);



            }
    }
    public void printKsiEta(){


        System.out.println("KsiArray\n");

        for (int i = 0; i < ksiArray.length; i++) {
                System.out.print(ksiArray[i] + " ");

        }
        System.out.println("EtaArray\n");

        for (int i = 0; i < etaArray.length; i++) {
            System.out.print(etaArray[i] + " ");

        }



        System.out.println("\nKsi\n");

        for (int i = 0; i < dNdKsi.length; i++) {
            for (int j = 0; j < dNdKsi[i].length; j++) {
                System.out.print(dNdKsi[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("\nEta\n");
        for (int i = 0; i < dNdEta.length; i++) {
            for (int j = 0; j < dNdEta[i].length; j++) {
                System.out.print(dNdEta[i][j] + " ");
            }
            System.out.println();
        }



    }

    public void printKsiEta2(){


        System.out.println("KsiArray\n");

        for (int i = 0; i < ksiArray.length; i++) {
            System.out.print(ksiArray[i] + " ");

        }
        System.out.println("EtaArray\n");

        for (int i = 0; i < etaArray.length; i++) {
            System.out.print(etaArray[i] + " ");

        }



        System.out.println("\nKsi\n");

        for (int i = 0; i < dNdKsi.length; i++) {
            for (int j = 0; j < dNdKsi[i].length; j++) {
                System.out.print(dNdKsi[j][i] + " ");
            }
            System.out.println();
        }

        System.out.println("\nEta\n");
        for (int i = 0; i < dNdEta.length; i++) {
            for (int j = 0; j < dNdEta[i].length; j++) {
                System.out.print(dNdEta[j][i] + " ");
            }
            System.out.println();
        }



    }

    //  ξ - ksi
    // n - eta
    private void initKsiEtaCalcPointsArray() {
        // punkty całkowania z GaussTable
        if(pointsOfIntegral == 2){
            // SPRAWDZONE - poprawne w wynikami
            Double f = Math.sqrt(1.0/3.0);
            etaArray = new Double[]{-f,-f,f,f};
            ksiArray = new Double[]{-f,f,-f,f};

        } else if (pointsOfIntegral == 3) {
            // SPRAWDZONE - poprawne z wynikami
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


    // polowa gotowych funkcji:

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

