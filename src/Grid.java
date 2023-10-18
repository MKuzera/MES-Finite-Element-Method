import java.util.ArrayList;

public class Grid {
    public ArrayList<Node> Nodes;
    public ArrayList<Element> Elements = new ArrayList<>();

    public Grid(int nodesNumber, int elementsNumber){
        Nodes = new ArrayList<>(nodesNumber);
        Elements = new ArrayList<>(elementsNumber);
    }
}
