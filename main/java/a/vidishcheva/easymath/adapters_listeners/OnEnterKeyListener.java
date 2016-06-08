package a.vidishcheva.easymath.adapters_listeners;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Алёна on 17.05.2016.
 */
public class OnEnterKeyListener implements TextView.OnEditorActionListener {

    Button button;

    public OnEnterKeyListener(Button button) {
        this.button = button;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            button.performClick();
        }
        return false;
    }
}
