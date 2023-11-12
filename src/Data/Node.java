package Data;

public class Node {
    public double x;
    public double y;
    public int ID;
    public static int counter =1;
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
