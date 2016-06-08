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
import android.widget.TextView;

import a.vidishcheva.easymath.adapters_listeners.EditTextFocusSettings;
import a.vidishcheva.easymath.adapters_listeners.Localization;
import a.vidishcheva.easymath.adapters_listeners.OnEnterKeyListener;
import a.vidishcheva.easymath.R;
import a.vidishcheva.easymath.adapters_listeners.SlidingPanelSettings;
import a.vidishcheva.easymath.activities_helpers.TriangleHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Triangle extends Activity {

    private static final String VALUES = "values";
    private static final String IS_OPEN = "is open";
    private SlidingPaneLayout sp;
    private GridLayout gl;
    private TriangleHelper th;
    private TextView result;
    private Button buttonCount;
    private Button buttonClear;
    private Localization loc;
    private ArrayList<Double> res;
    private TextView rules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);
        loc = new Localization(this);
        loc.setLocalization();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_activity_triangle);
        defineAllFields();
        sp.closePane();
        sp.setPanelSlideListener(new SlidingPanelSettings(this));
        setDimentions();
        EditText textView = (EditText) findViewById(R.id.angle_c);
        textView.setOnEditorActionListener(new OnEnterKeyListener(buttonCount));
        gl.setOnFocusChangeListener(new EditTextFocusSettings(this));
        if (savedInstanceState != null){
            buttonCount.setText(R.string.button_count);
            buttonClear.setText(R.string.button_clear);
            rules.setText(R.string.triangle_formulas);
            if (savedInstanceState.getBoolean(IS_OPEN)){
                sp.openPane();
            } else {
                sp.closePane();
            }
            ArrayList<String> save = savedInstanceState.getStringArrayList(VALUES);
            result.setText(save.get(save.size()-1));
            for (int i = 1, j = 0; i<gl.getChildCount()-2; i+=2, j++){
                TextView tw = (TextView) gl.getChildAt(i);
                tw.setText(save.get(j));
            }
        }
    }

    private void defineAllFields() {
        sp = (SlidingPaneLayout) findViewById(R.id.sliding_panel_triangle);
        buttonCount = (Button) findViewById(R.id.button_count);
        buttonClear = (Button) findViewById(R.id.button_clear);
        th = new TriangleHelper();
        gl = (GridLayout) findViewById(R.id.triangle_layout);
        result = (TextView) findViewById(R.id.triangle_todo);
        rules = (TextView) findViewById(R.id.rules_text_view);
    }

    public void onClickTriangle(View view){
        ArrayList<Double> sidesAndAngles = new ArrayList<>();
        String text = "";
        for (int i = 3; i<gl.getChildCount()-5; i+=2){
            TextView tw = (TextView) gl.getChildAt(i);
            text = tw.getText().toString();
            if (text.equals("")){
                sidesAndAngles.add(0.0);
            } else {
                sidesAndAngles.add(Double.valueOf(text));
            }
        }
        res = th.findTriangle(sidesAndAngles);
        switch (res.get(0).intValue()){
            case 0:
                result.setText(R.string.triangle_todo);
                setTextViews();
                break;
            case -1:
                result.setText(R.string.not_exist);
                break;
            case -2:
                result.setText(R.string.not_exist_but);
                break;
            case -3:
                result.setText(R.string.infinity_triangles);
                setTextViews();
                break;
            case -4:
                result.setText(R.string.infinity_triangles);
                setTextViews();
                break;
        }
    }
    private void setTextViews(){
        DecimalFormat decimalFormat=new DecimalFormat("#.###");
        for (int i = 3, j=1; i<gl.getChildCount()-2; i+=2, j++){
            TextView tw = (TextView) gl.getChildAt(i);
            tw.setText(decimalFormat.format(res.get(j)));
        }
    }
    public void onClickTriangleClear(View view){
        result.setText(R.string.triangle_todo);
        for (int i = 3; i<gl.getChildCount()-2; i+=2){
            TextView tw = (TextView) gl.getChildAt(i);
            tw.setText("");
        }
    }
    private void setDimentions(){
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        ImageView iv = (ImageView) findViewById(R.id.triangle_image);
        TextView tw = (TextView) findViewById(R.id.triangle_todo);
        iv.setMaxWidth(width/2);
        tw.setMaxWidth(width/2-(int)(0.1*width));
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
        ArrayList<String> save = new ArrayList<>();
        for (int i = 1; i<gl.getChildCount()-2; i+=2) {
            TextView tw = (TextView) gl.getChildAt(i);
            save.add(tw.getText().toString());
        }
        save.add(result.getText().toString());
        outState.putStringArrayList(VALUES, save);
        outState.putBoolean(IS_OPEN, sp.isOpen());
        super.onSaveInstanceState(outState);
    }
}
