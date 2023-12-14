package IO;

import Data.Element;
import Data.GlobalData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

public class ExportToTVKFiles {
    static public void export(ArrayList<Double[]> wyniki) {
        int num = 0;
        for (Double[] wynik:wyniki) {
            num+=1;

        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append("# vtk DataFile Version 2.0\n");
        stringBuilder.append("Unstructured Grid Example\n");
        stringBuilder.append("ASCII\n");
        stringBuilder.append("DATASET UNSTRUCTURED_GRID\n");
        stringBuilder.append("\n");
        stringBuilder.append("POINTS ").append(GlobalData.nodesNumber).append(" float\n");
        for(int i = 0; i< GlobalData.nodesNumber; i++){
            stringBuilder.append(GlobalData.nodes.get(i).x).append(" ").append(GlobalData.nodes.get(i).y).append(" 0\n");
        }
        stringBuilder.append("\n");
        stringBuilder.append("CELLS ").append(GlobalData.elementsNumber).append(" ").append(GlobalData.elementsNumber * 5).append(" \n");

        ArrayList<Element> elements = GlobalData.elements;
        for(int i = 0; i< GlobalData.elementsNumber; i++){
            // potem poprawic
            stringBuilder.append("4 ").append(GlobalData.elements.get(i).toStringvminus1()).append("\n");
        }
        stringBuilder.append("\n");
        stringBuilder.append("CELL_TYPES 9\n");
        for (int i = 0; i < GlobalData.elementsNumber; i++) {
            stringBuilder.append("9\n");
        }

        stringBuilder.append("\n");
        stringBuilder.append("POINT_DATA 16\n");
        stringBuilder.append("SCALARS Temp float 1\n");
        stringBuilder.append("LOOKUP_TABLE default\n");
            for (Double d:wynik) {
                stringBuilder.append(d).append("\n");
            }


            try {

                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append("src/IO/wyniki/foo").append(num).append(".vtk");
                FileWriter fileWriter = new FileWriter(stringBuilder1.toString());

                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                String tekstDoZapisu = stringBuilder.toString();

                bufferedWriter.write(tekstDoZapisu);

                bufferedWriter.close();
                fileWriter.close();

            } catch (IOException e) {

                e.printStackTrace();
            }



        }



    }
}
