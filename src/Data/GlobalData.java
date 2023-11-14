package Data;

import java.util.ArrayList;
/**
 * <p>This class is a container of all data needed to start a simulation</p>
 *
 */
public abstract class GlobalData {
    /** *Simulation time */
    public static double simulationTime;
    /** *Simulation step time */
    public static double simulationStepTime;
    /** *Conductivity */
    public static double conductivity;
    /** *Alfa */
    public static double alfa;
    /** *Tot */
    public static double Tot;
    /** *initialTemp */
    public static double initialTemp;
    /** *Density */
    public static double density;
    /** *Specific Heat */
    public  static double specificHeat;
    /** *Number of nodes in the grid */
    public static int nodesNumber;
    /** *Number of elements in the grid */
    public static int elementsNumber;
    /** *List of nodes in the grid */
    public static ArrayList<Node> nodes;
    /** *List of elements in the grid */
    public static ArrayList<Element> elements;
    /** *DC */
    public static ArrayList<Integer> DC;

    public static ArrayList<Integer> getDC() {
        return DC;
    }

    public static void setDC(ArrayList<Integer> DC) {
        GlobalData.DC = DC;
    }

    public static void setNodes(ArrayList<Node> nodes){
        GlobalData.nodes = nodes;
    }
    public static void setElements(ArrayList<Element> elements){
        GlobalData.elements = elements;
    }
    public static void setSimulationTime(double simulationTime) {
        GlobalData.simulationTime = simulationTime;
    }

    public static void setSimulationStepTime(double simulationStepTime) {
        GlobalData.simulationStepTime = simulationStepTime;
    }

    public static void setConductivity(double conductivity) {
        GlobalData.conductivity = conductivity;
    }

    public static void setAlfa(double alfa) {
        GlobalData.alfa = alfa;
    }

    public static void setTot(double tot) {
        Tot = tot;
    }

    public static void setInitialTemp(double initialTemp) {
        GlobalData.initialTemp = initialTemp;
    }

    public static void setDensity(double density) {
        GlobalData.density = density;
    }

    public static void setSpecificHeat(double pecificHeat) {
        GlobalData.specificHeat = pecificHeat;
    }

    public static void setNodesNumber(int nodesNumber) {
        GlobalData.nodesNumber = nodesNumber;
    }

    public static void setElementsNumber(int elementsNumber) {
        GlobalData.elementsNumber = elementsNumber;
    }
    /**
     * <p>This method creates a string of Nodes</p>
     * @return all nodes as String
     */
    public static String nodesToString(){
       StringBuilder s = new StringBuilder();
        for (Node node: nodes) {
            s.append(node.toString()).append("\n");
        }
        return s.toString();
    }
    /**
     * <p>This method creates a string of Elements</p>
     * @return all Elements as String
     */
    public static String elementsToString(){
        StringBuilder s = new StringBuilder();
        for (Element element: elements) {
            s.append(element.toString()).append("\n");
        }
        return s.toString();
    }
    /**
     * <p>This method creates a string of DC points</p>
     * @return all points as String
     */
    public static String DCToString(){
        StringBuilder s = new StringBuilder();
        for (Integer integer: DC) {
            s.append(integer.toString()).append(" ");
        }
        return s.toString();
    }
    /**
     * <p>This method creates a string of all data that class contains</p>
     * @return string with summary of what class contains
     */
    public static String getDescription() {
        return  "simulationTime : " + simulationTime +
                "\nsimulationStepTime : " + simulationStepTime +
                "\nconductivity : " + conductivity +
                "\nalfa : " + alfa +
                "\nTot : " + Tot +
                "\ninitialTemp : " + initialTemp +
                "\ndensity : " + density +
                "\nspecificHeat : " + specificHeat +
                "\nnodesNumber : " + nodesNumber +
                "\nelementsNumber : " + elementsNumber+
                "\nDC : " + DCToString();
    }
}
