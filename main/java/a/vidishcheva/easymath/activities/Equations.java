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

import a.vidishcheva.easymath.activities_helpers.EquationsHelper;
import a.vidishcheva.easymath.R;
import a.vidishcheva.easymath.adapters_listeners.EditTextFocusSettings;
import a.vidishcheva.easymath.adapters_listeners.Localization;
import a.vidishcheva.easymath.adapters_listeners.OnEnterKeyListener;
import a.vidishcheva.easymath.adapters_listeners.SlidingPanelSettings;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Equations extends Activity {

    DecimalFormat decimalFormat;
    private static final String QE_STRING = "qe_string";
    private static final String BI_STRING = "bi_string";
    private static final String CUB_STRING = "cub_string";
    private static final String IS_OPEN = "is_open";
    private SlidingPaneLayout sp;
    private ArrayList<EditText> qeEditTexts;
    private ArrayList<EditText> biEditTexts;
    private ArrayList<EditText> cubEditTexts;
    private EquationsHelper eh;
    private TextView textQe;
    private TextView textBi;
    private TextView textCub;
    private LinearLayout ll;
    private Button qeButtonCount;
    private Button biButtonCount;
    private Button cubButtonCount;
    private Button qeButtonClear;
    private Button biButtonClear;
    private Button cubButtonClear;
    private Localization loc;
    private TextView rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equations);
        loc = new Localization(this);
        loc.setLocalization();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_activity_equations);
        defineAllFields();
        sp.closePane();
        sp.setPanelSlideListener(new SlidingPanelSettings(this));
        EditTextFocusSettings focusSettings = new EditTextFocusSettings(this);
        ll.setOnFocusChangeListener(focusSettings);
        for (int i = 0; i< ll.getChildCount(); i++){
            View view = ll.getChildAt(i);
            view.setOnFocusChangeListener(focusSettings);
        }
        if (savedInstanceState != null){
            qeButtonCount.setText(R.string.button_count);
            biButtonCount.setText(R.string.button_count);
            cubButtonCount.setText(R.string.button_count);
            qeButtonClear.setText(R.string.button_clear);
            biButtonClear.setText(R.string.button_clear);
            cubButtonClear.setText(R.string.button_clear);
            rules.setText(R.string.eq_formulas);
            ((TextView) findViewById(R.id.quadratic_equation)).setText(R.string.quad_eq);
            ((TextView) findViewById(R.id.biquadratic_equation)).setText(R.string.bi_quad_eq);
            ((TextView) findViewById(R.id.cubic_equation)).setText(R.string.cub_eq);

            ArrayList<CharSequence> save = (ArrayList<CharSequence>) savedInstanceState.get(QE_STRING);
            for (int i = 0; i < qeEditTexts.size(); i++){
                qeEditTexts.get(i).setText(save.get(i));
            }
            textQe.setText(save.get(save.size() - 1));

            save = (ArrayList<CharSequence>) savedInstanceState.get(BI_STRING);
            for (int i = 0; i < biEditTexts.size(); i++){
                biEditTexts.get(i).setText(save.get(i));
            }
            textBi.setText(save.get(save.size() - 1));

            save = (ArrayList<CharSequence>) savedInstanceState.get(CUB_STRING);
            for (int i = 0; i < cubEditTexts.size(); i++){
                cubEditTexts.get(i).setText(save.get(i));
            }
            textCub.setText(save.get(save.size() - 1));

            if (!savedInstanceState.getBoolean(IS_OPEN)){
                sp.closePane();
            } else {
                sp.openPane();
            }
        }
    }

    private void defineAllFields() {
        sp = (SlidingPaneLayout) findViewById(R.id.sliding_panel_qe);
        eh = new EquationsHelper();
        ll = (LinearLayout) findViewById(R.id.equation_layout);
        qeButtonCount = (Button) findViewById(R.id.qe_button_count);
        biButtonCount = (Button) findViewById(R.id.bi_button_count);
        cubButtonCount = (Button) findViewById(R.id.cub_button_count);
        qeButtonClear = (Button) findViewById(R.id.qe_button_clear);
        biButtonClear = (Button) findViewById(R.id.bi_button_clear);
        cubButtonClear = (Button) findViewById(R.id.cub_button_clear);
        textQe = (TextView) findViewById(R.id.qe_todo);
        textBi = (TextView) findViewById(R.id.four_todo);
        textCub = (TextView) findViewById(R.id.cub_todo);
        qeEditTexts = new ArrayList<>();
        qeEditTexts.add((EditText) findViewById(R.id.qe_first_num));
        qeEditTexts.add((EditText) findViewById(R.id.qe_second_num));
        qeEditTexts.add((EditText) findViewById(R.id.qe_third_num));
        EditText qeRes = (EditText) findViewById(R.id.qe_result);
        qeRes.setOnEditorActionListener(new OnEnterKeyListener(qeButtonCount));
        qeEditTexts.add(qeRes);

        biEditTexts = new ArrayList<>();
        biEditTexts.add((EditText) findViewById(R.id.four_first_num));
        biEditTexts.add((EditText) findViewById(R.id.four_second_num));
        biEditTexts.add((EditText) findViewById(R.id.four_third_num));
        EditText biRes = (EditText) findViewById(R.id.four_result);
        biRes.setOnEditorActionListener(new OnEnterKeyListener(biButtonCount));
        biEditTexts.add(biRes);

        cubEditTexts = new ArrayList<>();
        cubEditTexts.add((EditText) findViewById(R.id.cub_first_num));
        cubEditTexts.add((EditText) findViewById(R.id.cub_second_num));
        cubEditTexts.add((EditText) findViewById(R.id.cub_third_num));
        cubEditTexts.add((EditText) findViewById(R.id.cub_forth_num));
        EditText cubRes = (EditText) findViewById(R.id.cub_result);
        cubRes.setOnEditorActionListener(new OnEnterKeyListener(cubButtonCount));
        cubEditTexts.add(cubRes);

        decimalFormat=new DecimalFormat("#.#####");
        rules = (TextView) findViewById(R.id.rules_text_view);
    }

    public void onClickQECount(View view){
        ArrayList<Double> params = new ArrayList<>();
        for(int i = 0; i< qeEditTexts.size(); i++){
            if (qeEditTexts.get(i).getText().toString().equals("")){
                params.add(0.0);
            } else {
                params.add(Double.valueOf(qeEditTexts.get(i).getText().toString()));
            }
        }

        ArrayList<Double> result = eh.countQE(params);
        switch (result.get(0).intValue()){
            case 0:
                textQe.setText(getString(R.string.x1) + decimalFormat.format(result.get(1)) + "\n" + getString(R.string.x2) + decimalFormat.format(result.get(2)));
                break;
            case -1:
                textQe.setText(getString(R.string.x1) + getString(R.string.x2) + decimalFormat.format(result.get(1)));
                break;
            case -2:
                textQe.setText(R.string.no_solution);
                break;
            case -3:
                textQe.setText(R.string.not_an_eq);
                break;
        }
    }

    public void onClickFourCount(View view){
        ArrayList<Double> params = new ArrayList<>();
        for(int i = 0; i< biEditTexts.size(); i++){
            if (biEditTexts.get(i).getText().toString().equals("")){
                params.add(0.0);
            } else {
                params.add(Double.valueOf(biEditTexts.get(i).getText().toString()));
            }
        }

        ArrayList<Double> result = eh.countBI(params);
        switch (result.get(0).intValue()){
            case 0:
                textBi.setText(getString(R.string.x1) + decimalFormat.format(result.get(1)) + "\n" + getString(R.string.x2) + decimalFormat.format(result.get(2)));
                break;
            case -2:
                textBi.setText(R.string.no_solution);
                break;
            case -3:
                textBi.setText(R.string.not_an_eq);
                break;
            case -4:
                textBi.setText(getString(R.string.x1) + decimalFormat.format(result.get(1)) + "\n" + getString(R.string.x2) + decimalFormat.format(result.get(2)) +
                        "\n" +  getString(R.string.x3) + decimalFormat.format(result.get(3)) + "\n" + getString(R.string.x4) + decimalFormat.format(result.get(4)));
                break;
        }
    }

    public void onClickCubCount(View view){
        ArrayList<Double> params = new ArrayList<>();
        for(int i = 0; i< cubEditTexts.size(); i++){
            if (cubEditTexts.get(i).getText().toString().equals("")){
                params.add(0.0);
            } else {
                params.add(Double.valueOf(cubEditTexts.get(i).getText().toString()));
            }
        }

        ArrayList<Double> result = eh.countCub(params);
        switch (result.get(0).intValue()){
            case 0:
                textCub.setText(getString(R.string.x1) + decimalFormat.format(result.get(1)) + "\n" + getString(R.string.x2) + decimalFormat.format(result.get(2)));
                break;
            case -1:
                textCub.setText(getString(R.string.x1) + getString(R.string.x2) + decimalFormat.format(result.get(1)));
                break;
            case -3:
                textCub.setText(R.string.not_an_eq);
                break;
            case -5:
                textCub.setText(getString(R.string.x1) + decimalFormat.format(result.get(1)) + "\n" + getString(R.string.x2) + decimalFormat.format(result.get(2)) +
                        "\n" +  getString(R.string.x3) + decimalFormat.format(result.get(3)) );
                break;
            case -6:
                textCub.setText(getString(R.string.x1) + decimalFormat.format(result.get(1)) + "\n" + getString(R.string.x2) + decimalFormat.format(result.get(2)) +
                         " + i * (" + decimalFormat.format(result.get(3)) + ")" +  "\n" +  getString(R.string.x3) + decimalFormat.format(result.get(2)) +
                         " - i * (" + decimalFormat.format(result.get(3)) + ")");
                break;
        }
    }

    public void onClickQEClear(View view){
        textQe.setText(R.string.eq_todo);
        for(int i = 0; i< qeEditTexts.size(); i++){
            qeEditTexts.get(i).setText("");
        }
    }

    public void onClickFourClear(View view){
        textBi.setText(R.string.eq_todo);
        for(int i = 0; i< biEditTexts.size(); i++){
            biEditTexts.get(i).setText("");
        }
    }
    public void onClickCubClear(View view){
        textCub.setText(R.string.eq_todo);
        for(int i = 0; i< cubEditTexts.size(); i++){
            cubEditTexts.get(i).setText("");
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
        ArrayList<CharSequence> saveQe = new ArrayList<>();
        for (int i = 0; i < qeEditTexts.size(); i++){
            saveQe.add(qeEditTexts.get(i).getText());
        }
        saveQe.add(textQe.getText());

        ArrayList<CharSequence> saveBi = new ArrayList<>();
        for (int i = 0; i < biEditTexts.size(); i++){
            saveBi.add(biEditTexts.get(i).getText());
        }
        saveBi.add(textBi.getText());

        ArrayList<CharSequence> saveCub = new ArrayList<>();
        for (int i = 0; i < cubEditTexts.size(); i++){
            saveCub.add(cubEditTexts.get(i).getText());
        }
        saveCub.add(textCub.getText());

        outState.putCharSequenceArrayList(QE_STRING, saveQe);
        outState.putCharSequenceArrayList(BI_STRING, saveBi);
        outState.putCharSequenceArrayList(CUB_STRING, saveCub);
        outState.putBoolean(IS_OPEN, sp.isOpen());
        super.onSaveInstanceState(outState);
    }

}
