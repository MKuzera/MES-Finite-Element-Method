package Data;

import java.util.ArrayList;

public abstract class GlobalData {
    public static double simulationTime;
    public static double simulationStepTime;
    public static double conductivity;
    public static double alfa;
    public static double Tot;
    public static double initialTemp;
    public static double density;
    public  static double specificHeat;
    public static int nodesNumber;
    public static int elementsNumber;
    public static ArrayList<Node> nodes;
    public static ArrayList<Element> elements;
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
    public static String nodesToString(){
       StringBuilder s = new StringBuilder();
        for (Node node: nodes) {
            s.append(node.toString()).append("\n");
        }
        return s.toString();
    }
    public static String elementsToString(){
        StringBuilder s = new StringBuilder();
        for (Element element: elements) {
            s.append(element.toString()).append("\n");
        }
        return s.toString();
    }
    public static String DCToString(){
        StringBuilder s = new StringBuilder();
        for (Integer integer: DC) {
            s.append(integer.toString()).append(" ");
        }
        return s.toString();
    }

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
