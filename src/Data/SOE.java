package Data;

import Functions.MatrixCalculator;

import java.util.ArrayList;

public class SOE {
    Grid grid;
    Element element;
    int size;
    Double[][] GLOBALmatrixH; //
    // H + HBC ! uwzglednione

    Double[] GLOBALmatrixP; // BC warunek brzegowy
    Double[][] GLOBALmatrixC; //
    // C / deltaT UWZGLEDNIONE!!!
    ArrayList<Integer> IDs;



    public SOE(Grid grid, int size){

        this.grid = grid;
        this.size = size;

        this.GLOBALmatrixH = MatrixCalculator.zeros(size,size);
        this.GLOBALmatrixC = MatrixCalculator.zeros(size,size);
        this.GLOBALmatrixP = MatrixCalculator.VECTORzeros(size);



        calcGlobalHandP(); // agregacja macierzy
        System.out.println("\nP ");
        MatrixCalculator.VECTORprint(GLOBALmatrixP);
        System.out.println("\nH ");
        MatrixCalculator.printMatrix(GLOBALmatrixH);
        System.out.println("\nC ");
        MatrixCalculator.printMatrix(GLOBALmatrixC);

    }

    public Double[][] getGLOBALmatrixH() {
        return GLOBALmatrixH;
    }

    public Double[] getGLOBALmatrixP() {
        return GLOBALmatrixP;
    }

    public Double[][] getGLOBALmatrixC() {
        return GLOBALmatrixC;
    }

    private void calcGlobalHandP() {


        for (Element element: grid.elements) {

            IDs = element.ID;
          //  Double[][] tempH = MatrixCalculator.zeros(4,4);
          //  Double[] tempP = MatrixCalculator.VECTORzeros(4);
            // calc P

            for (int i = 0 ;i < 4 ; i++){
            //    tempP[i]+= element.matrixP[i];

                // agregacja macierzy w przestrzeni 1D
                GLOBALmatrixP[IDs.get(i)-1] += element.matrixP[i];
            }

            // calc H

            Double[][] tempHandHBC = MatrixCalculator.addMatrices(element.matrixH,element.matrixHBC);
            Double[][] tempCDivDeltaT = MatrixCalculator.divideMatrixByValue(element.matrixC,50.0);
            for (int i = 0 ;i < 4 ; i++){
                for(int j=0; j<4; j++){
                 //   tempH[i][j] +=  tempHandHBC[i][j];

                    // agregacja macierzy w przestrzeni 2D
                    // odrazu z ukladu globalnego czyli
                    // element z node np 1 2 6 5 na -> ID1 ID2 ID3 ID4 i wstawia
                    GLOBALmatrixH[IDs.get(i)-1][IDs.get(j)-1] += tempHandHBC[i][j];
                    //agregacja macierzy C na macierz Globalna C
                    GLOBALmatrixC[IDs.get(i)-1][IDs.get(j)-1] += tempCDivDeltaT[i][j];
                }
            }
         //   System.out.println();
         //   MatrixCalculator.printMatrix(tempH);



        }



    }


}
