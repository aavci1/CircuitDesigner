import javax.swing.*;
import java.io.*;
import java.io.File;
import java.util.*;

public class Circuit {
    StringTokenizer tokenizer;
    int MAX2 = 0;

    Elements[] el = new Elements[1000];
    int[] t = new int[10];
    int[] d = new int[10];

    double[][] ag;
    double[] ga;

    boolean active = true;
    boolean cutoff = false;
    boolean saturation = false;
    boolean onV = true;
    boolean offI = false;

    String line, type, name, textInfo = "", tourS = "", tour2S = "";
    int firstN, secondN, posCont, negCont, conN, thirdN, activeK, saturationK, cutoffK, onK, offK;
    double value, vsat;
    int count = 0, scan = 0, i = 0, ct = 0, tour = 0, tour2 = 0, cd = 0;

    public Circuit() {

    }

    public double[] getResult() {
        count = 0;
        textInfo = "";

        File file1 = new File("circuit.dat");

        try {
            FileReader fr = new FileReader(file1);
            BufferedReader inFile = new BufferedReader(fr);

            line = inFile.readLine();

            while (line != null) {
                tokenizer = new StringTokenizer(line);

                try {
                    type = tokenizer.nextToken();
                    name = tokenizer.nextToken();
                    firstN = Integer.parseInt(tokenizer.nextToken());
                    secondN = Integer.parseInt(tokenizer.nextToken());
                    thirdN  = Integer.parseInt(tokenizer.nextToken());
                    posCont = Integer.parseInt(tokenizer.nextToken());
                    negCont = Integer.parseInt(tokenizer.nextToken());
                    conN = Integer.parseInt(tokenizer.nextToken());
                    vsat = Double.parseDouble(tokenizer.nextToken());
                    value = Double.parseDouble(tokenizer.nextToken());

                    if (i < firstN)
                        i = firstN;
                    if (i < secondN)
                        i = secondN;
                    if (i < thirdN)
                        i = thirdN;

                    if( type.equalsIgnoreCase("T")){
                        //t[ct++] = count;
                        el[count] = new Elements("VS", "T-1", firstN, thirdN, thirdN, posCont, negCont, conN, vsat, 0.7);
                        System.out.println(el[count]);
                        count++;
                        el[count] = new Elements("CDCS", "T-2", firstN, secondN, thirdN, posCont, negCont, thirdN, vsat, value);
                        System.out.println(el[count]);
                        count++;
                    }
                    else if( type.equalsIgnoreCase("D") ){
                        //d[ cd ] = count;
                        el[count] = new Elements("VS", "Diode", firstN, secondN, thirdN, posCont, negCont, conN, vsat, 0.7);
                        System.out.println(el[count]);
                        count++;
                    }
                    else if( type.equalsIgnoreCase("O") ){
                        //d[ cd ] = count;
                        el[count] = new Elements("VDVS", "Opamp", thirdN, secondN, thirdN, firstN, secondN, conN, vsat, 100000);
                        System.out.println(el[count]);
                        count++;
                    }
                    else{
                        el[count] = new Elements(type, name, firstN, secondN, thirdN, posCont, negCont, conN, vsat, value);

                        System.out.println(el[count]);


                        count++;
                    }
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, " Error in input. Line ignored: " + line, " Exception message!!! ", JOptionPane.ERROR_MESSAGE);
                }
                line = inFile.readLine();
            }
            inFile.close();
            i += 1;
            MAX2 = i + count - 1;

            ag = new double[MAX2*2][MAX2*2];
            ga = new double[MAX2*2];

            for (int n = 0; n < ag.length; n++)
                for (int m = 0; m < ag[i].length; m++)
                    ag[n][m] = 0;
            for (int m2 = 0; m2 < ga.length; m2++)
                ga[m2] = 0;

            double[][] ag2 = new double[MAX2][MAX2 + 1];
            double[] ga2 = new double[MAX2];
            double[] result = new double[MAX2];

            /*tour2 = 2^(d.length);
            for( int j = 0; j <= tour2-1 ; j++ ){              //********************************* diode's for

                tour2S = "" + (i%2);

                tour = 3^(t.length);

                for( int i = 0; i <= tour-1 ;i++){  */         //*************************************  transistor's for

                    for (int n1 = 0; n1 < ag2.length; n1++)
                        for (int m1 = 0; m1 < ag2[n1].length; m1++)
                            ag2[n1][m1] = 0;
                    for (int n2 = 0; n2 < ga2.length; n2++)
                        ga2[n2] = 0;

                    /*int k = 0;
                    int l = 0;

                    tourS = "" + (i%3);*/
                    for (scan = 1; scan < count + 1; scan++){
                        /*if( scan == t[k] ){
                            if( tourS.charAt(k) == '0' ){
                                active = true;
                                saturation = false;
                                cutoff = false;
                                activeK = k;
                            }
                            else if( tourS.charAt(k) == '1' ){
                                saturation = true;
                                cutoff = false;
                                active = false;
                                saturationK = k;
                            }
                            else if ( tourS.charAt(k) == '2' ){
                                cutoff = true;
                                active = false;
                                saturation = false;
                                cutoffK = k;
                            }
                            k++;
                        }
                        if( scan == d[k] ){
                            if( tour2S.charAt(k) == '0' ){
                                offI = true;
                                offK = k;
                            }
                            else if( tour2S.charAt(k) == '1' ){
                                onV = true;
                                onK = l;
                            }
                            l++;
                        }       */
                        makeArray(scan, el[scan - 1]);
                    }

                    for (int i4 = 0; i4 < ag2.length; i4++)
                        for (int j4 = 0; j4 < ag2[i4].length; j4++)
                            ag2[i4][j4] = ag[i4 + 1][j4 + 1];

                    for (int i5 = 0; i5 < ga2.length; i5++)
                        ga2[i5] = ga[i5 + 1];

                    for (int em = 0; em < MAX2; em++)
                        ag2[em][MAX2] = ga2[em];

                    try {

                        eliminate(ag2);

                        System.out.println("<<result table>>");
                        for (int i2 = 0; i2 < MAX2; i2++) {
                            result[i2] = ag2[i2][MAX2];
                            System.out.println(result[i2]);
                        }
                        /*if( t.length != 0){
                            if( active && (result[activeK] < 0) && ((result[activeK+el[activeK].thirdN] - result[activeK+el[activeK].secondN]) < 0))
                                return null;
                            if( saturation && (result[saturationK+1] > result[saturationK] * el[saturationK].value) && (result[saturationK] < 0) )
                                return null;
                            if( cutoff && ( (result[cutoffK+el[cutoffK].thirdN] - result[cutoffK+el[cutoffK].firstN]) < 0) )
                                return null;
                        }
                        if( d.length != 0 ){
                            if( onV && (result[onK] < 0))
                                return null;
                            if( offI && ((result[offK+el[offK].secondN] - result[offK+el[offK].firstN]) > 0))
                                return null;
                        }    */
                        return result ;
                    } catch(Exception exc) {
                        exc.printStackTrace();
                        JOptionPane.showMessageDialog(null,"Please, redraw the circuit");
                        return null ;
                    //}
                //}                                                        //***************************          end transistor's for
            }                                                       //*****************************       end diode's for
        } catch (FileNotFoundException exception) {
            System.out.println(" The file " + file1 + " was not found!!! ");
            System.exit(0);
        } catch (IOException exception) {
            System.out.println(exception);
            System.exit(0);
        }
        return null ;
    }

    public double[][] getArray(double[][] last) {
        double[][] go = new double[last.length][last[0].length];

        for (int i = 0; i < last.length; i++)
            for (int j = 0; j < last[i].length; j++)
                go[i][j] = last[i][j];
        return last;
    }

    public void makeArray(int k, Elements e) {
        if (e.firstN > 0)
            ag[e.firstN][k] = 1;
        if (e.secondN > 0)
            ag[e.secondN][k] = -1;
        if (e.type.equalsIgnoreCase("VS")) {
            if (e.firstN > 0)
                ag[i - 1 + k][count + e.firstN] = 1;
            if (e.secondN > 0)
                ag[i - 1 + k][count + e.secondN] = -1;
            ga[i - 1 + k] = e.value;
        }
        if (e.type.equalsIgnoreCase("CS")) {
            ag[i - 1 + k][k] = 1;
            ga[i - 1 + k] = e.value;
        }
        if (e.type.equalsIgnoreCase("R")) {
            ag[i - 1 + k][k] = -(e.value);
            if (e.firstN > 0)
                ag[i - 1 + k][count + e.firstN] = 1;
            if (e.secondN > 0)
                ag[i - 1 + k][count + e.secondN] = -1;
        }
        if (e.type.equalsIgnoreCase("VDVS")) {
            if (e.firstN > 0)
                ag[i - 1 + k][count + e.firstN] = 1;
            if (e.secondN > 0)
                ag[i - 1 + k][count + e.secondN] = -1;
            if (e.posCont > 0)
                ag[i - 1 + k][count + e.posCont] -= e.value;
            if (e.negCont > 0)
                ag[i - 1 + k][count + e.negCont] += e.value;
        }
        if (e.type.equalsIgnoreCase("CDCS")) {
            ag[i - 1 + k][k] = 1;
            ag[i - 1 + k][e.conN] -= e.value;
        }
        if (e.type.equalsIgnoreCase("VDCS")) {
            ag[i - 1 + k][k] = 1;
            ag[i - 1 + k][count + e.posCont] = -(e.value);
            ag[i - 1 + k][count + e.negCont] += e.value;
        }
        if (e.type.equalsIgnoreCase("CDVS")) {
            if (e.firstN > 0)
                ag[i - 1 + k][count + e.firstN] = 1;
            if (e.secondN > 0)
                ag[i - 1 + k][count + e.secondN] = -1;
            ag[i - 1 + k][e.conN] = -(e.value);
        }
    }

    public void eliminate(double[][] matrix) {
        double pivot, element;
        double dummy[][] = new double[1][1000];
        int ex;

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != matrix.length + 1)
                return;
        }

        for (int pr = 0; pr < matrix.length; pr++) {
            pivot = matrix[pr][pr];
            System.out.println("pivot=" + pivot);


//exchanging rows

            if (pivot == 0) {
                for (int x = 0; x < matrix[0].length; x++)
                    dummy[0][x] = matrix[pr][x];           	// copying matrix into dummy

                ex = 1;

                try {
                    while ((matrix[pr + ex][pr] == 0) && (ex < matrix.length - pr)) 	//  < or <=
                        ex++;
                } catch(Exception exc) {
                    JOptionPane.showMessageDialog(null,"ERROR. REDRAW the Circuit");
                    return ;
                }

                if (matrix[pr + ex][pr] != 0) {
                    for (int x = 0; x < matrix[0].length; x++)
                        matrix[pr][x] = matrix[pr + ex][x];
                    // exchanging matrix
                    for (int x = 0; x < matrix[0].length; x++)
                        matrix[pr + ex][x] = dummy[0][x];
                } else {
                    System.out.println("else");
                    ex = -1;
                    while ((matrix[pr + ex][pr] == 0) && (pr + ex >= 0))
                        ex--;
                    for (int x = 0; x < matrix[0].length; x++)
                        matrix[pr][x] = matrix[pr + ex][x];
                    // exchanging matrix
                    for (int x = 0; x < matrix[0].length; x++)
                        matrix[pr + ex][x] = dummy[0][x];
                }
                if (matrix[pr][pr] != 0)
                    pivot = matrix[pr][pr];
                else
                    System.out.println("ERROR!");
            }
            for (int c = 0; c < matrix[0].length; c++)
                matrix[pr][c] /= pivot;
            for (int sr = 0; sr < matrix.length; sr++) {
                if (pr == sr)
                    continue;
                element = matrix[sr][pr];

                for (int c = pr; c < matrix[0].length; c++)
                    matrix[sr][c] -= element * matrix[pr][c];
            }
        }
    }
}