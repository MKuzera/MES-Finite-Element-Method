package Data;

import Exceptions.BadPointsOfIntegralException;
import java.util.ArrayList;
import Functions.MatrixCalculator;
import Gauss.GaussTable;
/**
 * <p>
 * ElementUniversal is used to calculate the H matrix.
 * </p>
 *
 * The class is responsible for:
 *
 * <ul>
 *     <li>Inits ksi and eta Array for Calculating Jacobian Matrix</li>
 *     <li>Calculation of Jacobian Matrix</li>
 *     <li>Calculation of determinant of that Matrix</li>
 *     <li>Calculating dNdX and dNdY Matrices</li>
 *     <li>Calculating list of H matrices for each calculation point</li>
 *     <li>Calculation final H matrix</li>
 * </ul>
 */
public class ElementUniversal {

    private Integer pointsOfIntegral;
    private Integer arraySizeBasedOnPointsOfIntegral;
    private Element element;
    private Double [] ksiArray;
    private Double [] etaArray;
    private Double[][] dNdKsi;
    private Double [][] dNdEta;
    private Double[] dets;
    private ArrayList<Double[][]> HMatrixesList;
    private Double[][] H;
    private Double[][] dNdX;
    private Double[][] dNdY;
    private Double[] x;
    private Double[] y;
    private Grid grid;
    private Double kt;
    private Double alfa;
    private Double[][] Hbc;
    private Double[] P;
    private Double[][] matrixC;

    public Double[][] getMatrixC() {
        return matrixC;
    }


    public Double[][] getH() {
        return H;
    }

    /**
     * <p>
     * Constructor creates a new Element Universal and Calculates matrix H for given parameters
     * @param pointsOfIntegral number of points of integral
     * @param kt parameter
     * @param element element that will be calculated
     * @param grid the global grid used in program
     * </p>
     */
    public ElementUniversal(int pointsOfIntegral,Element element,Double kt, Grid grid,Double alfa){

        if(pointsOfIntegral ==2 || pointsOfIntegral == 3 || pointsOfIntegral ==4) {

                Double[][] XY = element.createXYListBasedOnElement(grid);
                this.grid = grid;
                this.element = element;
                this.kt =kt;
                this.alfa= alfa;
                this.x = XY[0];
                this.y =  XY[1];
                this.pointsOfIntegral = pointsOfIntegral;
                this.arraySizeBasedOnPointsOfIntegral = pointsOfIntegral * pointsOfIntegral;


                // inits empty matrices (ksi/eta Array, dNdksi dNdeta, dets)
                initMatrices();

                // inits eta and ksi Arrays used for calc Jakobian
                initKsiEtaCalcPointsArray();

                // inits the dNdKsi and dNdEta matrix
                initdNdKsidNdEta();

                // inits the Jacobian Matrix, calculates the dets[] and dNdX dNdY matrices
                initJakobianAndInterpolate();

                // inits the HMatrixesList and calculates H for each calc point
                calcListofMatrixesH();

                // inits the H matrix and calculates the H matrix by multiplying
                // following Matrices H from the HMatrixesList by appropriate weights
                calculateFinalHforElement();

                // calc the HBC and P matrices for this element
                calcHBCandP();

               // calc the C matrix for this element
                calcMatrixC();

        }
        else{
                throw new BadPointsOfIntegralException("Avaible Options: 2,3,4, entered: " + pointsOfIntegral);
        }

    }

