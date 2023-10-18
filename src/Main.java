

public class Main {
    public static void main(String[] args) {

        // Files name:
        // "Test1_4_4.txt"
        // "Test2_4_4_MixGrid.txt"
        // "Test3_31_31_kwadrat.txt"
        DataImporter.importData("Test1_4_4.txt");
        System.out.println("\nnodes: "+ GlobalData.nodesNumber+"\n"+GlobalData.nodesToString());
        System.out.println("\nelements: "+ GlobalData.elementsNumber+"\n"+GlobalData.elementsToString());


    }
}