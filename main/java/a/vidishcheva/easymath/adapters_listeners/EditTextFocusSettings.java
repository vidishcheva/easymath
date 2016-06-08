package a.vidishcheva.easymath.adapters_listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Алёна on 17.05.2016.
 */
public class EditTextFocusSettings implements View.OnFocusChangeListener {

    Activity activity;

    public EditTextFocusSettings(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        System.out.println(v.toString());
        if (v instanceof EditText && !hasFocus){
            hideKeyboard(v);
        }
        if (v instanceof GridLayout && hasFocus) {
            hideKeyboard(v);
        }
        if (v instanceof LinearLayout && hasFocus) {
            hideKeyboard(v);
        }
        if (v instanceof TextView && hasFocus) {
            hideKeyboard(v);
        }
    }
    private void hideKeyboard(View v) {
            InputMethodManager imm = (InputMethodManager) v.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