    // inicjuje puste tablice
    public void initMatrices() {
        dNdKsi = new Double[arraySizeBasedOnPointsOfIntegral][4];
        dNdEta = new Double[arraySizeBasedOnPointsOfIntegral][4];
        ksiArray = new Double[arraySizeBasedOnPointsOfIntegral];
        etaArray = new Double[arraySizeBasedOnPointsOfIntegral];
        dets = new Double[arraySizeBasedOnPointsOfIntegral];
        Hbc = new Double[4][4];
        P = new Double[4];
    }
    // inicjuje wartości tablic etaArray oraz ksiArray. Określają one wartości współrzędnych
    // ksi oraz eta w układzie 2d.
    // Wartości f / (f1 f2) są to wartości punktów całkowania z tablicy gausa.
    // to czy ma - czy + zależy czy leży powyżej/poniżej swojej osi czy może jest równe 0
    // Potrzebne jest to po to aby przejsc z ukladu globalnego xy na uklad ksi/eta gdzie
    // Bede mógł policzyć całke z powierzchni.
    private void initKsiEtaCalcPointsArray() {
        if(pointsOfIntegral == 2){
            Double f = Math.sqrt(1.0/3.0);
            etaArray = new Double[]{-f,-f,f,f};
            ksiArray = new Double[]{-f,f,f,-f};

        } else if (pointsOfIntegral == 3) {
            Double f = Math.sqrt(3.0/5.0);

            ksiArray = new Double[]{-f,-f,-f,0.0,0.0,0.0,f,f,f};
            etaArray = new Double[]{-f,0.0,f,-f,0.0,f,-f,0.0,f};

        } else if (pointsOfIntegral == 4) {

            Double f1= Math.sqrt(3.0/7.0 + (2.0/7.0  *Math.sqrt(6.0/5.0)));
            Double f2= Math.sqrt(3.0/7.0 - (2.0/7.0  *Math.sqrt(6.0/5.0)));
            etaArray = new Double[]{-f1,-f1,-f1,-f1,-f2,-f2,-f2,-f2,f2,f2,f2,f2,f1,f1,f1,f1,};
            ksiArray = new Double[]{-f1,-f2,f2,f1,-f1,-f2,f2,f1,-f1,-f2,f2,f1,-f1,-f2,f2,f1};
        }

    }
    // dNdEta oraz dNdKsi są to tablice zawierające wartości funkcji kształtu dla danego punktu całkowania.
    // dNdEta[0],dNdKsi[0] zawieraja wartości funkcji kształtu dla 1 punktu całkowania w układzie
    // funkcje ksztaltu dla tego elementu istanieja tylko w ukladzie lokalnym
    // potrzebujemy je w celu interpolowania wspolrzednych (nie mozemy calkowac dla ukladu globalnego x,y gdzie nie wiemy
    // jaki punkt całkowania mamy uzyc)
    private void initdNdKsidNdEta() {
        // innits the dNdKsi and dNdEta matrix
        for(int i =0;i<arraySizeBasedOnPointsOfIntegral;i++) {
            dNdEta[i][0] = dN1dS(etaArray[i]);
            dNdEta[i][1] = dN2dS(etaArray[i]);
            dNdEta[i][2] = dN3dS(etaArray[i]);
            dNdEta[i][3] = dN4dS(etaArray[i]);

            dNdKsi[i][0]  = dN1dn(ksiArray[i]);
            dNdKsi[i][1]  = dN2dn(ksiArray[i]);
            dNdKsi[i][2]  = dN3dn(ksiArray[i]);
            dNdKsi[i][3]  = dN4dn(ksiArray[i]);
        }
    }

    // Obliczenie Jakobianu dla elementu. Macierz jakobiego/macierz przeksztalcenia
    // Jakobian przeksztalcenia detJ jest to macierz pochodnych czastkowych odwzorowania geometrycznego
    // czyli dzieki niemu wiemy jak zachowac dlugosc? wag? a bardziej proporcje pomiedzy naszym elementem w xy a elementem w ksi eta (uklad globalny -> lokalny)
    //
    // Macierz jakobiego mowi jak geometrycznie zmienil sie element uklad xy wzgledem ukladu ksieta
    // rowniez interpoluje z wsp ksieta na wsp XY
    // wagi w ukladzie xy globalnym sa inne niz w ukladzie ksieta wiec musze przez detJ wymnozyc


