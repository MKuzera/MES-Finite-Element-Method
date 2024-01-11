package Data;

public class Node {
    public double x;
    public double y;
    public double DC;
    public int ID;
    public static int counter =1;

    public Node(double x, double y){
        this.x = x;
        this.y = y;
        this.ID = counter;
        counter+=1;
        this.DC = 0;
    }
    void setBC(double dc){
        this.DC = dc;
    }

    @Override
    public String toString() {
        return "ID: "+ ID+ " x: "+x+" y: "+y+" DC: "+DC ;
    }
}
