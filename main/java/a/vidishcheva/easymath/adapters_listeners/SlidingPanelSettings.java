package a.vidishcheva.easymath.adapters_listeners;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Алёна on 16.05.2016.
 */
public class SlidingPanelSettings implements SlidingPaneLayout.PanelSlideListener {
    private Activity activity;

    public SlidingPanelSettings(Activity activity) {
        System.out.println("here");
        this.activity = activity;
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @Override
    public void onPanelOpened(View panel) {

    }
    @Override
    public void onPanelClosed(View panel) {

    }
}
