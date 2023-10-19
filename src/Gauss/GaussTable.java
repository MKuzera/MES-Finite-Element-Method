package Gauss;

import Exceptions.BadDataException;

import java.util.ArrayList;

public class GaussTable {

    public ArrayList<Double> pointsNum = new ArrayList<Double>();
    public ArrayList<Double> weights= new ArrayList<Double>();
    private int numberOfPointsVar;

    public GaussTable(int numberOfPoints){
        numberOfPointsVar = numberOfPoints;
        if(numberOfPoints < 1 || numberOfPoints > 4){
            throw new BadDataException("Wrong input - at Gauss.GaussTable.java");
        }
        else if(numberOfPoints == 1){
            pointsNum.add(0.0);
            weights.add(2.0);
        }
        else if(numberOfPoints == 2){
            pointsNum.add(-Math.sqrt(1.0/3.0));
            pointsNum.add(+Math.sqrt(1.0/3.0));

            weights.add(1.0);
            weights.add(1.0);
        }
        else if(numberOfPoints == 3){
            pointsNum.add(-Math.sqrt(3.0/5.0));
            pointsNum.add(0.0);
            pointsNum.add(Math.sqrt(3.0/5.0));

            weights.add(5.0/9.0);
            weights.add(8.0/9.0);
            weights.add(5.0/9.0);
        }

        else if(numberOfPoints == 4){
            pointsNum.add(-Math.sqrt(3.0/7.0 - (2.0/7.0)*(Math.sqrt(6.0/5.0))));
            pointsNum.add(-Math.sqrt(3.0/7.0 + (2.0/7.0)*(Math.sqrt(6.0/5.0))));
            pointsNum.add(Math.sqrt(3.0/7.0 + (2.0/7.0)*(Math.sqrt(6.0/5.0))));
            pointsNum.add(Math.sqrt(3.0/7.0 - (2.0/7.0)*(Math.sqrt(6.0/5.0))));

            weights.add((18.0 + Math.sqrt(30.0))/ 36.0);
            weights.add((18.0 - Math.sqrt(30.0))/ 36.0);
            weights.add((18.0 - Math.sqrt(30.0))/ 36.0);
            weights.add((18.0 + Math.sqrt(30.0))/ 36.0);
        }

    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Number of Points: ");
        s.append(numberOfPointsVar);
        s.append("\nPoints: \n");
        for (Double num: pointsNum) {
            s.append(num.toString()).append("\n");
        }
        s.append("Weights: \n");
        for (Double num: weights) {
            s.append(num.toString()).append("\n");
        }



        return s.toString();
    }

}
