package a.vidishcheva.easymath.experimental_lab;

import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by Алёна on 14.04.2016.
 */
public class ExpCounter {

    static EditText editText;
    static ArrayList<Double> numbers;
    static ArrayList<String> signs;
    static int currentBracesOpen;
    static int currentBracesClose;
    static String a;
    static HashSet<String> results;
    static DecimalFormat df;
    //static boolean checking;
    static int xCount;

        static long xInside;
        //static String che = "";

        public ExpCounter(EditText editText, String a) {
            df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            this.editText = editText;
            this.a = a;
            results = new HashSet<>();
            xInside = 0;
            //checking = false;
            xCount = 0;
    }

    public static HashSet<String> parseString() {
        //equals
        int equals = a.indexOf('=');
        if (equals != -1){
            if (a.endsWith("=0")){
                a = a.replace("=0", "");
            } else if (a.endsWith("=")){
                a = a.replace("=","");
            } else {
                a = a.replace("=", "-(") + ")";
            }
        } else {
            int braces = ExpEditTextController.checkBracesCount(a);
            if (braces>0){
                for (int i = 0; i<braces; i++){
                    a = a + ")";
                }
                editText.setText(a + "=0");
            }
        }

        // if x exists or not
        String res;
        if (a.contains("x")){
            int ind = a.indexOf("x");
            xCount++;
            while ((ind = a.indexOf("x", ind+1)) >0){
                xCount++;
            }
            /*//if only 1 x
            if(xCount == 1){
                countOneX();
            }*/
            countWithXInt(-100, 100);
        } else {
            String r = String.valueOf(countWithoutX(a));
            df.setMaximumFractionDigits(5);
            results.add(df.format(Double.valueOf(r)));
        }

        return results;
    }

    /*private static void countOneX() {

        // braces
        while (a.indexOf("(") >= 0) {
            int indOpen = a.indexOf("(");
            int indClose = a.indexOf(")");
            int indBtw = a.indexOf("(", indOpen+1);

            while (indBtw !=-1 && indBtw<indClose){
                indOpen = indBtw;
                indBtw = a.indexOf("(", indOpen+1);
            }
            if (a.indexOf('x')<indOpen && a.indexOf('x')>indClose){
                String numInBraces = getNumInBrackets(a);
            }
            String numInBraces = getNumInBrackets(a);
            double res = getRes(numInBraces);
            aTmp = aTmp.substring(0, currentBracesOpen) + res + aTmp.substring(currentBracesClose + 1);
        }
        Double result = getRes(aTmp);
        return result;
    }*/

