package Data;

import Functions.MatrixCalculator;
import Gauss.GaussElimination;
import IO.ExportToTVKFiles;

import java.util.ArrayList;

public class Simulation {
    private ArrayList<Double[]> TValuesInSimTime;
    public Simulation(){

    }
    public void run(SOE soe){

        TValuesInSimTime = new ArrayList<>();

            Double[] t0 = new Double[GlobalData.nodesNumber];
            for (int i = 0; i < GlobalData.nodesNumber; i++) {
                t0[i] = GlobalData.initialTemp;
            }

        TValuesInSimTime.add(t0);

            int iter= 0;

            for(int j = 0; j<GlobalData.simulationTime; j+=GlobalData.simulationStepTime) {
                System.out.println(j);
                Double[][] czesc1 = MatrixCalculator.addMatrices(soe.getGLOBALmatrixH(), soe.getGLOBALmatrixC());

                t0 = MatrixCalculator.VECTORzeros(GlobalData.nodesNumber);
                Double[] prevT;
                prevT = TValuesInSimTime.get(iter).clone();


                for (int i = 0; i < GlobalData.nodesNumber; i++) {
                    t0[i] = prevT[i];
                }

                Double[][] matirxC = soe.getGLOBALmatrixC();
                Double[][] czesc2= matirxC;
                Double[] czesc3 = MatrixCalculator.multiply(czesc2, t0);
                Double[] czesc4 = MatrixCalculator.VECTORadd(soe.getGLOBALmatrixP(), czesc3);
                Double[] wynik = GaussElimination.solve(czesc1, czesc4);

                TValuesInSimTime.add(wynik);
                iter+=1;

            }
        TValuesInSimTime.remove(0); // 0 element to z t0 jako vector

    }
    public void printValues(){
        int k =0;
        for (Double[] d: TValuesInSimTime){

            System.out.println("\n");
            k++;

            for(int i = 0; i<GlobalData.nodesNumber;i++){
                System.out.print(d[i] + " ");
            }
        }

    }
    public void export(){
        ExportToTVKFiles.export(TValuesInSimTime);
    }
}
