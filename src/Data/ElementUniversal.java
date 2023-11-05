package Data;

import java.util.ArrayList;

public class ElementUniversal {
    // podczas liczenia pochodnych za ksi i eta wstawiamy punkty calkowania
    // ?
    Double[][] meshKsi;
    Double [][] meshEta;
    private Integer pointsOfIntegral;
    private Integer arraySizeBasedOnPointsOfIntegral;

    // pointsOfIntegral - punkty calkowania
    // mesh - siatka

    public ElementUniversal(int pointsOfIntegral){
        this.pointsOfIntegral =pointsOfIntegral;
        this.arraySizeBasedOnPointsOfIntegral = pointsOfIntegral*pointsOfIntegral;
        meshEta = new Double[4][arraySizeBasedOnPointsOfIntegral];
        meshKsi = new Double[4][arraySizeBasedOnPointsOfIntegral];
    }

    public void initEtaKsi(){

    }







    // polowa gotowych funkcji:

    // ξ - ksi

    private double dN1dS(Double S){
        return -0.25*(1-S);
    }
    private double dN2dS(Double S){
        return 0.25*(1-S);
    }
    private double dN3dS(Double S){
        return 0.25*(1+S);
    }
    private double dN4dS(Double S){
        return -0.25*(1+S);
    }


    // η - eta
    private double dN1dn(Double n){
        return -0.25*(1-n);
    }
    private double dN2dn(Double n){
        return -0.25*(1+n);
    }
    private double dN3dn(Double n){ return 0.25*(1+n);}
    private double dN4dn(Double n){
        return 0.25*(1-n);
    }


}