    // z XY na KSIETA jakobianem
    // z KSIETA na XY odwroconym jakobianem
    private void initJakobianAndInterpolate() {

        dNdX = new Double[arraySizeBasedOnPointsOfIntegral][4];
        dNdY = new Double[arraySizeBasedOnPointsOfIntegral][4];

        Double[][] InvertedJakobianMatrix = new Double[2][2];
        Double[][] JakobianMatrix = new Double[2][2];

        for (int i =0; i< arraySizeBasedOnPointsOfIntegral;i++) {

            // wyznaczenie danych do macierzy jakobiego
            // punkt w macierzy jakobiego dXdKsi = PochodnaFunkcjiKsztaltuPoKsi(P_calkowania1)*P_calkowania1 + ...
            Double dXdKsi = dNdKsi[i][0] * x[0] + dNdKsi[i][1] * x[1] + dNdKsi[i][2] * x[2] + dNdKsi[i][3] * x[3];
            Double dXdEta = dNdEta[i][0] * x[0] + dNdEta[i][1] * x[1] + dNdEta[i][2] * x[2] + dNdEta[i][3] * x[3];
            Double dYdKsi = dNdKsi[i][0] * y[0] + dNdKsi[i][1] * y[1] + dNdKsi[i][2] * y[2] + dNdKsi[i][3] * y[3];
            Double dYdEta = dNdEta[i][0] * y[0] + dNdEta[i][1] * y[1] + dNdEta[i][2] * y[2] + dNdEta[i][3] * y[3];

            // macierz jakobiego
            JakobianMatrix[0][0] = dXdEta;
            JakobianMatrix[0][1] = dYdEta;
            JakobianMatrix[1][0] = dXdKsi;
            JakobianMatrix[1][1] = dYdKsi;

            // odwrocona macierz jakobiego  (jeszcze tutaj razy 1/detJ ofc zeby byla dokladnie odwrotne)
            InvertedJakobianMatrix[0][0] = dYdKsi;
            InvertedJakobianMatrix[0][1] = -dYdEta;
            InvertedJakobianMatrix[1][0] = -dXdKsi;
            InvertedJakobianMatrix[1][1] = dXdEta;

            // dodaje wyznacznik do tabeli wyznacznikow
            dets[i] = MatrixCalculator.detMatrx2x2(InvertedJakobianMatrix);

            // mnozy kazdy element odwroconej macierzy jakobiego przez 1/det
            InvertedJakobianMatrix = MatrixCalculator.multiplyMatrixByValue(InvertedJakobianMatrix,1.0/dets[i]);

            // oblicza dNdX dNdY dla danego punktu calkowania
            // interpolacja z ukladu ksieta na uklad XY za pomocą wzoru na interpolacje oraz macierzy jakobiego
            for (int k = 0; k < 4; k++) {
                dNdX[i][k] = (InvertedJakobianMatrix[1][1]*dNdKsi[i][k] + InvertedJakobianMatrix[1][0]*dNdEta[i][k]) ;
                dNdY[i][k] = (InvertedJakobianMatrix[0][1]*dNdKsi[i][k] + InvertedJakobianMatrix[0][0]*dNdEta[i][k]) ;
           }
        }
    }

    // Poszczególne macierze H to wartości funkcji
    //w punktach całkowania.
    private void calcListofMatrixesH(){

        // tworzy liste macierzy H (1 macierz H dla 1 punktu)
        HMatrixesList = new ArrayList<>(arraySizeBasedOnPointsOfIntegral);

        Double[] tempInputX = new Double[4];
        Double[] tempInputY = new Double[4];
        Double[][] matrixH;

        for(int i =0; i<arraySizeBasedOnPointsOfIntegral;i++){
            // pobiera dla kazdego punktu calkowania odpowiedni wektor
            for (int j = 0; j < 4; j++) {
                tempInputX[j] = dNdX[i][j];
                tempInputY[j] = dNdY[i][j];
            }

            // wektor jest mnozony przez wlasna transpozycje
            Double[][] tempDNDX = MatrixCalculator.multiplyVectorByItsOwnTranspose(tempInputX);
            Double[][] tempDNDY = MatrixCalculator.multiplyVectorByItsOwnTranspose(tempInputY);

            // dodaje macierze dndx dndy
            matrixH = MatrixCalculator.addMatrices(tempDNDX,tempDNDY);
            // mnoze razy wyznacznik jakobiego (we wzorze to jest dV! ale dajemy detJ
            // oraz conductivity
            // dV to objetosc?
            matrixH = MatrixCalculator.multiplyMatrixByValue(matrixH,kt*dets[i]);

            // dodaje do listy macierzy H
            HMatrixesList.add(matrixH);
        }

    }
    //Poszczególne macierze H to wartości funkcji
    //w punktach całkowania. Celem obliczenia
    //całki należy przemnożyć je przez
    //odpowiednie wagi.
    private void calculateFinalHforElement(){
        GaussTable gaussTable = new GaussTable(pointsOfIntegral);
        int weightX = 0;
        int weightY = 0;

        H = MatrixCalculator.zeros(4,4);
        Double[][] temp = MatrixCalculator.zeros(4,4);

        for (int k =0; k<arraySizeBasedOnPointsOfIntegral; k++) {

            temp = MatrixCalculator.multiplyMatrixByValue(HMatrixesList.get(k),(
                    gaussTable.weights.get(weightX)*gaussTable.weights.get(weightY)));

            H = MatrixCalculator.addMatrices(H,temp);

            weightX+=1;
            if(weightX == pointsOfIntegral){
                weightY+=1;
                weightX=0;
            }
        }

    }


