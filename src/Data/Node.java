package Data;
/**
 * <p>Class that represent the Node in the FEM
 * </p>
 * Node is a single point in a two-dimensional space used to represent its position
 */
public class Node {
    public double x;
    public double y;
    public int ID;
    public static int counter =1;
    /**
     * <p>The constructor creates a new Node with the specified coordinates.
     * The ID is automatically assigned in ascending order starting from 1.
     * </p>
     * @param x The x-coordinate of the node.
     * @param y The y-coordinate of the node.
     */
    public Node(double x, double y){
        this.x = x;
        this.y = y;
        this.ID = counter;
        counter+=1;
    }

    @Override
    public String toString() {
        return "ID: "+ ID+ " x: "+x+" y: "+y;
    }
}
