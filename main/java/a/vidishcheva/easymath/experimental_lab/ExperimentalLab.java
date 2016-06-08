package a.vidishcheva.easymath.experimental_lab;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import a.vidishcheva.easymath.R;
import a.vidishcheva.easymath.adapters_listeners.Localization;

import java.util.HashSet;

public class ExperimentalLab extends Activity {

    private SlidingPaneLayout sp;
    EditText editText;
    TextView textView;
    ExpLabCustomKeyBoard oneKeyboard;
    ExpCounter expCounter;
    private Localization loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_power_equation);
        loc = new Localization(this);
        loc.setLocalization();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_activity_one_power_equation);
        textView = (TextView) findViewById(R.id.one_todo);
        editText = (EditText) findViewById(R.id.one_power_eq);
        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        oneKeyboard = new ExpLabCustomKeyBoard(this, R.id.keyboardview, R.xml.keyboard);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override public void onBackPressed() {
        if( oneKeyboard.isCustomKeyboardVisible() ) oneKeyboard.hideCustomKeyboard(); else this.finish();
    }

    public void onClickOnePower(View view){
        String a = editText.getText().toString();
        expCounter = new ExpCounter(editText, a);
        HashSet<String> results = expCounter.parseString();
        String res = "";
        for (String b : results){
            res += " " + b;
        }
        textView.setText(res);
    }

    public void onClickOnePowerClear(View view){
        editText.setText("");
        textView.setText("");
    }
}