    // uzywam klasy surface to oblcizenia ()
    private void calcHBCandP() {
        Surface surface = new Surface(pointsOfIntegral,element,alfa,grid);
        Hbc = surface.getFinalHBC();
        P = surface.getFinalP();
    }

    private void calcMatrixC() {
        Double[][] matrixFunKsztaltu = new Double[arraySizeBasedOnPointsOfIntegral][4];

        for (int i = 0; i < arraySizeBasedOnPointsOfIntegral; i++) {
            matrixFunKsztaltu[i][0]=funKsztaltu1(etaArray[i],ksiArray[i]);
            matrixFunKsztaltu[i][1]=funKsztaltu2(etaArray[i],ksiArray[i]);
            matrixFunKsztaltu[i][2]=funKsztaltu3(etaArray[i],ksiArray[i]);
            matrixFunKsztaltu[i][3]=funKsztaltu4(etaArray[i],ksiArray[i]);
        }
        //   MatrixCalculator.printMatrix(matrixFunKsztaltu);

        ArrayList<Double[][]> listOfCMatrices = new ArrayList<>();

        for (int i = 0; i < arraySizeBasedOnPointsOfIntegral; i++) {
            Double[][] matrixLocalC = new Double[4][4];
            matrixLocalC = MatrixCalculator.multiplyVectorByItsOwnTranspose(matrixFunKsztaltu[i]);
            matrixLocalC = MatrixCalculator.multiplyMatrixByValue(matrixLocalC,GlobalData.specificHeat * GlobalData.density * dets[i]);

            //System.out.println(GlobalData.specificHeat + " * " + GlobalData.density + " * " + dets[i]);

            // matrix transponowana * cieplowlasciwe * gestosc * detJ wyznacznik
            listOfCMatrices.add(matrixLocalC);
        }
        GaussTable gaussTable = new GaussTable(pointsOfIntegral);

        int weightX = 0;
        int weightY = 0;

        Double[][] C = MatrixCalculator.zeros(4,4);
        Double[][] temp = MatrixCalculator.zeros(4,4);

        for (int k =0; k<arraySizeBasedOnPointsOfIntegral; k++) {

            temp = MatrixCalculator.multiplyMatrixByValue(listOfCMatrices.get(k),(
                    gaussTable.weights.get(weightX)*gaussTable.weights.get(weightY)));

            C = MatrixCalculator.addMatrices(C,temp);


            weightX+=1;
            if(weightX == pointsOfIntegral){
                weightY+=1;
                weightX=0;
            }
        }

        matrixC = C;

    }


    private Double funKsztaltu1(Double eta, Double ksi){  return 0.25*(1.0-ksi)*(1.0-eta); }
    private Double funKsztaltu2(Double eta, Double ksi){
        return 0.25*(1.0+ksi)*(1.0-eta);
    }
    private Double funKsztaltu3(Double eta, Double ksi){
        return 0.25*(1.0+ksi)*(1.0+eta);
    }
    private Double funKsztaltu4(Double eta, Double ksi){
        return 0.25*(1.0-ksi)*(1.0+eta);
    }

    // pochodne funkcji ksztaltu wzgledem ksi oraz eta
    private double dN1dS(Double eta){
        return -0.25*(1.0-eta);
    }
    private double dN2dS(Double eta){
        return 0.25*(1.0-eta);
    }
    private double dN3dS(Double eta){
        return 0.25*(1.0+eta);
    }
    private double dN4dS(Double eta){
        return -0.25*(1.0+eta);
    }

    private double dN1dn(Double ksi){
        return -0.25*(1.0-ksi);
    }
    private double dN2dn(Double ksi){
        return -0.25*(1.0+ksi);
    }
    private double dN3dn(Double ksi){ return 0.25*(1.0+ksi);}
    private double dN4dn(Double ksi){
        return 0.25*(1.0-ksi);
    }



    public Double[][] getHbc() {
        return Hbc;
    }

    public Double[] getP() {
        return P;
    }
}