    private static void countWithXInt(long Lower, long Higher) {
        if (results.size() == 2 || results.contains("no answer")){
            return;
        }
        if (Lower>=Higher){
            countWithXDouble(Higher - 1, Lower, 1);
            return;
        }

        long tmp = (Higher+Lower)/2;

        System.out.println("Lower = " + Lower);
        System.out.println("Middle = " + tmp);
        System.out.println("Higher = " + Higher);

        String tmpLower = a, tmpZero=a, tmpHighest = a;
        tmpLower = tmpLower.replaceAll("x", String.valueOf(df.format(Lower)));
        tmpZero = tmpZero.replaceAll("x", String.valueOf(df.format(tmp)));
        tmpHighest = tmpHighest.replaceAll("x", String.valueOf(df.format(Higher)));
        xInside = Lower;
        double tmpL = countWithoutX(tmpLower);
        if (results.size() == 2 || results.contains("no answer")) {
            return;
        }
        xInside = tmp;
        double tmpZ = countWithoutX(tmpZero);
        if (results.size() == 2 || results.contains("no answer")){
            return;
        }
        xInside = Higher;
        double tmpH = countWithoutX(tmpHighest);
        if (results.size() == 2 || results.contains("no answer")){
            return;
        }

        if (Higher-Lower == 1 && ((tmpL>0  && tmpZ> 0 && tmpH>0) || (tmpL<0 && tmpZ<0 && tmpH<0))){
            System.out.println("no answer");
            return;
        }

        System.out.println("result1 lower = " + tmpL);
        System.out.println("result2 tmp = " + tmpZ);
        System.out.println("result3 higher = " + tmpH);

        if (tmpL ==0 || tmpZ ==0 || tmpH ==0){
            df.setMaximumFractionDigits(5);
        }
        if (tmpL == 0.0){
            results.add(String.valueOf(df.format(Lower)));
            return;
        } else if (tmpZ == 0.0){
            results.add(String.valueOf(df.format(tmp)));
            return;
        } else if (tmpH == 0.0){
            results.add(String.valueOf(df.format(Higher)));
            return;
        }

        if (results.size() == 2 || results.contains("no answer")){
            return;
        }
        if (tmpL<0 && tmpZ<0 && tmpH<0){
            if (tmpL<tmpZ && tmpZ<tmpH){
                countWithXInt(Lower - Lower, Lower - 1);
                return;
            }
            if (tmpL>tmpZ && tmpZ>tmpH){
                countWithXInt(Higher + 1, 2 * Higher);
                return;
            } else {
                countWithXInt(tmp, Higher);
                countWithXInt(Lower + 1, tmp - 1);
                return;
            }
        } else if (tmpL>0 && tmpZ>0 && tmpH>0){
            if (tmpL<tmpZ && tmpZ<tmpH){
                countWithXInt(2 * Lower, Lower - 1);
                return;
            }
            if (tmpL>tmpZ && tmpZ>tmpH){
                countWithXInt(Higher + 1, 2 * Higher);
                return;
            } else {
                countWithXInt(tmp + 1, Higher - 1);
                countWithXInt(Lower + 1, tmp - 1);
                return;
            }
        } else if (tmpH>0){
            if (tmpZ > 0) {
                countWithXInt(Lower + 1, tmp - 1);
                return;
            } else if (tmpL > 0) {
                countWithXInt(Lower + 1, tmp - 1);
                countWithXInt(tmp + 1, Higher - 1);
                return;
            } else {
                countWithXInt(tmp + 1, Higher - 1);
                return;
            }
        } else if (tmpZ>0){
            if (tmpL > 0) {
                countWithXInt(tmp + 1, Higher - 1);
                return;
            } else {
                countWithXInt(Lower + 1, tmp - 1);
                countWithXInt(tmp + 1, Higher - 1);
                return;
            }
        } else if (tmpL>0) {
            countWithXInt(Lower + 1, tmp - 1);
            return;
        }
    }

