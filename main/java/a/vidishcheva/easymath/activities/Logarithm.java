package a.vidishcheva.easymath.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import a.vidishcheva.easymath.adapters_listeners.EditTextFocusSettings;
import a.vidishcheva.easymath.adapters_listeners.Localization;
import a.vidishcheva.easymath.activities_helpers.LogarithmHelper;
import a.vidishcheva.easymath.adapters_listeners.OnEnterKeyListener;
import a.vidishcheva.easymath.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Logarithm extends Activity {

    private static final String LOG_STRING = "log_string";
    private static final java.lang.String IS_OPEN = "is_open";
    private static final String LOG_ACTIVATED = "log_activated";
    private SlidingPaneLayout sp;
    private LinearLayout ll;
    private LogarithmHelper lh;
    private ArrayList<EditText> editTextsLog;
    private ArrayList<EditText> editTextsLn;
    private TextView text;
    private Button buttonCountLog;
    private Button buttonClearLog;
    private Button buttonCountLn;
    private Button buttonClearLn;
    private TextView rules;
    private Localization loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logarithm);
        loc = new Localization(this);
        loc.setLocalization();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_activity_logarithm);
        defineAllFields();
        sp.closePane();
        ll.setOnFocusChangeListener(new EditTextFocusSettings(this));
        for (int i = 0; i< ll.getChildCount(); i++){
            ll.getChildAt(i).setOnFocusChangeListener(new EditTextFocusSettings(this));
        }
        editTextsLog = new ArrayList<>();
        editTextsLog.add((EditText) findViewById(R.id.log_base));
        editTextsLog.add((EditText) findViewById(R.id.log_num));
        EditText etLog = (EditText) findViewById(R.id.log_result);
        etLog.setOnEditorActionListener(new OnEnterKeyListener(buttonCountLog));
        editTextsLog.add(etLog);
        editTextsLn = new ArrayList<>();
        editTextsLn.add((EditText) findViewById(R.id.ln_num));
        EditText etLn = (EditText) findViewById(R.id.ln_result);
        etLn.setOnEditorActionListener(new OnEnterKeyListener(buttonCountLn));
        editTextsLn.add(etLn);
        if (savedInstanceState != null){
            buttonCountLog.setText(R.string.button_count);
            buttonClearLog.setText(R.string.button_clear);
            buttonCountLn.setText(R.string.button_count);
            buttonClearLn.setText(R.string.button_clear);
            rules.setText(R.string.log_formulas);
            if (!savedInstanceState.getBoolean(IS_OPEN)){
                sp.closePane();
            }
            ArrayList<CharSequence> save = (ArrayList<CharSequence>) savedInstanceState.get(LOG_STRING);
            boolean[] activated = (boolean[]) savedInstanceState.get(LOG_ACTIVATED);
            for (int i = 0; i < editTextsLog.size(); i++){
                editTextsLog.get(i).setText(save.get(i));
                editTextsLog.get(i).setActivated(activated[i]);
                }
            for (int i = editTextsLog.size(); i < editTextsLn.size()+editTextsLog.size(); i++){
                editTextsLn.get(i-editTextsLog.size()).setText(save.get(i-editTextsLog.size()));
                editTextsLn.get(i-editTextsLog.size()).setActivated(activated[i-editTextsLog.size()]);
            }
            text.setText(save.get(save.size()-1));
        }
    }
    private void defineAllFields() {
        sp = (SlidingPaneLayout) findViewById(R.id.sliding_panel_log);
        lh = new LogarithmHelper();
        ll = (LinearLayout) findViewById(R.id.log_layout);
        buttonCountLog = (Button) findViewById(R.id.button_count_log);
        buttonClearLog = (Button) findViewById(R.id.button_clear_log);
        buttonCountLn = (Button) findViewById(R.id.button_count_ln);
        buttonClearLn = (Button) findViewById(R.id.button_clear_ln);
        text = (TextView) findViewById(R.id.log_todo);
        rules = (TextView) findViewById(R.id.rules_text_view);
    }
    public void onClickLog(View view){
        ArrayList<Double> params = new ArrayList<>();
        for (int i = 0; i<editTextsLog.size(); i++){
            if (editTextsLog.get(i).getText().toString().equals("")) {
                params.add(0.0);
            } else {
                params.add(Double.valueOf(editTextsLog.get(i).getText().toString()));
            }
        }
        ArrayList<Double> result = lh.countLog(params);
        switch(result.get(0).intValue()){
            case 0:
                text.setText(R.string.log_todo);
                for (int i = 0; i<editTextsLog.size(); i++){
                    DecimalFormat decimalFormat=new DecimalFormat("#.####");
                    editTextsLog.get(i).setText(decimalFormat.format(result.get(i+1)));
                }
                break;
            case -1:
                text.setText(R.string.log_wrong);
                break;
            case -2:
                text.setText(R.string.log_correct);
                break;
            case -3:
                text.setText(R.string.log_not_log);
                break;
            case -4:
                text.setText(R.string.log_todo);
                break;
        }
    }
    public void onClickLogClear(View view){
        text.setText(R.string.log_todo);
        for (int i = 0; i<editTextsLog.size(); i++){
            editTextsLog.get(i).setText("");
        }
    }
    public void onClickLn(View view){
        ArrayList<Double> params = new ArrayList<>();
        for (int i = 0; i<editTextsLn.size(); i++){
            if (editTextsLn.get(i).getText().toString().equals("")) {
                params.add(0.0);
            } else {
                params.add(Double.valueOf(editTextsLn.get(i).getText().toString()));
            }
        }
        ArrayList<Double> result = lh.countLn(params);
        switch(result.get(0).intValue()){
            case 0:
                text.setText(R.string.log_todo);
                for (int i = 0; i<editTextsLn.size(); i++){
                    DecimalFormat decimalFormat=new DecimalFormat("#.####");
                    editTextsLn.get(i).setText(decimalFormat.format(result.get(i + 1)));
                }
                break;
            case -1:
                text.setText(R.string.log_wrong);
                break;
            case -2:
                text.setText(R.string.log_correct);
                break;
            case -3:
                text.setText(R.string.log_not_ln);
                break;
            case -4:
                text.setText(R.string.log_todo);
                break;
        }
    }
    public void onClickLnClear(View view){
        text.setText(R.string.log_todo);
        for (int i = 0; i<editTextsLn.size(); i++){
            editTextsLn.get(i).setText("");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem info = menu.getItem(0);
        info.setIcon(R.mipmap.ic_info_18dp);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.info :
                if (sp.isOpen()) {
                    sp.closePane();
                } else {
                    sp.openPane();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<Boolean> activated = new ArrayList<>();
        ArrayList<CharSequence> save = new ArrayList<>();
        for (int i = 0; i < editTextsLog.size(); i++){
            save.add(editTextsLog.get(i).getText());
            activated.add(editTextsLog.get(i).isActivated());
        }
        for (int i = 0; i < editTextsLn.size(); i++){
            save.add(editTextsLn.get(i).getText());
            activated.add(editTextsLn.get(i).isActivated());
        }
        save.add(text.getText());
        outState.putCharSequenceArrayList(LOG_STRING, save);
        boolean[] act = new boolean[activated.size()];
        for (int i = 0; i<act.length; i++){
            act[i] = activated.get(i);
        }
        outState.putBooleanArray(LOG_ACTIVATED, act);
        outState.putBoolean(IS_OPEN, sp.isOpen());
        super.onSaveInstanceState(outState);
    }
}
