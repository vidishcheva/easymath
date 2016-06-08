package a.vidishcheva.easymath.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import a.vidishcheva.easymath.adapters_listeners.ExpAdapter;
import a.vidishcheva.easymath.adapters_listeners.Localization;
import a.vidishcheva.easymath.experimental_lab.ExperimentalLab;
import a.vidishcheva.easymath.R;

public class MainActivity extends Activity {

    private static final String IS_TRIG = "isTrigExp";
    private static final String IS_MATH = "isMathExp";
    private static final String IS_GEOM = "isGeomExp";
    private boolean isMathExp;
    private boolean isGeomExp;
    private boolean isTrigExp;
    private String[] menuArr;
    private String[] mathArr;
    private String[] geomArr;
    private String[] trigArr;
    private Localization loc;
    ExpAdapter expAdapter;
    SimpleExpandableListAdapter adapter;
    ExpandableListView expMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        loc = new Localization(this);
        loc.setLocalization();
        setListView();
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(IS_MATH)) {
                expMain.expandGroup(0);
            }
            if (savedInstanceState.getBoolean(IS_GEOM)) {
                expMain.expandGroup(1);
            }
            if (savedInstanceState.getBoolean(IS_TRIG)) {
                expMain.expandGroup(2);
            }
        }
    }
    private void startIntent(Class className){
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }
    private void setListView() {
        menuArr = getResources().getStringArray(R.array.first_menu);
        mathArr = getResources().getStringArray(R.array.math);
        geomArr = getResources().getStringArray(R.array.geom);
        trigArr = getResources().getStringArray(R.array.trig);

        expAdapter = new ExpAdapter(this, menuArr, mathArr, geomArr, trigArr);
        adapter = expAdapter.getAdapter();

        expMain = (ExpandableListView) findViewById(R.id.expMain);
        expMain.setAdapter(adapter);
        if (isMathExp) {
            expMain.expandGroup(0);
        }
        if (isGeomExp) {
            expMain.expandGroup(1);
        }
        if (isTrigExp) {
            expMain.expandGroup(2);
        }
        expMain.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition!=3)
                { return false;
                }
                return true;
            }
        });

        expMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                isMathExp = expMain.isGroupExpanded(0);
                isGeomExp = expMain.isGroupExpanded(1);
                isTrigExp = expMain.isGroupExpanded(2);
                if (groupPosition == 0) {
                    switch (childPosition) {
                        case 0:
                            Intent intent = new Intent(MainActivity.this, Equations.class);
                            startActivity(intent);
                            break;
                        case 1:
                            startIntent(Logarithm.class);
                            break;
                        case 2:
                            startIntent(ExperimentalLab.class);
                            break;
                    }
                    return true;
                }
                if (groupPosition == 1) {
                    switch (childPosition) {
                        case 0:
                            startIntent(Triangle.class);
                            break;
                        case 1:
                            startIntent(Circle.class);
                            break;
                    }
                    return true;

                }
                if (groupPosition == 2 && childPosition == 0) {
                    startIntent(Trigonometry.class);
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("onOptionsItemSelected");
        if (item.getItemId()==R.id.info) {
            loc.locInd = (loc.locInd+1)%2;
            if (loc.locInd == 0) {
                item.setIcon(R.mipmap.english);
            } else {
                item.setIcon(R.mipmap.russian);
            }
            loc.setLocalization();
            isMathExp = expMain.isGroupExpanded(0);
            isGeomExp = expMain.isGroupExpanded(1);
            isTrigExp = expMain.isGroupExpanded(2);
            setListView();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(IS_MATH, expMain.isGroupExpanded(0));
        outState.putBoolean(IS_GEOM, expMain.isGroupExpanded(1));
        outState.putBoolean(IS_TRIG, expMain.isGroupExpanded(2));
    }
    @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("onRestoreInstanceState");
        if (savedInstanceState != null){
            if (savedInstanceState.getBoolean(IS_MATH)) {
                expMain.expandGroup(0);
            }
            if (savedInstanceState.getBoolean(IS_GEOM)) {
                expMain.expandGroup(1);
            }
            if (savedInstanceState.getBoolean(IS_TRIG)) {
                expMain.expandGroup(2);
            }
        }
    }
}
