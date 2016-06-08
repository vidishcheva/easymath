package a.vidishcheva.easymath.activities_helpers;

import java.util.ArrayList;

/**
 * Created by Алёна on 31.03.2016.
 */
public class TriangleHelper {

    final int FORMAT = 1000;
    Double result; // 0 - ok, -1 - no triangle, -2 - please input only 3 params, -3 infinity of triangles
    Double AB;
    Double BC;
    Double AC;
    Double a;
    Double b;
    Double c;
    Double P;
    Double S;
    ArrayList<Double> res;

    public ArrayList<Double> findTriangle(ArrayList<Double> sidesAndAngles) {
        getParamsAndRoundToThreeDecimalPlaces(sidesAndAngles);
        if (AB != 0 && BC != 0 && AC != 0) {
            countByThreeSides();
        } else if ((AB != 0 && BC != 0 && b != 0) || (BC != 0 && AC != 0 && c != 0) || (AB != 0 && AC != 0 && a != 0)) {
            countByTwoSidesAndAngleBetween();
        } else if ((AB != 0 && a != 0 && b != 0) || (BC != 0 && b != 0 && c != 0) || (AC != 0 && a != 0 && c != 0)){
            countByOneSideAndTwoAngles();
        } else if ((AB != 0 && BC != 0 && a != 0) || (BC != 0 && AC != 0 && a != 0) || (AB != 0 && AC != 0 && b != 0) ||
                (AB != 0 && BC != 0 && c != 0) || (BC != 0 && AC != 0 && b != 0) || (AB != 0 && AC != 0 && c != 0)) {
            countByTwoSidesAndOppositeAngle();
        } else if (a!=0 && b!=0 && c!=0){
            countByThreeAngles();
        } else if ((a!=0 && b!=0) || (a!=0 && c!=0) || (b!=0 && c!=0)){
            countByTwoAnglesAndSmthElse();
        } else {
            result = 0.0;
            setParams();
        }
        return res;
    }
    private void countByTwoAnglesAndSmthElse() {
        if (a+b+c >=180){
            result = -1.0;
            setParams();
        } else if (a!=0 && b!=0){
            c = 180 - (a+b);
            countByThreeAngles();
        } else if (a!=0 && c!=0){
            b = 180-(a+c);
            countByThreeAngles();
        } else if (b!=0 && c!=0){
            a = 180-(b+c);
            countByThreeAngles();
        }
    }
    private void countByThreeAngles() {
        if (AB != 0 && AC != 0 && BC !=0){
            countByThreeSides();
        } else if ((AB!=0 && AC!=0) || (AB!=0 && BC!=0) || (AC!=0 && BC!=0)){
            countByTwoSidesAndAngleBetween();
        } else if (AB!=0 || AC!=0 || BC!=0){
            countByOneSideAndTwoAngles();
        } else {
            if (a+b+c != 180){
                result = -1.0;
            } else {
                AB = 10.0;
                BC = getSideByOneSideAndTwoAngles(AB, a, c);
                AC = getSideByOneSideAndTwoAngles(AB, b, c);
                result = -3.0;
                setPAndS();
            }
            setParams();
        }
    }
    private void countByOneSideAndTwoAngles() {
        if (AB!=0 && a!=0 && b!=0){
            if (a+b>=180){
                result = -1.0;
            } else{
                double cTmp = 180 - (a+b);
                double BCTmp = getSideByOneSideAndTwoAngles(AB, a, cTmp);
                double ACTmp = getSideByOneSideAndTwoAngles(AB, b, cTmp);
                if (checkAdditionalParams(c, cTmp) && checkAdditionalParams(BC, BCTmp) && checkAdditionalParams(AC, ACTmp)){
                    result = 0.0;
                    c = cTmp;
                    BC = BCTmp;
                    AC = ACTmp;
                    setPAndS();
                } else {
                    result = -2.0;
                }
            }
        } else if (BC!=0 && b!=0 && c!=0) {
            if (b+c>=180){
                result = -1.0;
            } else{
                double aTmp = 180 - (b+c);
                double ACTmp = getSideByOneSideAndTwoAngles(BC, b, aTmp);
                double ABTmp = getSideByOneSideAndTwoAngles(BC, c, aTmp);
                if (checkAdditionalParams(a, aTmp) && checkAdditionalParams(AC, ACTmp) && checkAdditionalParams(AB, ABTmp)){
                    result = 0.0;
                    a = aTmp;
                    AC = ACTmp;
                    AB = ABTmp;
                    setPAndS();
                } else {
                    result = -2.0;
                }
            }
        } else if (AC!=0 && a!=0 && c!=0){
            if (a+c>=180){
                result = -1.0;
            } else{
                double bTmp = 180 - (a+c);
                double BCTmp = getSideByOneSideAndTwoAngles(AC, a, bTmp);
                double ABTmp = getSideByOneSideAndTwoAngles(AC, c, bTmp);
                if (checkAdditionalParams(b, bTmp) && checkAdditionalParams(BC, BCTmp) && checkAdditionalParams(AB, ABTmp)){
                    result = 0.0;
                    b = bTmp;
                    BC = BCTmp;
                    AB = ABTmp;
                    setPAndS();
                } else {
                    result = -2.0;
                }
            }
        }
        setParams();
    }
    private Double getSideByOneSideAndTwoAngles(Double ab, Double a, Double c) {
        return ab * Math.sin(Math.toRadians(a)) / Math.sin(Math.toRadians(c));
    }
    private void countByTwoSidesAndOppositeAngle() {
        Double d;
        if (AB != 0 && BC != 0 && a != 0) {
            d = getD(AB, BC, a);
            if (d == -200.00) {
                result = -1.0;
            } else{
                double cTmp = Math.toDegrees(Math.asin(d));
                double bTmp = 180 - (a + cTmp);
                double ACTmp = getSideByTwoSidesAndAngleBetween(AB, BC, bTmp);
                if (checkAdditionalParams(c, cTmp) && checkAdditionalParams(b, bTmp) && checkAdditionalParams(AC, ACTmp)) {
                    result = 0.0;
                    AC = ACTmp;
                    b = bTmp;
                    c = cTmp;
                    setPAndS();
                } else if (BC < AB) {
                    double cTmp1 = 180 - cTmp;
                    double bTmp1 = 180 - (a + cTmp1);
                    double ACTmp1 = getSideByTwoSidesAndAngleBetween(AB, BC, bTmp1);
                    if (checkAdditionalParams(c, cTmp1) && checkAdditionalParams(b, bTmp1) && checkAdditionalParams(AC, ACTmp1)) {
                        AC = ACTmp1;
                        b = bTmp1;
                        c = cTmp1;
                        setPAndS();
                        result = 0.0;
                    }
                } else {
                    result = -2.0;
                }
            }
        } else if (BC != 0 && AC != 0 && a != 0) {
            d = getD(AC, BC, a);
            if (d == -200.00) {
                result = -1.0;
            }else {
                double bTmp = Math.toDegrees(Math.asin(d));
                double cTmp = 180 - (a + bTmp);
                double ABTmp = getSideByTwoSidesAndAngleBetween(BC, AC, cTmp);
                if (checkAdditionalParams(b, bTmp) && checkAdditionalParams(c, cTmp) && checkAdditionalParams(AB, ABTmp)) {
                    result = 0.0;
                    AB = ABTmp;
                    b = bTmp;
                    c = cTmp;
                    setPAndS();
                } else if(BC < AC){
                    double bTmp1 = 180 - bTmp;
                    double cTmp1 = 180 - ( a + bTmp1);
                    double ABTmp1 = getSideByTwoSidesAndAngleBetween(BC, AC, cTmp1);
                    if (checkAdditionalParams(b, bTmp1) && checkAdditionalParams(c, cTmp1) && checkAdditionalParams(AB, ABTmp1)) {
                        AB = ABTmp1;
                        b = bTmp1;
                        c = cTmp1;
                        setPAndS();
                        result = 0.0;
                    }
                } else {
                    result = -2.0;
                }
                }
            } else if (AB != 0 && AC != 0 && b != 0) {
                d = getD(AB, AC, b);
            if (d == -200.00) {
                result = -1.0;
            }else {
                double cTmp = Math.toDegrees(Math.asin(d));
                double aTmp = 180 - (b + cTmp);
                double BCTmp = getSideByTwoSidesAndAngleBetween(AB, AC, aTmp);
                if (checkAdditionalParams(c, cTmp) && checkAdditionalParams(a, aTmp) && checkAdditionalParams(BC, BCTmp)) {
                    result = 0.0;
                    BC = BCTmp;
                    a = aTmp;
                    c = cTmp;
                    setPAndS();
                    } else if(AC < AB){
                        double cTmp1 = 180 - cTmp;
                        double aTmp1 = 180 - (b + cTmp1);
                        double BCTmp1 = getSideByTwoSidesAndAngleBetween(AB, AC, aTmp1);
                        if (checkAdditionalParams(c, cTmp1) && checkAdditionalParams(a, aTmp1) && checkAdditionalParams(BC, BCTmp1)) {
                            BC = BCTmp1;
                            a = aTmp1;
                            c = cTmp1;
                            setPAndS();
                            result = 0.0;
                        }
                    } else {
                        result = -2.0;
                    }
                }
            } else if (AB != 0 && BC != 0 && c != 0) {
                d = getD(BC, AB, c);
                if (d == -200.00) {
                    result = -1.0;
                } else {
                    double aTmp = Math.toDegrees(Math.asin(d));
                    double bTmp = 180 - (c + aTmp);
                    double ACTmp = getSideByTwoSidesAndAngleBetween(AB, BC, bTmp);
                    if (checkAdditionalParams(a, aTmp) && checkAdditionalParams(b, bTmp) && checkAdditionalParams(AC, ACTmp)) {
                        result = 0.0;
                        AC = ACTmp;
                        b = bTmp;
                        a = aTmp;
                        setPAndS();
                    } else if (AB < BC){
                        double aTmp1 = 180 - aTmp;
                        double bTmp1 = 180 - (c + aTmp1);
                        double ACTmp1 = getSideByTwoSidesAndAngleBetween(AB, BC, bTmp1);
                        if (checkAdditionalParams(a, aTmp1) && checkAdditionalParams(b, bTmp1) && checkAdditionalParams(AC, ACTmp1)) {
                            AC = ACTmp1;
                            b = bTmp1;
                            a = aTmp1;
                            setPAndS();
                            result = 0.0;
                        }
                    } else {
                        result = -2.0;
                    }
                }
            } else if (BC != 0 && AC != 0 && b != 0) {
                d = getD(BC, AC, b);
                if (d == -200.00) {
                    result = -1.0;
                } else {
                    double aTmp = Math.toDegrees(Math.asin(d));
                    double cTmp = 180 - (b + aTmp);
                    double ABTmp = getSideByTwoSidesAndAngleBetween(AC, BC, cTmp);
                    if (checkAdditionalParams(c, cTmp) && checkAdditionalParams(a, aTmp) && checkAdditionalParams(AB, ABTmp)) {
                        result = 0.0;
                        AB = ABTmp;
                        a = aTmp;
                        c = cTmp;
                        setPAndS();
                    } else if (AC < BC){
                        double aTmp1 = 180 - aTmp;
                        double cTmp1 = 180 - (b + aTmp1);
                        double ABTmp1 = getSideByTwoSidesAndAngleBetween(AC, BC, cTmp1);
                        if (checkAdditionalParams(c, cTmp1) && checkAdditionalParams(a, aTmp1) && checkAdditionalParams(AB, ABTmp1)) {
                            AB = ABTmp1;
                            a = aTmp1;
                            c = cTmp1;
                            setPAndS();
                            result = 0.0;
                        }
                    }else {
                        result = -2.0;
                    }
                }
            } else if (AB != 0 && AC != 0 && c != 0) {
                d = getD(AC, AB, c);
                if (d == -200.00) {
                    result = -1.0;
                } else {
                    double bTmp = Math.toDegrees(Math.asin(d));
                    double aTmp = 180 - (c + bTmp);
                    double BCTmp = getSideByTwoSidesAndAngleBetween(AB, AC, aTmp);
                    if (checkAdditionalParams(b, bTmp) && checkAdditionalParams(a, aTmp) && checkAdditionalParams(BC, BCTmp)) {
                        result = 0.0;
                        BC = BCTmp;
                        b = bTmp;
                        a = aTmp;
                        setPAndS();
                    } else if(AB < AC){
                        double bTmp1 = 180 - bTmp;
                        double aTmp1 = 180 - (c + bTmp1);
                        double BCTmp1 = getSideByTwoSidesAndAngleBetween(AB, AC, aTmp1);
                        if (checkAdditionalParams(b, bTmp1) && checkAdditionalParams(a, aTmp1) && checkAdditionalParams(BC, BCTmp1)) {
                            BC = BCTmp1;
                            b = bTmp1;
                            a = aTmp1;
                            setPAndS();
                            result = 0.0;
                        }
                    } else {
                        result = -2.0;
                    }
                }
            }
            setParams();
    }
    private Double getD(Double x, Double y, Double ang) {
        double d = Math.rint (100000 * (x/y)*Math.sin(Math.toRadians(ang))) / 100000;
        if (ang > 179.998 || (ang >= 90 && x>=y) || d>1){
            return -200.0;
        }
        return d;
    }
    private void countByTwoSidesAndAngleBetween() {
        if (AB!=0 && BC!=0 && b!=0){
            if (b > 179.998){
                result = -1.0;
            } else {
                double ACTmp = getSideByTwoSidesAndAngleBetween(AB, BC, b);
                double aTmp = getAngleWithThreeSides(ACTmp, AB, BC);
                double cTmp = 180 - (aTmp + b);
                if (checkAdditionalParams(AC, ACTmp) && checkAdditionalParams(a, aTmp) && checkAdditionalParams(c, cTmp)) {
                    AC = ACTmp;
                    a = aTmp;
                    c = cTmp;
                    setPAndS();
                    result = 0.0;
                } else {
                    result = -2.0;
                }
            }
        } else if (BC!=0 && AC!=0 && c!=0){
            if (c > 179.998){
                result = -1.0;
            } else {
                double ABTmp = getSideByTwoSidesAndAngleBetween(BC, AC, c);
                double bTmp = getAngleWithThreeSides(BC, ABTmp , AC);
                double aTmp = 180 - (bTmp + c);
                if (checkAdditionalParams(AB, ABTmp) && checkAdditionalParams(b, bTmp) && checkAdditionalParams(a, aTmp)) {
                    AB = ABTmp;
                    b = bTmp;
                    a = aTmp;
                    setPAndS();
                    result = 0.0;
                } else {
                    result = -2.0;
                }
            }
        } else if (AB!=0 && AC!=0 && a !=0){
            if (a > 179.998){
                result = -1.0;
            } else {
                double BCTmp = getSideByTwoSidesAndAngleBetween(AB, AC, a);
                double bTmp = getAngleWithThreeSides(BCTmp, AB, AC);
                double cTmp = 180 - (a + bTmp);
                if (checkAdditionalParams(BC, BCTmp) && checkAdditionalParams(b, bTmp) && checkAdditionalParams(c, cTmp)) {
                    BC = BCTmp;
                    b = bTmp;
                    c = cTmp;
                    setPAndS();
                    result = 0.0;
                } else {
                    result = -2.0;
                }
            }
        }
        setParams();
    }

