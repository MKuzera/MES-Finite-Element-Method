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

        for(int c =0 ; c<4;c++){
            for(Node node:nodes){
            if(node.ID == element.ID.get(c)){
                XY[0][c] = node.x;
                XY[1][c] = node.y;
                break;
                }
            }
        }

        return XY;
    }
}
