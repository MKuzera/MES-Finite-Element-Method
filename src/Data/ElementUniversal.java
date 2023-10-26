package Data;

import java.util.ArrayList;

public class ElementUniversal {
    Double[][] mesh;
    // pointsOfIntegral - punkty calkowania
    // mesh - siatka? tak google tlumaczy
    public ElementUniversal(int pointsOfIntegral){
        mesh = new Double[4][pointsOfIntegral*pointsOfIntegral];
    }

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
    private double dN3dn(Double n){
        return 0.25*(1+n);
    }
    private double dN4dn(Double n){
        return 0.25*(1-n);
    }
}

