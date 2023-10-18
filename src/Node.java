public class Node {
    public double x;
    public double y;
    public Node(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x: "+x+" y: "+y;
    }
}
