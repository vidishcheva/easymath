package a.vidishcheva.easymath.activities_helpers;

import java.util.*;


/**
 * Created by Алёна on 05.04.2016.
 */
public class LogarithmHelper {

    private final int FORMAT = 10000;
    private ArrayList<Double> result;
    private double r;
    private double b;
    private double num;
    private double res;

    public ArrayList<Double> countLog(List<Double> params){
        setParamsLog(params);
        // 0.0 - ok; -1.0 can't find with such params; -2.0 there are 3 params and there are correct; -3.0 -//- not correct; -4.0 values
        if (b!=0 && num!=0 && res!=0){
            if (b < 0 || b ==1){
                r = -1.0;
            } else{
                ArrayList<Double> tmp = new ArrayList<>();
                tmp.add(b);
                tmp.add(num);
                tmp.add(0.0);
                if (res == countLog(tmp).get(3)){
                    r = -2.0;
                } else {
                    r = -3.0;
                }
            }
        } else if (b!=0 && num!=0){
            if (b < 0 || b ==1){
                r = -1.0;
            } else {
                res = Math.log(num) / Math.log(b);
                r = 0.0;
            }
        } else if (num!=0 && res!=0){
            b = Math.pow(num, 1/res);
            r = 0.0;
        } else if (b!=0 && res!=0){
            if (b < 0 || b ==1){
                r = -1.0;
            } else {
                num = Math.pow(b, res);
                r = 0.0;
            }
        }
        else r = -4.0;
        setResultsLog();
        return result;
    }
    public ArrayList<Double> countLn(List<Double> params){
        setParamsLn(params);
        if (num!=0 && res!=0){
            if (Math.log(num) == res){
                r = -2.0;
            } else {
                r = -3.0;
            }
        } else if (num != 0) {
            res = Math.log(num);
            r = 0.0;
        } else if (res !=0 ) {
            num = Math.pow(Math.E, res);
            r = 0.0;
        }
        else r = -4.0;
        setResultsLn();
        return result;
    }
    private void setParamsLog(List<Double> params) {
        r = 0.0;
        b = params.get(0);
        num = params.get(1);
        res = params.get(2);
    }
    private void setParamsLn(List<Double> params) {
        r = 0.0;
        num = params.get(0);
        res = params.get(1);
    }
    private void setResultsLog(){
        result = new ArrayList<>();
        result.add(rint(r));
        result.add(rint(b));
        result.add(rint(num));
        result.add(rint(res));
    }
    private void setResultsLn(){
        result = new ArrayList<>();
        result.add(rint(r));
        result.add(rint(num));
        result.add(rint(res));
    }
    private Double rint(double x){
        return Math.rint(FORMAT * x) / FORMAT;
    }
}