    private Double getSideByTwoSidesAndAngleBetween(Double x, Double y, Double ang) {
        return Math.sqrt(x * x + y * y - 2 * x * y * Math.cos(Math.toRadians(ang)));
    }

    private void countByThreeSides() {
        if (AB > BC+AC || BC > AB+AC || AC > AB+BC){
            result = -1.0;
        }
        else {
            double aTmp = getAngleWithThreeSides(AC, AB, BC);
            double bTmp = getAngleWithThreeSides(AB, BC, AC);
            double cTmp = 180.0 - (aTmp + bTmp);
            if (checkAdditionalParams(a, aTmp) && checkAdditionalParams(b, bTmp) &&  checkAdditionalParams(c, cTmp)){
                a = aTmp;
                b = bTmp;
                c = cTmp;
                setPAndS();
                result = 0.0;
            } else {
                result = -2.0;
            }
        }
        setParams();
    }
    private Double getAngleWithThreeSides(double x, double y, double z) {
        double cos = (x*x + y*y - z*z)/(2*x*y);
        cos = Math.rint(100000*cos) / 100000;
        return Math.toDegrees(Math.acos(cos));
    }
    private void setPAndS() {
        findPerimeter();
        findSquare();
    }
    private void findSquare() {
        double p = P/2;
        S = Math.sqrt(p * (p-AB) * (p-AC) * (p-BC));
    }
    private void findPerimeter() {
        P = AB + BC + AC;
    }
    private void getParamsAndRoundToThreeDecimalPlaces(ArrayList<Double> sidesAndAngles) {
        AB = sidesAndAngles.get(0);
        a = sidesAndAngles.get(1);
        BC = sidesAndAngles.get(2);
        b = sidesAndAngles.get(3);
        AC = sidesAndAngles.get(4);
        c = sidesAndAngles.get(5);
        P = 0.0;
        S = 0.0;
    }
    private void setParams() {
        res = new ArrayList<>();
        res.add(result);
        if (result!=-1.0 && result !=-2.0){
            res.add(rint(AB));
            res.add(rint(a));
            res.add(rint(BC));
            res.add(rint(b));
            res.add(rint(AC));
            res.add(rint(c));
            res.add(rint(P));
            res.add(rint(S));
        }
    }
    private boolean checkAdditionalParams(double x, double xTmp){
        return x == 0.0 || rint(x)==rint(xTmp) || rint(x)==Math.floor(xTmp*FORMAT) / FORMAT;
    }
    private Double rint(double x){
        return Math.rint(FORMAT * x) / FORMAT;
    }
}