    private static void countWithXDouble(double Lower, double Higher, int digits) {
        if (results.size() == 2 || results.contains("no answer")){
            return;
        }
        System.out.println("digits = " + digits);
        df.setMaximumFractionDigits(digits);
        double min = 1/Math.pow(10, digits);

        if (digits == 10){
            Lower = Math.rint(Lower * Math.pow(10, digits)) / Math.pow(10, digits);
            Higher = Math.rint(Higher * Math.pow(10, digits)) / Math.pow(10, digits);
            double tmp = Math.rint((Higher+Lower)/2*Math.pow(10, digits))/ Math.pow(10, digits);

            String tmpLower = a, tmpZero=a, tmpHighest = a;
            tmpLower = tmpLower.replaceAll("x", String.valueOf(df.format(Lower)));
            tmpZero = tmpZero.replaceAll("x", String.valueOf(df.format(tmp)));
            tmpHighest = tmpHighest.replaceAll("x", String.valueOf(df.format(Higher)));

            double tmpL = countWithoutX(tmpLower);
            if (results.size() == 2 || results.contains("no answer")){
                return;
            }
            double tmpZ = countWithoutX(tmpZero);
            if (results.size() == 2 || results.contains("no answer")){
                return;
            }
            double tmpH = countWithoutX(tmpHighest);
            if (results.size() == 2 || results.contains("no answer")){
                return;
            }

            df.setMaximumFractionDigits(5);
            double res = Math.min( Math.abs(tmpH), Math.min(Math.abs(tmpL), Math.abs(tmpZ)));
            if (res == Math.abs(tmpH)) {
                results.add(String.valueOf(df.format(Higher)));
            } else if( res == Math.abs(tmpZ)){
                results.add(String.valueOf(df.format(tmp)));
            } else if(res == Math.abs(tmpL)){
                results.add(String.valueOf(df.format(Lower)));
            }
            return;
        }


        if (results.size() == 2 || results.contains("no answer")){
            return;
        }
        if (Lower >= Higher){
                countWithXDouble(Higher - min, Lower, digits + 1);
                return;
        }

        Lower = Math.rint(Lower * Math.pow(10, digits)) / Math.pow(10, digits);
        Higher = Math.rint(Higher * Math.pow(10, digits)) / Math.pow(10, digits);
        double tmp = Math.rint((Higher+Lower)/2*Math.pow(10, digits))/ Math.pow(10, digits);

        System.out.println("Lower = " + Lower);
        System.out.println("Middle = " + tmp);
        System.out.println("Higher = " + Higher);

        String tmpLower = a, tmpZero=a, tmpHighest = a;
        tmpLower = tmpLower.replaceAll("x", String.valueOf(df.format(Lower)));
        tmpZero = tmpZero.replaceAll("x", String.valueOf(df.format(tmp)));
        tmpHighest = tmpHighest.replaceAll("x", String.valueOf(df.format(Higher)));
        double tmpL = countWithoutX(tmpLower);
        if (results.size() == 2 || results.contains("no answer")){
            return;
        }
        double tmpZ = countWithoutX(tmpZero);
        if (results.size() == 2 || results.contains("no answer")){
            return;
        }
        double tmpH = countWithoutX(tmpHighest);
        if (results.size() == 2 || results.contains("no answer")){
            return;
        }

        System.out.println("result1 lower = " + df.format(tmpL));
        System.out.println("result2 tmp = " + df.format(tmpZ));
        System.out.println("result3 higher = " + df.format(tmpH));

        if (tmpL ==0 || tmpZ ==0 || tmpH ==0){
            df.setMaximumFractionDigits(5);
        }
        if (tmpL == 0.0){
            results.add(String.valueOf(df.format(Lower)));
            return;
        } else if (tmpZ == 0.0){
            results.add(String.valueOf(df.format(tmp)));
            return;
        } else if (tmpH == 0.0){
            results.add(String.valueOf(df.format(Higher)));
            return;
        }

        if (tmpL<0 && tmpZ<0 && tmpH<0){
            if (tmpL<tmpZ && tmpZ<tmpH){
                // check from checkwithXInt MIN MAX
                countWithXDouble(Lower - Lower, Lower-min, digits);
            }
            if (tmpL>tmpZ && tmpZ>tmpH){
                countWithXDouble(Higher+min, Higher + Higher, digits);
            } else {
                countWithXDouble(tmp+min, Higher-min, digits);
                countWithXDouble(Lower+min, tmp-min, digits);
            }
        } else if (tmpL>0 && tmpZ>0 && tmpH>0){
            if (tmpL<tmpZ && tmpZ<tmpH){
                countWithXDouble(Lower - Lower, Lower-min, digits);
            }
            if (tmpL>tmpZ && tmpZ>tmpH){
                countWithXDouble(Higher+min, Higher + Higher, digits);
            } else {
                countWithXDouble(tmp+min, Higher-min, digits);
                countWithXDouble(Lower+min, tmp-min, digits);
            }
        } else if (tmpH>0){
            if (tmpZ > 0) {
                countWithXDouble(Lower+min, tmp-min, digits);
            } else if (tmpL > 0) {
                countWithXDouble(Lower+min, tmp-min, digits);
                countWithXDouble(tmp+min, Higher-min, digits);
            } else {
                countWithXDouble(tmp+min, Higher-min, digits);
            }
        } else if (tmpZ>0){
            if (tmpL > 0) {
                countWithXDouble(tmp+min, Higher-min, digits);
            } else {
                countWithXDouble(Lower+min, tmp-min, digits);
                countWithXDouble(tmp+min, Higher-min, digits);
            }
        } else if (tmpL>0) {
            countWithXDouble(Lower+min, tmp, digits);
        }
    }

    private static double countWithoutX(String aTmp) {
        while (aTmp.indexOf("(") >= 0) {
            String numInBraces = getNumInBrackets(aTmp);
            double res = getRes(numInBraces);
            aTmp = aTmp.substring(0, currentBracesOpen) + res + aTmp.substring(currentBracesClose + 1);
        }
        Double result = getRes(aTmp);
        return result;
    }

    private static String getNumInBrackets(String tmp) {
        int indOpen = tmp.indexOf("(");
        int indClose = tmp.indexOf(")");
        int indBtw = tmp.indexOf("(", indOpen+1);

        while (indBtw !=-1 && indBtw<indClose){
            indOpen = indBtw;
            indBtw = tmp.indexOf("(", indOpen+1);
        }
        currentBracesOpen = indOpen;
        currentBracesClose = indClose;
        return tmp.substring(indOpen + 1, indClose);
    }

