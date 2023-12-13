import Data.*;

import Functions.MatrixCalculator;
import Gauss.GaussElimination;
import IO.DataImporter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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


           // System.out.println(element.toStringWithMatrices());



        }
       // System.out.println();
        // tu liczy globalne
        SOE soe = new SOE(grid,GlobalData.nodesNumber);

        Double[][] czesc1 = MatrixCalculator.addMatrices(soe.getGLOBALmatrixH(),soe.getGLOBALmatrixC());
        System.out.println("H + C");
        MatrixCalculator.printMatrix(czesc1);





        Double[] t0 = new Double[16];
        for(int i = 0;i<16;i++){
            t0[i]= GlobalData.initialTemp;
        }
        System.out.println("t0");
        MatrixCalculator.VECTORprint(t0);
        Double[][] matirxC = soe.getGLOBALmatrixC();
        System.out.println("C");
        MatrixCalculator.printMatrix(matirxC);

        Double[][] czesc2 = MatrixCalculator.divideMatrixByValue(matirxC,1.0); // juz wczesniej mnoze w SOE / 50.0
        System.out.println("czesc2 C/50");
        MatrixCalculator.printMatrix(czesc2);
        Double[] czesc3 = MatrixCalculator.multiply(czesc2,t0);


        Double[] czesc4 = MatrixCalculator.VECTORadd(soe.getGLOBALmatrixP(),czesc3);
        System.out.println("P + c/dt * t0");
        MatrixCalculator.VECTORprint(czesc4);

        Double[] czesc5 = {21280.4, 21601.4, 14537.1, 9873.93, 21601.3, 12945.4, 10223.4, 14537.1, 14537.1, 10223.4, 12945.4, 21601.4, 9873.93, 14537.1, 21601.4, 21280.4};

        System.out.println("WYnik");
        Double[] wynik = GaussElimination.solve(czesc1,czesc4);
        MatrixCalculator.VECTORprint(wynik);



      //  Element element = new Element(new Double[]{0.0,0.025,0.025,0.0}, new Double[]{0.0,0.0,0.025,0.025});
     //   ElementUniversal elementUniversal = new ElementUniversal(2,element,GlobalData.conductivity,grid,25.0);




    }
}