package a.vidishcheva.easymath.activities_helpers;

import android.widget.GridLayout;
import android.widget.TextView;

import a.vidishcheva.easymath.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Алёна on 29.03.2016.
 */
public class TrigonometryHelper {

    private final String FORMAT = "#0.00000";
    private final int form = 100000;
    private double infinity = 1/Math.tan(0);
    final static char gradSign = 176;
    final static char radic = 8730;
    final static char piSign = 960;
    final String COS = "cos";
    final String SIN = "sin";
    final String TG = "tg";
    final String CTG = "ctg";
    final String PI = "pi";
    int grad;
    int minutes;
    double seconds;
    Map<Integer, Map<String, String>> popAngles;
    Map<String, String> part;
    {
        popAngles = new HashMap<>();
        part = new HashMap<String, String>(); // 30
        part.put(COS, radic + "3/2");
        part.put(TG, " = " + radic + "3/3");
        part.put(CTG, " = " + radic + "3");
        part.put(PI, " = " + piSign + "/6");
        popAngles.put(30, part);
        part = new HashMap<String, String>(); // 45
        part.put(SIN, radic + "2/2");
        part.put(COS, radic + "2/2");
        part.put(PI, " = " + piSign + "/4");
        popAngles.put(45, part);
        part = new HashMap<String, String>(); // 60
        part.put(SIN, radic + "3/2");
        part.put(TG, " = " + radic + "3");
        part.put(CTG, " = " + radic + "3/3");
        part.put(PI, " = " + piSign + "/3");
        popAngles.put(60, part);
        part = new HashMap<String, String>(); //120
        part.put(SIN, radic + "3/2");
        part.put(TG, " = - " + radic + "3");
        part.put(CTG, " = - " + radic + "3/3");
        part.put(PI, " = 2" + piSign + "/3");
        popAngles.put(120, part);
        part = new HashMap<String, String>(); //135
        part.put(SIN, radic + "2/2");
        part.put(COS, radic + "2/2");
        part.put(PI, " = 3" + piSign + "/4");
        popAngles.put(135, part);
        part = new HashMap<String, String>(); //150
        part.put(COS, radic + "3/2");
        part.put(TG, " = - " + radic + "3/3");
        part.put(CTG, " = - " + radic + "3");
        part.put(PI, " = 5" + piSign + "/6");
        popAngles.put(150, part);
        part = new HashMap<String, String>(); //210
        part.put(PI, " = 7" + piSign + "/6");
        popAngles.put(210, part);
        part = new HashMap<String, String>(); //225
        part.put(PI, " = 5" + piSign + "/4");
        popAngles.put(225, part);
        part = new HashMap<String, String>(); //240
        part.put(PI, " = 4" + piSign + "/3");
        popAngles.put(240, part);
        part = new HashMap<String, String>(); //270
        part.put(PI, " = 3" + piSign + "/2");
        popAngles.put(270, part);
        part = new HashMap<String, String>(); //300
        part.put(PI, " = 5" + piSign + "/3");
        popAngles.put(300, part);
        part = new HashMap<String, String>(); //315
        part.put(PI, " = 7" + piSign + "/4");
        popAngles.put(315, part);
        part = new HashMap<String, String>(); //330
        part.put(PI, " = 11" + piSign + "/6");
        popAngles.put(330, part);
    }
    public String getMinutesSeconds(double angle){
        grad = (int) angle;
        double rest = angle % 1;
        double mins = Math.abs(rest*60);
        minutes = (int) mins;
        seconds = (mins % 1) * 60;
        seconds = getDoubleFormat(seconds, 100);
        return " = " + grad + gradSign + " " + minutes+ "\" " + String.valueOf(seconds) + "\'";
    }
    public void setValues(double ang, GridLayout gl){
        double rad = Math.toRadians(ang);
        for (int i = 2; i<gl.getChildCount()-2; i++){
            if (i%3 != 0){
                TextView tw = (TextView) gl.getChildAt(i);
                tw.setText("");
                switch (tw.getId()) {
                    case (R.id.angle_min_sec) :{
                        tw.setText(getMinutesSeconds(ang));
                        break;}
                    case (R.id.angle_rad) : {
                        tw.setText(new DecimalFormat(FORMAT).format(rad));
                        break;
                    }
                    case (R.id.angle_pi) : {
                        if (ang == 30 || ang == 45 || ang == 60 || ang == 120 ||
                                ang == 135 || ang == 150 || ang == 210 || ang == 225 ||
                                ang == 240 || ang == 270 || ang == 300 || ang == 315 || ang == 330){
                            tw.setText(popAngles.get((int)ang).get(PI));
                        } else {
                            double radPi = getDoubleFormat(rad / Math.PI, form);
                            tw.setText(" = " + String.valueOf(radPi) + " " + piSign);
                        }
                        break;
                    }
                    case (R.id.sin) : {
                        double sin = Math.sin(rad);
                        sin = getDoubleFormat(sin, form);
                        tw.setText(String.valueOf(sin));
                        break;
                    }
                    case (R.id.sin_radic) : {
                        if (ang == 45 || ang == 60 || ang == 120 || ang == 135){
                            tw.setText(" = " + popAngles.get((int)ang).get(SIN));
                        }
                        if (ang == 225 || ang == 240 || ang == 300 || ang == 315){
                            tw.setText(" = - " + popAngles.get((int)ang-180).get(SIN));
                        }
                        break;
                    }
                    case (R.id.cos) : {
                        double cos = Math.cos(rad);
                        cos = getDoubleFormat(cos, form);
                        tw.setText(String.valueOf(cos));
                        break;
                    }
                    case (R.id.cos_radic) : {
                        if (ang == 30 || ang == 45){
                            tw.setText(" = " + popAngles.get((int)ang).get(COS));
                        }
                        if (ang == 135 || ang == 150){
                            tw.setText(" = - " + popAngles.get((int) ang).get(COS));
                        }
                        if (ang == 210 || ang == 225){
                            tw.setText(" = - " + popAngles.get((int)ang-180).get(COS));
                        }
                        if (ang == 315 || ang == 330){
                            tw.setText(" = " + popAngles.get((int)ang-180).get(COS));
                        }
                        break;
                    }
                    case (R.id.tg) : {
                        double tg = Math.tan(rad);
                        tg = getDoubleFormat(tg, form);
                        if (tg > form || tg < -form) {
                            tw.setText(new DecimalFormat(FORMAT).format(infinity));
                        } else {
                            tw.setText(String.valueOf(tg));
                        }
                        break;
                    }
                    case (R.id.tg_radic) : {
                        if (ang == 30 || ang == 60 || ang == 120 || ang == 150){
                            tw.setText(popAngles.get((int)ang).get(TG));
                        }
                        if (ang == 210 || ang == 240 || ang == 300 || ang == 330){
                            tw.setText(popAngles.get((int)ang-180).get(TG));
                        }
                        break;
                    }
                    case (R.id.ctg) : {
                        double ctg = 1/Math.tan(rad);
                        ctg = getDoubleFormat(ctg, form);
                        if (ctg > form || ctg < -form) {
                            tw.setText(new DecimalFormat(FORMAT).format(infinity));
                        } else {
                            tw.setText(String.valueOf(ctg));
                        }
                        break;
                    }
                    case (R.id.ctg_radic) : {
                        if (ang == 30 || ang == 60 || ang == 120 || ang == 150){
                            tw.setText(popAngles.get((int)ang).get(CTG));
                        }
                        if (ang == 210 || ang == 240 || ang == 300 || ang == 330){
                            tw.setText(popAngles.get((int)ang-180).get(CTG));
                        }
                        break;
                    }
                }
            }
        }
    }
    public double getDoubleFormat(double a, int form){
        return Math.rint(form * a) / form;
    }
}