    private static double getRes(String num) {
        numbers = new ArrayList<Double>();
        signs = new ArrayList<String>();

        StringBuilder number = new StringBuilder("");

        String[] ar = num.split("");

        for (int i = 0; i < ar.length; i++){
            if (ar[i].equals("-") && (ar[i-1].equals("+") || ar[i-1].equals("*") || ar[i-1].equals("/") || ar[i-1].equals("^")
                    || ar[i-1].equals("-") || ar[i-1].equals(""))){
                number.append(ar[i]);
            } else if (ar[i].equals("+") || ar[i].equals("*") || ar[i].equals("-") || ar[i].equals("/") || ar[i].equals("^")){
                signs.add(ar[i]);
                numbers.add(Double.valueOf(number.toString()));
                number = new StringBuilder("");
            } else {
                number.append(ar[i]);
            }
        }
        numbers.add(Double.valueOf(number.toString()));
        while (signs.contains("^")){
            lastAction("^");
        }
        while (signs.contains("/")){
            lastAction("/");
        }
        while (signs.contains("*")){
            lastAction("*");
        }
        while (signs.contains("+")){
            lastAction("+");
        }
        while (signs.contains("-")){
            lastAction("-");
        }
        return numbers.get(0);
    }

    private static void lastAction(String s) {
        double a = 0.0;
        int ind = 0;
        switch (s) {
            case "^": ind = signs.indexOf("^");
                /*if (xCount ==1 && (signs.indexOf("^")==signs.lastIndexOf("^")) && (numbers.get(ind+1)%1<1.0 || numbers.get(ind+1)%2==0) && numbers.get(ind) == xInside){
                    che = String.valueOf(numbers.get(ind)) + "^" + String.valueOf(numbers.get(ind+1));
                    ArrayList<Double> tmpNumbers = numbers;
                    ArrayList<String> tmpSigns = signs;
                    numbers.remove(ind);
                    numbers.remove(ind);
                    numbers.add(ind, 1.0);
                    signs.remove(ind);
                    checking = true;
                    while (signs.contains("/")){
                        lastAction("/");
                    }
                    while (signs.contains("*")){
                        lastAction("*");
                    }
                    while (signs.contains("+")){
                        lastAction("+");
                    }
                    while (signs.contains("-")){
                        lastAction("-");
                    }
                    if (numbers.get(0)>0){
                        results.add("no answer");
                        return;
                    } else {
                        checking = false;
                        numbers = tmpNumbers;
                        signs = tmpSigns;
                    }
                }*/
                a = Math.pow(numbers.get(ind), numbers.get(ind+1));
                if (numbers.get(ind) == xInside || numbers.get(ind+1) == xInside){
                    xInside = (long) a;
                }
                break;
            case "*": ind = signs.indexOf("*");
                a = numbers.get(ind) * numbers.get(ind+1);
                if (numbers.get(ind) == xInside || numbers.get(ind+1) == xInside){
                    xInside = (long) a;
                }
                break;
            case "/": ind = signs.indexOf("/");
                /*if ((signs.indexOf("/")==signs.lastIndexOf("/")) && numbers.get(ind+1) == xInside && checking == false){
                    che = String.valueOf(numbers.get(ind)) + "/" + String.valueOf(numbers.get(ind+1));
                    ArrayList<Double> tmpNumbers = numbers;
                    ArrayList<String> tmpSigns = signs;
                    numbers.remove(ind);
                    numbers.remove(ind);
                    numbers.add(ind, 0.0);
                    signs.remove(ind);
                    while (signs.contains("*")){
                        lastAction("*");
                    }
                    while (signs.contains("+")){
                        lastAction("+");
                    }
                    while (signs.contains("-")){
                        lastAction("-");
                    }
                    if (numbers.get(0)==0){
                        results.add("no answer");
                        return;
                    } else {
                        numbers = tmpNumbers;
                        signs = tmpSigns;
                    }
                }*/
                a = numbers.get(ind) / numbers.get(ind+1);
                if (numbers.get(ind) == xInside || numbers.get(ind+1) == xInside){
                    xInside = (long) a;
                }
                break;
            case "+": ind = signs.indexOf("+");
                a = numbers.get(ind) + numbers.get(ind+1);
                if (numbers.get(ind) == xInside || numbers.get(ind+1) == xInside){
                    xInside = (long) a;
                }
                break;
            case "-": ind = signs.indexOf("-");
                a = numbers.get(ind) - numbers.get(ind+1);
                if (numbers.get(ind) == xInside || numbers.get(ind+1) == xInside){
                    xInside = (long) a;
                }
                break;
        }
        numbers.remove(ind);
        numbers.remove(ind);
        numbers.add(ind, a);
        signs.remove(ind);
    }


}
