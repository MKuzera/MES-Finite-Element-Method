package IO;
import Data.*;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * <p>
 * This class provides methods needed for importing data from a given file and processing data into the Data.GlobalData class
 * </p>
 * Currently works only for the following files:
 * <ul>
 *   <li>Test1_4_4.txt</li>
 *   <li>Test2_4_4_MixGrid.txt</li>
 *   <li>Test3_31_31_kwadrat.txt</li>
 * </ul>
 */
public abstract class DataImporter {


    private static ArrayList<Node> nodes;
    private static ArrayList<Element> elements;
    /**
     * <p>
     * This method imports data from the given filename and exports processed data into Data.GlobalData.
     * </p>
     * @param fileName The given fileName.
     * @throws FileNotFoundException when the file is not found.
     * @throws RuntimeException when there is a problem with IO.
     */
    public static void importData(String fileName) throws FileNotFoundException {

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
            throw new FileNotFoundException("File not found : " + fileName);
        } catch (IOException e) {
            throw new RuntimeException("IO exception at IO.DataImporter.java");
        }

        // process the data
        implementGlobalData(new ArrayList<>(allLines.subList(0,10)));
        initializeNodes();
        addNodes(new ArrayList<>(allLines.subList(11,11 + (GlobalData.nodesNumber))));
        initializeElements();
        addElements(new ArrayList<>(allLines.subList(12 + GlobalData.nodesNumber,12 + GlobalData.nodesNumber + GlobalData.elementsNumber)));
        initDC(allLines.get(allLines.size() -1));
        setDCtoNodes();
    }
    /**
     * <p>
     *This method initialize ArrayList of Nodes based on nodesNumber from Data.GlobalData
     * </p>
     */
    private static void initializeNodes(){
        nodes = new ArrayList<>(GlobalData.nodesNumber);
    }
    /**
     * <p>
     *This method initialize ArrayList of Elements based on elementNumber from Data.GlobalData
     * </p>
     */
    private static void initializeElements(){
        elements = new ArrayList<>(GlobalData.elementsNumber);
    }
    /**
     * <p>
     *This method process lines to elements for ArrayList and exports List to Data.GlobalData as elements
     * </p>
     * @param strings ArrayList of lines (each line contains data for one element)
     */
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
    /**
     * <p>
     *This method process lines to nodes for ArrayList and exports List to Data.GlobalData as nodes
     * </p>
     * @param strings ArrayList of lines (each line contains data for one node)
     */
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

    /**
     * <p>
     *This method process lines to data used in Data.GlobalData
     * </p>
     * @param lines list of strings to be processed
     */
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
    /**
     * <p>
     *Return first int found in line(String)
     * </p>
     * @param line line in which we find int
     */
    private static int getIntNumberFromLine(String line){
        String numberOnly= line.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }
    /**
     * <p>
     *Return first double found in line(String)
     * </p>
     * @param line line in which we find double
     */
    private static double getDoubleNumberFromLine(String line){
        String numberOnly= line.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numberOnly);
    }
    /**
     * <p>
     *This method process String line containing a list of DC points to an ArrayList and exports this Array to Data.GlobalData
     * </p>
     * @param line Given line with list of DC like (1 2 3 4...)
     */
    private static void  initDC(String line){
        String[] lineSplited = line.split(",");
        ArrayList<Integer> temp = new ArrayList<>();
        for (String s:lineSplited) {
            s.trim();
            temp.add(Integer.parseInt(s.trim()));
        }
        GlobalData.setDC(temp);
    }

    private static void setDCtoNodes(){
        for (Node node: GlobalData.nodes ) {
            if(GlobalData.DC.contains(node.ID)){
                node.DC = 1;
            }
        }

    }


}
