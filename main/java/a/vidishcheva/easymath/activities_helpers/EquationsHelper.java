package a.vidishcheva.easymath.activities_helpers;

import java.util.ArrayList;

/**
 * Created by Алёна on 06.04.2016.
 */
public class EquationsHelper {

    double d;
    double n1;
    double n2;
    double n3;
    double n4;
    double res;
    double res1;
    double res2;
    double res3;
    double res4;
    ArrayList<Double> result;
    double r; //0.0 - 2 answers; -1.0 - 1 ans; -2.0 no answ; -3.0 - no values
    // -4.0 - 4 ans, -5.0 - 3 ans, -6.0 rational ans

    public ArrayList<Double> countQE(ArrayList<Double> params){
        setParams(params);
        setDiscriminant();
        count();
        setResult();
        return result;
    }
    public ArrayList<Double> countBI(ArrayList<Double> params){
        setParams(params);
        setDiscriminant();
        count();
        addCountFour();
        setResult();
        return result;
    }
    public ArrayList<Double> countCub(ArrayList<Double> params){
        setParams(params);
        addcountCub();
        setResult();
        return result;
    }
    private void addcountCub() {
        if (n1 == 0){
            r = -3.0;
            return;
        }
        if (res != 0.0){
            n4 = n4-res;
            res = 0;
        }
        double a = n2/n1;
        double b = n3/n1;
        double c = n4/n1;
        double Q = (a*a-3.0*b)/9.0;
        double R = (2.0*a*a*a - 9.0*a*b +27.0*c)/54.0;
        double R2=R*R;
        double Q3=Q*Q*Q;

        if(R2<Q3) {
            System.out.println("R2<Q3");
            double tmp=Math.toDegrees(Math.acos(R / Math.sqrt(Q3))) / 3;
            res1 = -2.0 * Math.sqrt(Q) * Math.cos(tmp) - a/3.0;
            res2 = -2.0 * Math.sqrt(Q) * Math.cos(tmp + (2./3.)*Math.PI) - a/3.;
            res3 = -2.0 * Math.sqrt(Q) * Math.cos(tmp - (2./3.)*Math.PI) - a/3.;
            r = -5.0;
        } else if (R2>Q3){
            if (Q>0){
                System.out.println("R2>Q3  & Q>0");
                double x = Math.abs(R)/Math.pow(Math.abs(Q), 3./2.);
                double tmp = Math.log(x+Math.sqrt(x*x-1.))/3.;
                x = (Math.pow(Math.E, tmp) + Math.pow(Math.E, -tmp))/2.;
                res1 = -2.*Math.signum(R) * Math.sqrt(Q) *  x - a/3.;
                res2 = Math.signum(R) * Math.sqrt(Q) *  x - a/3.;
                x = (Math.pow(Math.E, tmp) - Math.pow(Math.E, -tmp))/2.;
                res3 = Math.sqrt(3.) * Math.sqrt(Q) * x;
            } else if (Q<0){
                System.out.println("R2>Q3  & Q<0");
                double x = Math.abs(R)/Math.sqrt(Math.abs(Q3));
                double tmp = Math.log(x+Math.sqrt(x*x+1.))/3.;
                x = (Math.pow(Math.E, tmp) - Math.pow(Math.E, -tmp))/2.;
                res1 = -2.*Math.signum(R) * Math.sqrt(Math.abs(Q)) *  x - a/3.;
                res2 = Math.signum(R) * Math.sqrt(Math.abs(Q)) *  x - a/3.;
                x = (Math.pow(Math.E, tmp) + Math.pow(Math.E, -tmp))/2.;
                res3 = Math.sqrt(3.) * Math.sqrt(Math.abs(Q)) * x;
            } else if (Q==0){
                System.out.println("R2>Q3  & Q=0");
                res1 = -Math.pow(c - a*a*a/27.0, 1/3.0)-a/3.0;
                res2 = -(a+res1)/2;
                res3 = (0.5)*Math.sqrt(Math.abs((a-3*res1)*(a+res1)-4*b));
            }
            r = -6.0;
        } else if (R2 == Q3){
            System.out.println("R2 == Q3");
            r = 0.0;
            res1 = -2*Math.pow(R, 1/3.) - a/3.;
            res2 = Math.pow(R, 1/3.) - a/3.;
        }
    }
    private void addCountFour() {
        if (r!=-2.0 & r!=-3.0){
            if (res1<0 & res2<0){
                r = -2.0;
            } else if (res1<0){
                r = 0.0;
                res1 = Math.sqrt(res2);
                res2 = -res1;
            } else if (res2<0){
                r = 0.0;
                res2 = Math.sqrt(res1);
                res1 = -res2;
            } else {
                r = -4;
                res3 = Math.sqrt(res1);
                res4 = Math.sqrt(res2);
                res1 = -res3;
                res2 = -res4;
            }
        }
    }
    private void setResult() {
        result = new ArrayList<>();
        result.add(r);
        if (r != -2.0 || r!= -3.0){
            result.add(res1);
            if (r!=-1.0){
                result.add(res2);
                if (r!=0.0){
                    result.add(res3);
                    if (r == -4.0){
                        result.add(res4);
                    }
                }
            }
        }
    }
    private void setParams(ArrayList<Double> params) {
        if (params.size()==4){
            n1 = params.get(0);
            n2 = params.get(1);
            n3 = params.get(2);
            res = params.get(3);
        } else {
            n1 = params.get(0);
            n2 = params.get(1);
            n3 = params.get(2);
            n4 = params.get(3);
            res = params.get(4);
        }
    }
    private void setDiscriminant() {
        if (res != 0.0){
            n3 = n3-res;
            res = 0;
        }
        d = n2 * n2 - 4 * n1 * n3;
    }
    private void count() {
        if (n1 == 0 && n2 == 0){
            r = -3.0;
        } else if (n2 == 0){
            res1 = Math.sqrt((-n3)/n1);
            res2 = -Math.sqrt((-n3)/n1);
            r = 0.0;
        }else if (n1 == 0){
            res1 = res2 = (-n3)/n2;
            r = -1.0;
        }else{
            if (d == 0){
                res1 = res2 = -(n2 / (2 * n1));
                r = -1.0;
            }else if (d>0){
                res1 = (-n2 + Math.sqrt(d))/(2*n1);
                res2 = (-n2 - Math.sqrt(d))/(2*n1);
                r = 0.0;
            } else if (d<0){
                r = -2.0;
            }
        }
    }
}
