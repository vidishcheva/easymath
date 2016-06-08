package a.vidishcheva.easymath.activities;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;

import android.support.v4.widget.SlidingPaneLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import a.vidishcheva.easymath.adapters_listeners.EditTextFocusSettings;
import a.vidishcheva.easymath.adapters_listeners.Localization;
import a.vidishcheva.easymath.adapters_listeners.OnEnterKeyListener;
import a.vidishcheva.easymath.R;
import a.vidishcheva.easymath.adapters_listeners.SlidingPanelSettings;
import a.vidishcheva.easymath.activities_helpers.TrigonometryHelper;

import java.util.ArrayList;

public class Trigonometry extends Activity {

    private final String ANGLE_STRING = "angle_string";
    private final String IS_OPEN = "is_open";
    private final String V_SCROLL = "v_scroll";
    private final String H_SCROLL = "h_scroll";
    private SlidingPaneLayout sp;
    private GridLayout gl;
    private ScrollView vScrollView;
    private HorizontalScrollView hScrollView;
    private Localization loc;
    private TrigonometryHelper th;
    private EditText angle;
    private TextView rules;
    private Button buttonCount;
    private Button buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigonometry);
        loc = new Localization(this);
        loc.setLocalization();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_activity_trigonometry);
        defineAllFields();
        sp.closePane();
        sp.setPanelSlideListener(new SlidingPanelSettings(this));
        if (savedInstanceState != null){
            buttonCount.setText(R.string.button_count);
            buttonClear.setText(R.string.button_clear);
            rules.setText(R.string.trig_formulas);
            if (savedInstanceState.getBoolean(IS_OPEN)){
                sp.openPane();
            } else {
                sp.closePane();
            }
            ArrayList<CharSequence> res = (ArrayList<CharSequence>) savedInstanceState.get(ANGLE_STRING);
            for (int i = 1; i < gl.getChildCount()-2; i++){
                if (i%3 != 0){
                    TextView textView = (TextView) gl.getChildAt(i);
                    textView.setText(res.get(i));
                }
            }
            final int[] vPosition = savedInstanceState.getIntArray(V_SCROLL);
            if(vPosition != null)
                vScrollView.post(new Runnable() {
                    public void run() {
                        vScrollView.scrollTo(vPosition[0], vPosition[1]);
                    }
                });

            final int[] hPosition = savedInstanceState.getIntArray(H_SCROLL);
            if(hPosition != null)
                hScrollView.post(new Runnable() {
                    public void run() {
                        hScrollView.scrollTo(hPosition[0], hPosition[1]);
                    }
                });
        }
        angle.setOnFocusChangeListener(new EditTextFocusSettings(this));
        angle.setOnEditorActionListener(new OnEnterKeyListener(buttonCount));
    }
    private void defineAllFields() {
        angle = (EditText) findViewById(R.id.angle_grad);
        rules = (TextView) findViewById(R.id.rules_text_view);
        vScrollView = (ScrollView) findViewById(R.id.v_scroll);
        hScrollView = (HorizontalScrollView) findViewById(R.id.h_scroll);
        gl = (GridLayout) findViewById(R.id.trig_layout);
        sp = (SlidingPaneLayout) findViewById(R.id.sliding_panel_trig);
        buttonCount = (Button) findViewById(R.id.click_trig);
        buttonClear = (Button) findViewById(R.id.clear_trig);
        th = new TrigonometryHelper();
    }
    public void onClickTrig(View view){
        if (angle.getText().toString().equals("")){
            angle.setText("0");
            th.setValues(0, gl);
        } else {
            Double ang = Double.valueOf(String.valueOf(angle.getText()));
            if (ang < 0){
                ang = ang % 360;
                ang = 360+ang;
            }
            if (ang > 360){
                ang = ang % 360;
            }
            th.setValues(ang, gl);
        }

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void onClickClear(View view){
        for (int i = 1; i< gl.getChildCount()-2; i++){
            if (i%3 != 0){
                TextView tw = (TextView) gl.getChildAt(i);
                tw.setText("");
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<CharSequence> res = new ArrayList<>();
        for (int i = 0; i < gl.getChildCount()-2; i++){
            if (i%3 != 0){
                TextView textView = (TextView) gl.getChildAt(i);
                res.add(i, textView.getText());
            } else {
                res.add(i, "");
            }
        }
        outState.putCharSequenceArrayList(ANGLE_STRING, res);
        outState.putBoolean("IS_OPEN", sp.isOpen());
        outState.putIntArray(V_SCROLL,
                new int[]{vScrollView.getScrollX(), vScrollView.getScrollY()});
        outState.putIntArray(H_SCROLL,
                new int[]{ hScrollView.getScrollX(), hScrollView.getScrollY()});
        super.onSaveInstanceState(outState);
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
}