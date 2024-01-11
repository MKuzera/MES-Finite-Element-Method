package Data;

import Functions.MatrixCalculator;

import java.util.ArrayList;

public class Element {
    ArrayList<Integer> ID = new ArrayList<>(4);
    Double[][] matrixH;
    Double[][] matrixHBC;
    Double[] matrixP;
    Double[][] matrixC;
    Double[] x; // needed for single element calc
    Double[] y; // needed for single element calc
    public void setMatrixC(Double[][] matrixC) {
        this.matrixC = matrixC;
    }


    public Element(int x1,int x2, int x3, int x4){
        ID.add(x1);
        ID.add(x2);
        ID.add(x3);
        ID.add(x4);
    }

    public Element(Double[] x , Double[] y){
        this.x = x;
        this.y =y;
    }
    public Double[][] createXYbasenOnPointXY(){
        Double[][] XY = new Double[2][x.length];
        XY[0] = x;
        XY[1] = y;
        return XY;
    }
    public String toString(){
        return ID.get(0) + " " + ID.get(1) + " " + ID.get(2) + " " + ID.get(3);
    }
    public String toStringvminus1(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ID.get(0)-1).append(" ");
        stringBuilder.append(ID.get(1)-1).append(" ");
        stringBuilder.append(ID.get(2)-1).append(" ");
        stringBuilder.append(ID.get(3)-1).append(" ");

        return stringBuilder.toString();
    }
    public String toStringWithMatrices(){
        return "Element:\n" + ID.get(0) + " " + ID.get(1) + " " + ID.get(2) + " " + ID.get(3) +" H:\n"+ MatrixCalculator.toStringMatrix(matrixH) + "\nHBC: \n" + MatrixCalculator.toStringMatrix(matrixHBC) + "\nP: \n"+ MatrixCalculator.VECTORtoString(matrixP);
    }
    public void setMatrixH(Double[][] H){
        this.matrixH = H;
    }

    public void setMatrixHBC(Double[][] matrixHBC) {
        this.matrixHBC = matrixHBC;
    }
    public void setMatrixP(Double[] matrixP) {
        this.matrixP = matrixP;
    }

    // klasa pomocnicza
    public Double[][] createXYListBasedOnElement(Grid grid){
        Double[][] XY = new Double[2][4];
        for(int c =0 ; c<4;c++){
            for(Node node: grid.nodes){
                if(node.ID == ID.get(c)){
                    XY[0][c] = node.x;
                    XY[1][c] = node.y;
                    break;
                }
            }
        }
        this.x = XY[0];
        this.y = XY[1];
        return XY;
    }
}