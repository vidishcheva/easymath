package a.vidishcheva.easymath.activities;

import android.app.ActionBar;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import a.vidishcheva.easymath.R;

import a.vidishcheva.easymath.adapters_listeners.EditTextFocusSettings;
import a.vidishcheva.easymath.adapters_listeners.Localization;
import a.vidishcheva.easymath.adapters_listeners.OnEnterKeyListener;
import a.vidishcheva.easymath.adapters_listeners.SlidingPanelSettings;
import a.vidishcheva.easymath.activities_helpers.CircleHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Circle extends Activity {

    private final String V_SCROLL = "v_scroll";
    private final String IS_OPEN = "is_open";
    private final String CIRCLE_STRING = "circle_string";
    private SlidingPaneLayout sp;
    private GridLayout gl;
    private ScrollView vScrollView;
    private EditTextFocusSettings etfs;
    private TextView rules;
    private TextView textView;
    private Button buttonCount;
    private Button buttonClear;
    private Localization loc;
    private CircleHelper cH;
    private ArrayList<Double> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        loc = new Localization(this);
        loc.setLocalization();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_activity_circle);
        defineAllFields();
        sp.closePane();
        sp.setPanelSlideListener(new SlidingPanelSettings(this));
        gl.setOnFocusChangeListener(etfs);
        setDimentions();
        for (int i = 3; i < gl.getChildCount()-2; i+=2){
            EditText textView = (EditText) gl.getChildAt(i);
            textView.setOnEditorActionListener(new OnEnterKeyListener(buttonCount));
        }
        if (savedInstanceState != null){
            buttonCount.setText(R.string.button_count);
            buttonClear.setText(R.string.button_clear);
            rules.setText(R.string.circle_formulas);
            ArrayList<CharSequence> res = (ArrayList<CharSequence>) savedInstanceState.get(CIRCLE_STRING);
            textView.setText(res.get(res.size()-1));
            for (int i = 3, j=0; i < gl.getChildCount()-2; i+=2, j++){
                    TextView textView = (TextView) gl.getChildAt(i);
                    textView.setText(res.get(j));
            }
            final int[] vPosition = savedInstanceState.getIntArray(V_SCROLL);
            if(vPosition != null)
                vScrollView.post(new Runnable() {
                    public void run() {
                        vScrollView.scrollTo(vPosition[0], vPosition[1]);
                    }
                });

            if (savedInstanceState.getBoolean(IS_OPEN)){
                sp.openPane();
            } else {
                sp.closePane();
            }
        }
    }
    private void defineAllFields() {
        sp = (SlidingPaneLayout) findViewById(R.id.sliding_panel_circle);
        vScrollView = (ScrollView) findViewById(R.id.v_scroll);
        cH = new CircleHelper();
        gl = (GridLayout) findViewById(R.id.circle_layout);
        etfs = new EditTextFocusSettings(this);
        textView = (TextView) findViewById(R.id.circle_todo);
        buttonCount = (Button) findViewById(R.id.button_count);
        buttonClear = (Button) findViewById(R.id.button_clear);
        rules = (TextView) findViewById(R.id.rules_text_view);
    }
    public void onClickCircle(View view){
        params = new ArrayList<>();
        String text = "";
        for (int i = 3; i<gl.getChildCount()-1; i+=2) {
            TextView tw = (TextView) gl.getChildAt(i);
            text = tw.getText().toString();
            if (text.equals("")) {
                params.add(0.0);
            } else {
                params.add(Double.valueOf(tw.getText().toString()));
            }
        }
        ArrayList<Double> res = cH.findCircle(params);
        if (res.get(0)==-1.0){
            textView.setText(getResources().getString(R.string.circle_incorrect_input));
        } else if (res.get(0) == -2.0){
            textView.setText(getResources().getString(R.string.circle_todo));
        } else if (res.get(0) == 0.0){
            DecimalFormat decimalFormat=new DecimalFormat("#.#####");
            textView.setText(getResources().getString(R.string.circle_todo));
            for (int i = 3, j = 1; i < gl.getChildCount()-1; i+=2, j++) {
                TextView tw = (TextView) gl.getChildAt(i);
                tw.setText(decimalFormat.format(res.get(j)));
            }
        }
    }
    public void onClickCircleClear(View view){
        textView.setText(getResources().getString(R.string.circle_todo));
        for (int i = 3, j = 0; i<gl.getChildCount()-1; i+=2, j++) {
            TextView tw = (TextView) gl.getChildAt(i);
            tw.setText("");
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
        ArrayList<CharSequence> res = new ArrayList<>();
        for (int i = 3; i < gl.getChildCount()-2; i+=2){
                TextView textView = (TextView) gl.getChildAt(i);
                res.add(textView.getText());
        }
        res.add(textView.getText());
        outState.putCharSequenceArrayList(CIRCLE_STRING, res);
        outState.putBoolean("IS_OPEN", sp.isOpen());
        outState.putIntArray(V_SCROLL,
                new int[]{vScrollView.getScrollX(), vScrollView.getScrollY()});
        super.onSaveInstanceState(outState);
    }
    private void setDimentions(){
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        ImageView iv = (ImageView) findViewById(R.id.circle_image);
        iv.setMaxWidth(width / 2);
        textView.setMaxWidth(width / 2 - (int) (0.1 * width));
    }

}
