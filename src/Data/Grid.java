package Data;

import java.util.ArrayList;
/**
 *
 * <p>
 *This Class provides structure of nodes and elements used to represent a analysis area
 * </p>
 *
 * Siatka jest kluczowym elementem w MES, ponieważ umożliwia przybliżenie rozwiązania równań różniczkowych na skończoną liczbę prostych i łatwych do rozwiązania równań algebraicznych
 */
public class Grid {
    /** *List of nodes */
    public ArrayList<Node> nodes;
    /** *List of elements */
    public ArrayList<Element> elements;

    /**
     * <p>
     *Constructor creates new Grid based on list of elements and nodes
     * </p>
     * @param elements List of elements
     * @param nodes List of nodes
     */
    public Grid(ArrayList<Element> elements, ArrayList<Node> nodes){
        this.nodes = nodes;
        this.elements = elements;
    }

    /**
     * <p>
     *This method is used to find all four nodes of element in Grid's nodes, processes nodes data to create an two-dim Array with Xs and Ys
     * </p>
     * @param element element to find the nodes of
     * @return a matrix[2][4] with processed Xs and Ys from following nodes of entered element
     */
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
