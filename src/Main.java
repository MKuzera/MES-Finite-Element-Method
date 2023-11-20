import Data.*;

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
            DataImporter.importData("src\\IO\\Test1_4_4.txt");
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

        Double[] x = new Double[]{0.0 , 0.025 , 0.025,0.0};
        Double[] y = new Double[]{0.0 , 0.0 , 0.025,0.025};
     //   ElementUniversal   elementUniversal = new ElementUniversal(2,x,y,30.0);

        ArrayList<ElementUniversal> elementUniversals = new ArrayList<>();
        for (Element element: grid.elements) {
            Double[][] XY = grid.createXYListBasedOnElement(element);
            x = XY[0];
            y = XY[1];

            System.out.println();
            ElementUniversal elementUniversal = new ElementUniversal(4,x,y,GlobalData.conductivity);

            elementUniversals.add(elementUniversal);
            break;
        }



    }
}