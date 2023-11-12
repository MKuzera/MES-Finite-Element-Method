package Data;

import java.util.ArrayList;

public class Grid {
    public ArrayList<Node> nodes;
    public ArrayList<Element> elements;

    public Grid(ArrayList<Element> elements, ArrayList<Node> nodes){
        this.nodes = nodes;
        this.elements = elements;
    }
    public Double[][] createXYListBasedOnElement(Element element){
        Double XY[][] = new Double[2][4];
        int x =0;
        for (Node node: nodes) {
            if(node.ID == element.ID.get(0) || node.ID == element.ID.get(1) || node.ID == element.ID.get(2) || node.ID == element.ID.get(3)){
                XY[0][x] = node.x;
                XY[1][x] = node.y;
                x+=1;
                if(x == 4)break;
            }
        }
        return XY;
    }
}
