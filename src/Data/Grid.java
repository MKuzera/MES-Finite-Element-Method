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


}
