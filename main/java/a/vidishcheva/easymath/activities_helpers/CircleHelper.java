package a.vidishcheva.easymath.activities_helpers;

import java.util.ArrayList;

/**
 * Created by Алёна on 28.04.2016.
 */
public class CircleHelper {

    final int FORMAT = 100000;
    private double r;
    private double d;
    private double p;
    private double s;
    ArrayList<Double> res; // 0.0 - ok; -1.0 - incorrect, -2,0 - no input
    private int count;

    public ArrayList<Double> findCircle(ArrayList<Double> params){
        setParams(params);
        if (count == 0){
            res.add(-2.0);
            return res;
        }
        if (count != 1){
            res.add(-1.0);
            return res;
        }
        if (r != 0 || d!=0){
            if (r!=0){
                d =  2 * r;
            } else {
                r = d/2;
            }
            p = 2 * r * Math.PI;
            s = Math.PI * Math.pow(r, 2);
        } else if (p!=0){
            r = p/2/Math.PI;
            d = 2*r;
            s = Math.PI * Math.pow(r, 2);
        } else if (s!=0){
            r = Math.sqrt(s/Math.PI);
            d = 2*r;
            p = 2 * r * Math.PI;
        }
        res.add(0.0);
        setResult();
        return res;
    }
    private void setResult() {
        res.add(rint(r));
        res.add(rint(d));
        res.add(rint(p));
        res.add(rint(s));
    }
    private void setParams(ArrayList<Double> params) {
        r = params.get(0);
        d = params.get(1);
        p = params.get(2);
        s = params.get(3);
        res = new ArrayList<>();
        count = 0;
        for (int i = 0; i < params.size(); i++){
            if (params.get(i)!=0){
                count++;
            }
        }
    }
    private Double rint(double x){
        return Math.rint(FORMAT * x) / FORMAT;
    }
}
