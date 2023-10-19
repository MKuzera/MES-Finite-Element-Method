package Data;

import java.util.ArrayList;

public class Element {
    ArrayList<Integer> ID = new ArrayList<>(4);
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
