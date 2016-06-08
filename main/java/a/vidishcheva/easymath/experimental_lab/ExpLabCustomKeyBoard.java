package a.vidishcheva.easymath.experimental_lab;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import a.vidishcheva.easymath.R;

/**
 * Created by Алёна on 12.04.2016.
 */
public class ExpLabCustomKeyBoard {

    public final static int CodeCancel   = -3;
    public final static int CodeDelete = -5;
    KeyboardView oneKeyboardView;
    private Activity onePowerEquation;
    EditText editText;
    ExpEditTextController etController;

    private KeyboardView.OnKeyboardActionListener oneOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener(){

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = editText.getText();
            int start = editText.getSelectionStart();

            if (primaryCode == CodeCancel){
                hideCustomKeyboard();
            } else if (primaryCode == CodeDelete){
                if( editable!=null && start>0 ) editable.delete(start - 1, start);
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
                etController.checkString();
            }
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeUp() {
        }
    };

    public ExpLabCustomKeyBoard(Activity activity, int viewid, int layoutid) {
        onePowerEquation = activity;
        oneKeyboardView = (KeyboardView) activity.findViewById(R.id.keyboardview);
        Keyboard oneKeyboard = new Keyboard(activity, R.xml.keyboard);
        oneKeyboardView.setKeyboard(oneKeyboard);
        oneKeyboardView.setPreviewEnabled(false);
        oneKeyboardView.setOnKeyboardActionListener(oneOnKeyboardActionListener);
        editText = (EditText) activity.findViewById(R.id.one_power_eq);
        etController = new ExpEditTextController(onePowerEquation, editText);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) showCustomKeyboard(v);
                else hideCustomKeyboard();
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });


        oneKeyboardView.setOnKeyboardActionListener(oneOnKeyboardActionListener);

        //InputFilter inputF = new InputFilterForOnePowerEquation();
        //editText.setFilters(new InputFilter[]{inputF});

    }

    public void hideCustomKeyboard() {
        oneKeyboardView.setVisibility(View.GONE);
        oneKeyboardView.setEnabled(false);
    }

    public void showCustomKeyboard( View v ) {
        oneKeyboardView.setVisibility(View.VISIBLE);
        oneKeyboardView.setEnabled(true);
        if( v!=null ) ((InputMethodManager) onePowerEquation.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public boolean isCustomKeyboardVisible() {
        return oneKeyboardView.getVisibility() == View.VISIBLE;
    }
}
