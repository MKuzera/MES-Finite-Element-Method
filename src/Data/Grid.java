package Data;

import java.util.ArrayList;

public class Grid {

    public ArrayList<Node> nodes;

    public ArrayList<Element> elements;


    public Grid(ArrayList<Element> elements, ArrayList<Node> nodes){
        this.nodes = nodes;
        this.elements = elements;
    }


}
