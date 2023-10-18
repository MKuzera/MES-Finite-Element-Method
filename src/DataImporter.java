import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataImporter {


    private static ArrayList<Node> nodes;
    private static ArrayList<Element> elements;
    public static void importData(String fileName){

        ArrayList<String> allLines = new ArrayList<>();
        try(
                FileReader fileReader = new FileReader(fileName);
                BufferedReader reader = new BufferedReader(fileReader))
        {
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                allLines.add(nextLine);
            }

        } catch (FileNotFoundException e) {
            throw new Exceptions.FileNotFoundException("File not found : " + fileName);
        } catch (IOException e) {
            throw new RuntimeException("IO exception at DataImporter.java");
        }

        implementGlobalData(new ArrayList<>(allLines.subList(0,10)));
        initializeNodes();
        addNodes(new ArrayList<>(allLines.subList(11,11 + (GlobalData.nodesNumber))));
        initializeElements();
        addElements(new ArrayList<>(allLines.subList(12 + GlobalData.nodesNumber,12 + GlobalData.nodesNumber + GlobalData.elementsNumber)));
    }

    private static void addElements(ArrayList<String> strings) {
        for (String string:strings) {
            String[] line = string.split(",");
            int i = 0;
            int x1 = 0;
            int x2 = 0;
            int x3 = 0;
            int x4 = 0;
            for (String lines: line) {
                if(i == 1){
                    x1 = Integer.parseInt(lines.trim());
                }
                if(i == 2){
                    x2 = Integer.parseInt(lines.trim());
                }
                if(i == 3){
                    x3 = Integer.parseInt(lines.trim());
                }
                if(i == 4){
                    x4 = Integer.parseInt(lines.trim());
                    elements.add(new Element(x1,x2,x3,x4));
                }
                i++;
            }
        }
        GlobalData.setElements(elements);
    }

    private static void initializeNodes(){
        nodes = new ArrayList<>(GlobalData.nodesNumber);
    }
    private static void initializeElements(){
        elements = new ArrayList<>(GlobalData.elementsNumber);
    }
    private static void addNodes(ArrayList<String> strings) {
        for (String string:strings) {
            String[] line = string.split(",");
            int i = 0;
            double x = 0;
            double y = 0;
            for (String lines: line) {
                if(i == 1){
                    x = Double.parseDouble(lines);
                }
                if(i == 2)
                {
                    y = Double.parseDouble(lines);
                    nodes.add(new Node(x,y));
                }
                i++;
            }
        }
        GlobalData.setNodes(nodes);
    }


    private static void implementGlobalData(ArrayList<String> lines){

        GlobalData.setSimulationTime(getDoubleNumberFromLine(lines.get(0)));
        GlobalData.setSimulationStepTime(getDoubleNumberFromLine(lines.get(1)));
        GlobalData.setConductivity(getDoubleNumberFromLine(lines.get(2)));
        GlobalData.setAlfa(getDoubleNumberFromLine(lines.get(3)));
        GlobalData.setTot(getDoubleNumberFromLine(lines.get(4)));
        GlobalData.setInitialTemp(getDoubleNumberFromLine(lines.get(5)));
        GlobalData.setDensity(getDoubleNumberFromLine(lines.get(6)));
        GlobalData.setSpecificHeat(getDoubleNumberFromLine(lines.get(7)));
        GlobalData.setNodesNumber(getIntNumberFromLine(lines.get(8)));
        GlobalData.setElementsNumber(getIntNumberFromLine(lines.get(9)));

    }
    private static int getIntNumberFromLine(String line){
        String numberOnly= line.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }
    private static double getDoubleNumberFromLine(String line){
        String numberOnly= line.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numberOnly);
    }



}
