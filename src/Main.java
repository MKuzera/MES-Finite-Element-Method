import Data.*;

import Functions.MatrixCalculator;
import Gauss.GaussElimination;
import IO.DataImporter;
import IO.ExportToTVKFiles;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {


        // Files name:
        // "src\IO\Test1_4_4.txt"
        // "src\IO\Test2_4_4_MixGrid.txt"
        // "src\IO\Test3_31_31_kwadrat.txt"


        try {
            DataImporter.importData("src\\IO\\Test2_4_4_MixGrid.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(GlobalData.getDescription());
        System.out.println("\nnodes: "+ GlobalData.nodesNumber+"\n"+GlobalData.nodesToString());
        System.out.println("\nelements: "+ GlobalData.elementsNumber+"\n"+GlobalData.elementsToString());

        Grid grid = new Grid(GlobalData.elements, GlobalData.nodes);


      //  MyFunctionX myFunctionX = x -> 5*x * x + 3 * x + 6;
      //  System.out.println(GaussInterpolation.calculateX(3, myFunctionX));

      //  MyFunctionXY myFunctionXY = (x, y) -> 5*x * x *y*y + 3*x*y +6;
      //  System.out.println(GaussInterpolation.calculateXY(3, myFunctionXY));

    //    Double[] x = new Double[]{0.0 , 0.025 , 0.025,0.0};
     //   Double[] y = new Double[]{0.0 , 0.0 , 0.025,0.025};
     //   ElementUniversal   elementUniversal = new ElementUniversal(2,x,y,30.0);



        for (Element element: grid.elements) {

            ElementUniversal elementUniversal = new ElementUniversal(2,element,GlobalData.conductivity,grid,GlobalData.alfa);

            element.setMatrixH(elementUniversal.getH());
            element.setMatrixHBC(elementUniversal.getHbc());
            element.setMatrixP(elementUniversal.getP());
            element.setMatrixC(elementUniversal.getMatrixC());

//
//            System.out.println("\nHBC element " + element.toString());
//            MatrixCalculator.printMatrix(elementUniversal.getHbc());
//
//
//            System.out.println("P \n");
//            MatrixCalculator.VECTORprint(elementUniversal.getP());

           // System.out.println(element.toStringWithMatrices());



        }
       // System.out.println();
        // tu liczy globalne
        SOE soe = new SOE(grid,GlobalData.nodesNumber);

        if (true) {





        ArrayList<Double[]> wyniki = new ArrayList<>();

        Double[] t0 = new Double[16];
        for (int i = 0; i < 16; i++) {
            t0[i] = GlobalData.initialTemp;
        }

        wyniki.add(t0);
        MatrixCalculator.VECTORprint(t0);
        System.out.println();
        MatrixCalculator.VECTORprint(wyniki.get(0));
        System.out.println();
        int iter= 0;
        for(int j = 0; j<GlobalData.simulationTime; j+=GlobalData.simulationStepTime) {
            Double[][] czesc1 = MatrixCalculator.addMatrices(soe.getGLOBALmatrixH(), soe.getGLOBALmatrixC());

            System.out.println("H + C");
            MatrixCalculator.printMatrix(czesc1);


            t0 = MatrixCalculator.VECTORzeros(16);
            Double[] prevT;
            prevT = wyniki.get(iter).clone();
//            System.out.println("x");
//            MatrixCalculator.VECTORprint(t0);
            System.out.println(t0.length);
            System.out.println(prevT.length);

            for (int i = 0; i < 16; i++) {
                System.out.println("XDD " + i);
                t0[i] = prevT[i];
            }
//        System.out.println("t0");
//        MatrixCalculator.VECTORprint(t0);
            Double[][] matirxC = soe.getGLOBALmatrixC();
//        System.out.println("C");
 //       MatrixCalculator.printMatrix(matirxC);
            Double[][] czesc2= matirxC;
          //  Double[][] czesc2 = MatrixCalculator.divideMatrixByValue(matirxC, 1.0); // juz wczesniej mnoze w SOE / 50.0
//        System.out.println("czesc2 C/50");
//        MatrixCalculator.printMatrix(czesc2);
            Double[] czesc3 = MatrixCalculator.multiply(czesc2, t0);

            System.out.println("MATRIXC/50 * wektorTEMP");
            MatrixCalculator.VECTORprint(czesc3);
            // P JEST DOBRZE
            System.out.println("P ");
            MatrixCalculator.VECTORprint(soe.getGLOBALmatrixP());

            Double[] czesc4 = MatrixCalculator.VECTORadd(soe.getGLOBALmatrixP(), czesc3);




//          System.out.println("\nP + c/dt * t0");
//          MatrixCalculator.VECTORprint(czesc4);
//        System.out.println("WYnik");
            Double[] wynik = GaussElimination.solve(czesc1, czesc4);
//            MatrixCalculator.VECTORprint(wynik);

            wyniki.add(wynik);
            iter+=1;

        }

        int k =0;
        for (Double[] d: wyniki ){

            System.out.println("\n");
            k++;

            for(int i = 0; i<16;i++){
                System.out.print(d[i] + " ");
            }
        }
        wyniki.remove(0);
        ExportToTVKFiles.export(wyniki);

      //  Element element = new Element(new Double[]{0.0,0.025,0.025,0.0}, new Double[]{0.0,0.0,0.025,0.025});
     //   ElementUniversal elementUniversal = new ElementUniversal(2,element,GlobalData.conductivity,grid,25.0);



        }
    }
}