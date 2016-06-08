package a.vidishcheva.easymath.experimental_lab;

import android.app.Activity;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Алёна on 13.04.2016.
 */
public class ExpEditTextController {

    Activity activity;
    EditText editText;
    ArrayList<Character> symbols;

    public ExpEditTextController(Activity activity, EditText editText) {
        this.activity = activity;
        this.editText = editText;
        symbols = new ArrayList<>();
        symbols.add('.');
        symbols.add('+');
        symbols.add('-');
        symbols.add('*');
        symbols.add('/');
        symbols.add('^');
        symbols.add('=');
    }
    public void checkString(){
        String strToCheck = editText.getText().toString();
        char a = strToCheck.charAt(strToCheck.length() - 1);

        if (strToCheck.length()==1){
            checkStringForLength1(strToCheck);
        } else if (a =='.') {
            checkDots(strToCheck);
        } else if (a == ')' || a == '(') {
            checkBraces(strToCheck);
        } else if (a == '='){
            checkEqual(strToCheck);
        } else if (symbols.contains(a)){
            checkSymbols(strToCheck);
        } else if (Character.isDigit(a) || a == 'x'){
            checkDigits(strToCheck);
        }
    }
    private void checkDigits(String strToCheck) {
        char tmp = strToCheck.charAt(strToCheck.length() - 1);
        if (strToCheck.charAt(strToCheck.length()-2) == ')' || (tmp == 'x' && Character.isDigit(strToCheck.charAt(strToCheck.length()-2))) ||
                (Character.isDigit(tmp) && strToCheck.charAt(strToCheck.length()-2) == 'x')){
            strToCheck = strToCheck.substring(0, strToCheck.length()-1) + "*" + tmp;
            setTextAndSelection(strToCheck);
        }
    }
    private void checkEqual(String strToCheck) {
        if (strToCheck.indexOf('=') != strToCheck.lastIndexOf('=')){
            strToCheck = strToCheck.substring(0, strToCheck.length()-1);
            setTextAndSelection(strToCheck);
            return;
        }
        if (symbols.contains(strToCheck.charAt(strToCheck.length()-2))){
            strToCheck = strToCheck.substring(0, strToCheck.length()-2) + strToCheck.charAt(strToCheck.length() - 1);
        }
        int brCount = checkBracesCount(strToCheck);
        if (brCount > 0){
            strToCheck = strToCheck.substring(0, strToCheck.length()-1);
            for (int i = 0; i< brCount; i++){
                strToCheck += ')';
            }
            strToCheck = strToCheck + "=";
            setTextAndSelection(strToCheck);
        }
    }
    private void checkBraces(String strToCheck) {
        if (strToCheck.charAt(strToCheck.length()-1)== '('){
            char tmp = strToCheck.charAt(strToCheck.length()-2);
            if (tmp == ')' || tmp == '(' || Character.isDigit(tmp) || tmp == 'x'){
                strToCheck = strToCheck.substring(0, strToCheck.length()-1) + "*" + strToCheck.charAt(strToCheck.length()-1);
                setTextAndSelection(strToCheck);
            }
            return;
        }
        int braces = checkBracesCount(strToCheck);
        if (braces<0){
            strToCheck = strToCheck.substring(0, strToCheck.length()-1);
            setTextAndSelection(strToCheck);
            return;
        } else {
            checkSymbols(strToCheck);
        }
    }
    private void checkSymbols(String strToCheck) {
        char tmp = strToCheck.charAt(strToCheck.length()-1);

        if (strToCheck.charAt(strToCheck.length()-2) == '='){
            strToCheck = strToCheck.substring(0, strToCheck.length()-1) + "0" + tmp;
            setTextAndSelection(strToCheck);
        } else if (symbols.contains(strToCheck.charAt(strToCheck.length()-2))){
            strToCheck = strToCheck.substring(0, strToCheck.length()-2) + strToCheck.charAt(strToCheck.length() - 1);
            setTextAndSelection(strToCheck);
        } else if (strToCheck.charAt(strToCheck.length()-2) == '('){
            strToCheck = strToCheck.substring(0, strToCheck.length()-1) + "0" + tmp;
            setTextAndSelection(strToCheck);
        }
    }
    private void checkDots(String strToCheck) {
        strToCheck = strToCheck.substring(0, strToCheck.length()-1);
        char tmp = strToCheck.charAt(strToCheck.length() - 1);
        if (!Character.isDigit(tmp) ){
            if (tmp == ')' || tmp == 'x') {
                strToCheck = strToCheck + "*0.";
            } else if (tmp != '.'){
                strToCheck = strToCheck + "0.";
            }
            setTextAndSelection(strToCheck);
            return;
        }
        int dot = strToCheck.lastIndexOf(".");
        if (dot == -1){
            return;
        }
        for (int i = 0; i < symbols.size(); i++){
            if (dot < strToCheck.lastIndexOf(symbols.get(i))){
                return;
            }
        }
        if (dot < strToCheck.lastIndexOf(")")){
            return;
        } else if (dot < strToCheck.lastIndexOf("(")){
            return;
        } else {
            setTextAndSelection(strToCheck);;
        }
    }
    private void checkStringForLength1(String strToCheck) {
        if (symbols.contains(strToCheck.charAt(0))){
            strToCheck = "0" + strToCheck;
            setTextAndSelection(strToCheck);
        } else if(strToCheck.charAt(0)==')'){
            strToCheck = "";
            setTextAndSelection(strToCheck);
        }
    }
    public static int checkBracesCount(String strToCheck) {
        String[] arr = strToCheck.split("");
        int open = 0;
        int close = 0;
        for (int i = 0; i<arr.length; i++){
            if (arr[i].equals("(")){
                open++;
            } else if (arr[i].equals(")")){
                close++;
            }
        }
        return open-close;
    }
    private void setTextAndSelection(String str){
        editText.setText(str);
        editText.setSelection(str.length());
    }
}
