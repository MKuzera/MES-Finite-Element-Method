package Data;

import Functions.MatrixCalculator;

import java.util.ArrayList;

public class SOE {
    Grid grid;
    Element element;
    int size;
    Double[][] matrixSOE;
    Double[] matrixP;
    ArrayList<Integer> IDs;



    public SOE(Grid grid, int size){

        this.grid = grid;
        this.size = size;

        this.matrixSOE = MatrixCalculator.zeros(size,size);
        this.matrixP = MatrixCalculator.VECTORzeros(size);



        calcMatrixSOE();
        System.out.println("\nP ");
        MatrixCalculator.VECTORprint(matrixP);
        System.out.println("\nH ");
        MatrixCalculator.printMatrix(matrixSOE);

    }

    private void calcMatrixSOE() {


        for (Element element: grid.elements) {

            Double[][] sumH = MatrixCalculator.addMatrices(element.matrixH,element.matrixHBC);
            IDs = element.ID;
            // calc P
            for (int i = 0 ;i < 4 ; i++){
                matrixP[IDs.get(i)-1] += element.matrixP[i];
            }

            // calc H

            for (int i = 0 ;i < 4 ; i++){
                for(int j=0; j<4; j++){
                    matrixSOE[IDs.get(i)-1][IDs.get(j)-1] += element.matrixH[i][j];
                }
            }
            // poprawic XD


        }



    }


}
