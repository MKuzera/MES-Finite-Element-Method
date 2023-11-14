package Data;

import java.util.ArrayList;
/**
 * <p>Class that represent the Element in the FEM
 * </p>
 * Element is a geometrical representation of shape of the analysis area
 */
public class Element {
    ArrayList<Integer> ID = new ArrayList<>(4);
    /**
     * <p>The Constructor creates a new Element with assigned IDs values of nodes that Element contains
     * </p>
     * @param x1 ID of the First Node
     * @param x2 ID of the Second Node
     * @param x3 ID of the Third Node
     * @param x4 ID of the Fourth Node
     */
    public Element(int x1,int x2, int x3, int x4){
        ID.add(x1);
        ID.add(x2);
        ID.add(x3);
        ID.add(x4);
    }
    public String toString(){
        return ID.get(0) + " " + ID.get(1) + " " + ID.get(2) + " " + ID.get(3);
    }
}
