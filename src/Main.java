import Data.*;
import IO.DataImporter;
import java.io.FileNotFoundException;



public class Main {
    public static void main(String[] args) {

        // 1. Import data from file

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
        System.out.println("\nnodes: " + GlobalData.nodesNumber + "\n" + GlobalData.nodesToString());
        System.out.println("\nelements: " + GlobalData.elementsNumber + "\n" + GlobalData.elementsToString());


        // 2. Calc H HBC P C for each element
        Grid grid = new Grid(GlobalData.elements, GlobalData.nodes);
        for (Element element : grid.elements) {

            ElementUniversal elementUniversal = new ElementUniversal(3, element, GlobalData.conductivity, grid, GlobalData.alfa);

            element.setMatrixH(elementUniversal.getH());
            element.setMatrixHBC(elementUniversal.getHbc());
            element.setMatrixP(elementUniversal.getP());
            element.setMatrixC(elementUniversal.getMatrixC());

        }
        // 3. Agregate matrices to Global Matrix H and C
        SOE soe = new SOE(grid, GlobalData.nodesNumber);

        // 4. Run a simulation in time
        Simulation simulation = new Simulation();

        simulation.run(soe);
        simulation.printValues();
        simulation.printMinMax();
        simulation.export();

    }
}